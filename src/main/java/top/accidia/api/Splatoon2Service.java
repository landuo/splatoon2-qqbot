package top.accidia.api;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import top.accidia.pojo.Schedule;
import top.accidia.util.SpApiConstant;

/**
 * @author accidia
 */
public class Splatoon2Service {

    public static Schedule getSchedules() {
        String response = HttpUtil.get(SpApiConstant.DATA_SCHEDULE);
        Gson gson = new Gson();
        Schedule schedule = gson.fromJson(response, Schedule.class);
        // schedule.getGachi().forEach(System.out::println);
        // schedule.getRegular().forEach(System.out::println);
        // schedule.getLeague().forEach(System.out::println);
        return schedule;
    }
}
