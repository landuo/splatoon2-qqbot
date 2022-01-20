package top.accidia.util;

import java.util.Date;

import cn.hutool.core.date.DateUtil;

/**
 * @author accidia
 */
public class DateUtils {
    private static final String BEGIN_OF_HOUR = "HH:mm";

    /**
     * 将时间戳格式化成默认格式的字符串
     * 
     * @param second
     *            秒级时间戳
     * 
     * @return
     */
    public static String formatDate(long second) {
        return formatDate(second, BEGIN_OF_HOUR);
    }

    /**
     * 将时间戳格式化成字符串
     * 
     * @param second
     *            秒级时间戳
     * @param pattern
     *            格式化的格式
     * 
     */
    public static String formatDate(long second, String pattern) {
        return DateUtil.format(new Date(second * 1000), pattern);
    }
}
