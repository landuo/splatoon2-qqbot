package top.accidia.handler;

import cn.hutool.core.io.resource.ResourceUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.enums.CommandEnum;

/**
 * 查看所有装备品牌
 * 
 * @author accidia
 */
public class BrandCommand implements CommandHandler {
    @Override
    public CommandEnum getCommand() {
        return CommandEnum.BRANDS;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        messages.append(Contact.uploadImage(event.getSubject(),
                ResourceUtil.getStream("images/-9oxvQ5-176eZ12T3cSxc-8y.jpeg")));
        event.getSubject().sendMessage(messages.build());
    }
}
