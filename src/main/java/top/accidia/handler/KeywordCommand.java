package top.accidia.handler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ArrayUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.enums.CommandEnum;
import top.accidia.util.ApplicationUtil;
import top.accidia.util.MessageUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 监控咕咕关键字聊天
 *
 * @author accidia
 */
public class KeywordCommand implements CommandHandler {
    @Override
    public CommandEnum getCommand() {
        return CommandEnum.KEYWORD;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        String[] commands = MessageUtil.splitCommands(event.getMessage().contentToString());
        if (commands.length == 1) {
            event.getSubject().sendMessage(ApplicationUtil.KEYWORDS.toString());
            return;
        }
        if ("delete".equalsIgnoreCase(commands[1])) {
            if (commands.length == 2) {
                event.getSubject().sendMessage("没有要删除的选项");
                return;
            }
            commands = ArrayUtil.remove(commands, 0);
            commands = ArrayUtil.remove(commands, 0);
            ApplicationUtil.KEYWORDS.removeAll(Arrays.asList(commands));
            FileUtil.writeLines(ApplicationUtil.KEYWORDS, ResourceUtil.getResource("keyword.txt").getPath(),
                    StandardCharsets.UTF_8);
        }
        if ("add".equalsIgnoreCase(commands[1])) {
            if (commands.length == 2) {
                event.getSubject().sendMessage("没有要添加的选项");
                return;
            }
            commands = ArrayUtil.remove(commands, 0);
            commands = ArrayUtil.remove(commands, 0);
            ApplicationUtil.KEYWORDS.addAll(Arrays.asList(commands));
            FileUtil.appendLines(Arrays.asList(commands), ResourceUtil.getResource("keyword.txt").getPath(),
                    StandardCharsets.UTF_8);
        }
        event.getSubject().sendMessage(ApplicationUtil.KEYWORDS.toString());
    }
}
