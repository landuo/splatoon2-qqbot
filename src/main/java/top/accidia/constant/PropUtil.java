package top.accidia.constant;

import cn.hutool.core.lang.Assert;
import cn.hutool.setting.dialect.Props;

/**
 * 配置文件key常量
 * 
 * @author accidia
 */
public class PropUtil {

    public static final String BOT_QQ = "botQQ";
    public static final String BOT_PWD = "botPwd";
    public static final String PIC_DIR = "picDir";
    private static final Props PROPS = new Props("app.properties");

    public static String getProp(String key) {
        String value = (String) PROPS.get(key);
        Assert.notBlank(value, "请在app.properties中配置" + key);
        return value;
    }

    public static String getPicDir() {
        String value = (String) PROPS.get(PIC_DIR);
        Assert.notBlank(value, "请在app.properties中配置" + PIC_DIR);
        return value;
    }

}
