package top.accidia.util;

import java.util.Date;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import top.accidia.api.Splatoon2Service;
import top.accidia.pojo.battle.Regular;
import top.accidia.pojo.battle.Schedule;
import top.accidia.pojo.salmon.Salmon;
import top.accidia.pojo.salmon.SalmonSchedule;

/**
 * @author accidia
 */
public class CacheUtils {
    private static Schedule schedule;
    private static Salmon salmon;

    static {
        schedule = Splatoon2Service.getSchedules();
        salmon = Splatoon2Service.getSalmon();
    }

    public static Schedule getSchedule() {
        Regular regular = schedule.getRegular().get(0);
        DateTime startTime = DateUtil.beginOfHour(new Date(regular.getStartTime() * 1000));
        DateTime endTime = DateUtil.beginOfHour(new Date(regular.getEndTime() * 1000));
        DateTime now = DateUtil.beginOfHour(new Date());
        if (now.compareTo(startTime) >= 0 && now.compareTo(endTime) < 0) {
            return schedule;
        }
        schedule = Splatoon2Service.getSchedules();
        return schedule;
    }

    public static Salmon getSalmon() {
        SalmonSchedule salmonSchedule = salmon.getSchedules().get(0);
        DateTime endTime = DateUtil.beginOfHour(new Date(salmonSchedule.getEndTime() * 1000));
        DateTime now = DateUtil.beginOfHour(new Date());
        if (now.compareTo(endTime) <= 0) {
            return salmon;
        }
        salmon = Splatoon2Service.getSalmon();
        return salmon;
    }

}
