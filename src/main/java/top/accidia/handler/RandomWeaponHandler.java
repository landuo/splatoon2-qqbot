package top.accidia.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.enums.CommandEnum;
import top.accidia.pojo.Weapon;

import java.util.Random;

import static top.accidia.util.ApplicationUtil.WEAPONS;
import static top.accidia.util.MessageUtil.sendWeaponMessage;

/**
 * 随机武器
 *
 * @author accidia
 */
public class RandomWeaponHandler implements CommandHandler {
    @Override
    public CommandEnum getCommand() {
        return CommandEnum.RANDOM_WEAPON;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        Random random = new Random();
        Weapon weapon = WEAPONS.get(random.nextInt(WEAPONS.size()));
        sendWeaponMessage(event, messages, weapon);
    }
}
