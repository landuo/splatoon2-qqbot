package top.accidia.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.enums.CommandEnum;

/**
 * @author accidia
 */
public interface CommandHandler {
    /**
     * 获取命令
     */
    CommandEnum getCommand();

    /**
     * 执行命令
     * 
     * @param bot
     * @param event
     * @param messages
     */
    void process(Bot bot, MessageEvent event, MessageChainBuilder messages);
}
