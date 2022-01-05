package top.accidia;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import cn.hutool.setting.dialect.Props;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.BotConfiguration;
import top.accidia.handler.CommandContext;
import top.accidia.handler.CommandHandler;
import top.accidia.util.ApplicationUtil;

/**
 * @author accidia
 */
public class BotStarter {

    public static void main(String[] args) {
        ApplicationUtil.initWeaponData();
        Props props = new Props("app.properties");
        String botQQStr = (String) props.get("botQQ");
        String botPwd = (String) props.get("botPwd");
        Assert.notBlank(botQQStr, "请在app.properties中配置botQQ");
        Assert.notBlank(botPwd, "请在app.properties中配置botPwd");

        long botQQ = Long.parseLong(botQQStr);
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
                messages.append("未找到该命令");
                event.getSubject().sendMessage(messages.build());
                return;
            }
            commandHandler.process(bot, event, messages);
        });
    }
}
