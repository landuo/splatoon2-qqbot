package top.accidia;

import cn.hutool.core.lang.Validator;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.BotConfiguration;
import top.accidia.util.PropUtil;
import top.accidia.handler.CommandContext;
import top.accidia.handler.CommandHandler;
import top.accidia.util.ApplicationUtil;

/**
 * @author accidia
 */
public class BotStarter {

    public static void main(String[] args) {
        ApplicationUtil.initWeaponData();
        ApplicationUtil.initSchedule();
        long botQQ = Long.parseLong(PropUtil.getProp(PropUtil.BOT_QQ));
        String botPwd = PropUtil.getProp(PropUtil.BOT_PWD);
        Bot bot = BotFactory.INSTANCE.newBot(botQQ, botPwd, configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
            configuration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
            configuration.fileBasedDeviceInfo("device.json");
        });
        bot.login();
        getMsg(bot);
    }

    private static void getMsg(Bot bot) {
        bot.getEventChannel().subscribeAlways(MessageEvent.class, (event) -> {
            String content = event.getMessage().contentToString();
            if (!content.startsWith(".") && !content.startsWith("。")) {
                return;
            }
            MessageChainBuilder messages = new MessageChainBuilder();
            // 有人在群消息中输入命令时, 回复该消息
            if (event instanceof GroupMessageEvent) {
                messages.append(new QuoteReply(event.getMessage()));
            }
            CommandHandler commandHandler = CommandContext.getMessageHandler(content);
            if (Validator.isNull(commandHandler)) {
                return;
            }
            commandHandler.process(bot, event, messages);
        });
    }
}
