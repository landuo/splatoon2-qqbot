package top.accidia.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.enums.CommandEnum;

/**
 * 查看帮助命令
 *
 * @author accidia
 */
public class HelpHandler implements CommandHandler {
    @Override
    public CommandEnum getCommand() {
        return CommandEnum.HELP;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            messages.append(commandEnum.getName()).append(":\n").append(commandEnum.getDesc());
            if (commandEnum.ordinal() != CommandEnum.values().length - 1) {
                messages.append("\n\n");
            }
        }
        event.getSubject().sendMessage(messages.build());

    }
}
