package top.accidia.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import top.accidia.pojo.Weapon;

/**
 * @author accidia
 */
public class ApplicationUtil {
    public static List<Weapon> WEAPONS;

    public static void initWeaponData() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("merge-data.json");
        InputStreamReader reader = new InputStreamReader(is);
        Gson gson = new Gson();
        // 使用GSON把一个json对象转换成java对象
        TypeToken<List<Weapon>> type = new TypeToken<List<Weapon>>() {
        };
        WEAPONS = gson.fromJson(reader, type.getType());
    }

    public static void initSchedule() {
        new CacheUtils();
    }
}
