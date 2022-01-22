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

    protected static Schedule getSchedules() {
        System.out.println("发起http请求查询对战时刻表");
        // InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("schedule.json");
        // InputStreamReader reader = new InputStreamReader(is);
        // Gson gson = new Gson();
        // // 使用GSON把一个json对象转换成java对象
        // TypeToken<Schedule> type = new TypeToken<Schedule>() {
        // };
        // return gson.fromJson(reader, type.getType());
        return getScheduleData(SpApiConstant.DATA_SCHEDULE, Schedule.class);
    }

    protected static Salmon getSalmon() {
        System.out.println("发起http请求查询打工时刻表");
        return getScheduleData(SpApiConstant.SALMON_RUN_SCHEDULES, Salmon.class);
    }

    private static <T> T getScheduleData(String url, Class<T> classOfT) {
        String response = HttpUtil.get(url);
        Gson gson = new Gson();
        return gson.fromJson(response, classOfT);
    }
}
