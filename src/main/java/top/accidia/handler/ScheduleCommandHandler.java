package top.accidia.handler;

import cn.hutool.core.date.DateUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.api.Splatoon2Service;
import top.accidia.pojo.Regular;
import top.accidia.pojo.Schedule;
import top.accidia.util.ResourceUtil;

import java.util.Date;

/**
 * 查询时间安排
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
        Schedule schedule = Splatoon2Service.getSchedules();
        getRegularAtIndex(event, messages, schedule, 0);
        messages.append("\n");
        getRegularAtIndex(event, messages, schedule, 1);
        event.getSubject().sendMessage(messages.build());
    }

    private void getRegularAtIndex(MessageEvent event, MessageChainBuilder messages, Schedule schedule, Integer index) {
        Regular regular = schedule.getRegular().get(index);
        String secondStartTime = DateUtil.format(new Date(regular.getStartTime() * 1000), "HH:mm");
        String secondEndTime = DateUtil.format(new Date(regular.getEndTime() * 1000), "HH:mm");
        String secondTime = secondStartTime + "-" + secondEndTime;
        messages.append(secondTime).append("\n");
        messages.append(
                Contact.uploadImage(event.getSubject(), ResourceUtil.scaleSize(regular.getStageA().getImage())));
        messages.append(
                Contact.uploadImage(event.getSubject(), ResourceUtil.scaleSize(regular.getStageB().getImage())));
    }
}
