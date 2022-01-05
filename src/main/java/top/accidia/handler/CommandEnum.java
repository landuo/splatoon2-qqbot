package top.accidia.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author accidia
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CommandEnum {
    HELP("help", "查看帮助, 所有命令以 . 或者 。开头, 命令可带参数时用空格区分"), SCHEDULES("图", "查询地图时间表"), RANDOM_WEAPON("随机武器", "查询随机武器"),
    WEAPON_CATE("武器分类", "不加参数时查询所有武器分类; 携带武器分类时查询该分类的武器,例如: 武器分类 水枪"), WEAPON("武器", "查询指定武器, 例如: 武器 红管");

    private String name;
    private String desc;
}
