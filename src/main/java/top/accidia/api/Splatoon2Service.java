package top.accidia.api;

import com.google.gson.Gson;

import cn.hutool.http.HttpUtil;
import top.accidia.constant.SpApiConstant;
import top.accidia.pojo.battle.Schedule;
import top.accidia.pojo.salmon.Salmon;

/**
 * @author accidia
 */
public class Splatoon2Service {

    public static Schedule getSchedules() {
        System.out.println("发起http请求查询对战时刻表");
        return getScheduleData(SpApiConstant.DATA_SCHEDULE, Schedule.class);
    }

    public static Salmon getSalmon() {
        System.out.println("发起http请求查询打工时刻表");
        return getScheduleData(SpApiConstant.SALMON_RUN_SCHEDULES, Salmon.class);
    }

    private static <T> T getScheduleData(String url, Class<T> classOfT) {
        String response = HttpUtil.get(url);
        Gson gson = new Gson();
        return gson.fromJson(response, classOfT);
    }
}
