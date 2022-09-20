package top.accidia.util;

import cn.hutool.core.io.resource.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import top.accidia.pojo.Weapon;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author accidia
 */
public class ApplicationUtil {
    public static List<Weapon> WEAPONS;
    public static CopyOnWriteArrayList<String> KEYWORDS;

    public static void initWeaponData() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("weapon2.json");
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

    /**
     * 初始化咕咕全通关键词
     */
    public static void initKeyword() {
        String keyword = ResourceUtil.readUtf8Str("keyword.txt");
        String[] split = keyword.split("\\n");
        KEYWORDS = new CopyOnWriteArrayList<>();
        KEYWORDS.addAll(Arrays.asList(split));
    }
}
