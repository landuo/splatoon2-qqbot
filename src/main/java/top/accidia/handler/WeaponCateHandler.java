package top.accidia.handler;

import cn.hutool.core.collection.CollectionUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.pojo.Weapon;
import top.accidia.util.MessageUtil;
import top.accidia.util.ResourceUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.accidia.util.ApplicationUtil.WEAPONS;

/**
 * 武器分类
 *
 * @author accidia
 */
public class WeaponCateHandler implements CommandHandler {
    @Override
    public CommandEnum getCommand() {
        return CommandEnum.WEAPON_CATE;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        String[] commands = MessageUtil.splitCommands(event.getMessage().contentToString());
        // 查询所有武器分类
        if (commands.length == 1) {
            Set<String> weaponTypes = WEAPONS.stream().map(Weapon::getType).collect(Collectors.toSet());
            weaponTypes.forEach(type -> messages.append(type).append("\n"));
            event.getSubject().sendMessage(messages.build());
            return;
        }

        // 查询武器分类下的某一类
        if (commands.length == 2) {
            List<Weapon> weapons = WEAPONS.stream().filter(w -> w.getType().equals(commands[1]))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(weapons)) {
                weapons.forEach(weapon -> messages
                        .append(Contact.uploadImage(event.getSubject(), ResourceUtil.scale(weapon.getImage())))
                        .append("\n").append(weapon.getZhCN()).append("\n"));
                event.getSubject().sendMessage(messages.build());
            }
        }

    }
}
