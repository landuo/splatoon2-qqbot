package top.accidia;

import cn.hutool.core.lang.Validator;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.events.GroupAwareMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.BotConfiguration;
import top.accidia.handler.CommandContext;
import top.accidia.handler.CommandHandler;
import top.accidia.util.ApplicationUtil;
import top.accidia.util.PropUtil;

/**
 * @author accidia
 */
public class BotStarter {

    public static void main(String[] args) {
         ApplicationUtil.initWeaponData();
        ApplicationUtil.initSchedule();
        ApplicationUtil.initKeyword();
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
            MessageChainBuilder messages = new MessageChainBuilder();

            // 以 。或者 .开头的是喷喷的查询
            if (content.startsWith(".") || content.startsWith("。")) {
                // 有人在群消息中输入命令时, 回复该消息
                if (event instanceof GroupMessageEvent) {
                    messages.append(new QuoteReply(event.getMessage()));
                }
                CommandHandler commandHandler = CommandContext.getMessageHandler(content);
                if (Validator.isNull(commandHandler)) {
                    return;
                }
                commandHandler.process(bot, event, messages);
                return;
            }
            if (ApplicationUtil.KEYWORDS.stream().anyMatch(content::contains) && content.contains("借")) {
                if (event instanceof GroupAwareMessageEvent) {
                    messages.append("群: ").append(((GroupAwareMessageEvent) event).getGroup().getName()).append("\n")
                            .append("发送人: ").append(event.getSenderName()).append("\n").append("发送信息: ")
                            .append(event.getMessage());
                    event.getBot().getFriend(Long.parseLong(PropUtil.getProp(PropUtil.SEND_TO)))
                            .sendMessage(messages.build());
                }
            }

        });
    }
}
