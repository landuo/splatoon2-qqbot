package top.accidia.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.pojo.Weapon;
import top.accidia.util.MessageUtil;

import java.util.Optional;

import static top.accidia.util.ApplicationUtil.WEAPONS;
import static top.accidia.util.MessageUtil.sendWeaponMessage;

/**
 * 武器查询
 *
 * @author accidia
 */
public class WeaponQueryHandler implements CommandHandler {
    @Override
    public CommandEnum getCommand() {
        return CommandEnum.WEAPON;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        String[] commands = MessageUtil.splitCommands(event.getMessage().contentToString());
        if (commands.length == 1) {
            messages.append("参数错误");
            event.getSubject().sendMessage(messages.build());
            return;
        }
        Optional<Weapon> optionalWeapon = WEAPONS.stream().filter(w -> commands[1].equals(w.getZhCN())).findFirst();
        if (!optionalWeapon.isPresent()) {
            messages.append("未找到该武器");
            event.getSubject().sendMessage(messages.build());
            return;
        }
        Weapon weapon = optionalWeapon.get();
        sendWeaponMessage(event, messages, weapon);
    }
}
