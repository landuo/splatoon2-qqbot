package top.accidia.handler;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.enums.CommandEnum;
import top.accidia.util.CacheUtils;
import top.accidia.util.ResourceUtils;

/**
 * 查询打工时间安排
 *
 * @author accidia
 */
public class SalmonCommandV3Handler implements CommandHandler {

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.SALMON_V3;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        BufferedImage bufferedImage = CacheUtils.getSalmon3(LocalDateTime.now());
        messages.append(Contact.uploadImage(event.getSubject(), ResourceUtils.scale(bufferedImage)));
        messages.append(new At(event.getSender().getId()));
        event.getSubject().sendMessage(messages.build());
    }

}
