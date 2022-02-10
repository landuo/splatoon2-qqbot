package top.accidia.handler;

import java.awt.image.BufferedImage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.enums.CommandEnum;
import top.accidia.util.CacheUtils;
import top.accidia.util.MessageUtil;
import top.accidia.util.ResourceUtils;

/**
 * 查询对战时间安排
 *
 * @author accidia
 */
public class ScheduleCommandHandler implements CommandHandler {

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.SCHEDULES;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        String[] commands = MessageUtil.splitCommands(event.getMessage().contentToString());
        int startHour;
        try {
            // 获取对战开始时间(偶数点)
            int hour = commands.length == 1 ? DateUtil.thisHour(true) : Integer.parseInt(commands[1]);
            startHour = hour >> 1 << 1;
        } catch (NumberFormatException e) {
            messages.append("参数格式错误");
            event.getSubject().sendMessage(messages.build());
            return;
        }
        // 组装map的key
        String startTime = (startHour < (DateUtil.thisHour(true) >> 1 << 1)
                ? DateUtil.format(DateUtil.tomorrow(), "MM-dd") : DateUtil.format(DateUtil.date(), "MM-dd"))
                + CharSequenceUtil.SPACE + ((startHour < 10 ? "0" : "") + startHour);
        BufferedImage bufferedImage = CacheUtils.getScheduleByTime(startTime);
        messages.append(Contact.uploadImage(event.getSubject(), ResourceUtils.scale(bufferedImage)));
        messages.append(new At(event.getSender().getId()));
        event.getSubject().sendMessage(messages.build());
    }

}
