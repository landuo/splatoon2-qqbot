package top.accidia.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.pojo.battle.Regular;
import top.accidia.pojo.battle.Schedule;
import top.accidia.util.CacheUtils;
import top.accidia.util.DateUtils;
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
        Schedule schedule = CacheUtils.getSchedule();
        getRegularAtIndex(event, messages, schedule, 0);
        messages.append("\n");
        getRegularAtIndex(event, messages, schedule, 1);
        event.getSubject().sendMessage(messages.build());
    }

    private void getRegularAtIndex(MessageEvent event, MessageChainBuilder messages, Schedule schedule, Integer index) {
        Regular regular = schedule.getRegular().get(index);
        String startTime = DateUtils.formatDate(regular.getStartTime());
        String endTime = DateUtils.formatDate(regular.getEndTime());
        String time = startTime + "-" + endTime;
        messages.append(time).append("\n");
        messages.append(
                Contact.uploadImage(event.getSubject(), ResourceUtils.scaleSize(regular.getStageA().getImage())));
        messages.append(
                Contact.uploadImage(event.getSubject(), ResourceUtils.scaleSize(regular.getStageB().getImage())));
    }
}
