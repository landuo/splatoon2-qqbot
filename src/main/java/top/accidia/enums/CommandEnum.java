package top.accidia.enums;

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
    HELP("help", "查看帮助, 所有命令以 . 或者 。开头, 命令可带参数时用空格区分"),
    SCHEDULES("图", "查询地图时间表, 后接时间的小时可查询该时间的对战. \n如： 图 18, 查询18:00-20:00的安排"), RANDOM_WEAPON("随机武器", "查询随机武器"),
    WEAPON_CATE("武器分类", "不加参数时查询所有武器分类;\n 可查询某类武器的所有武器,例如: 武器分类 水枪"), WEAPON("武器", "查询指定武器, 例如: 武器 红管"),
    SALMON("打工", "查询打工时刻表"), BRANDS("品牌", "查看所有装备品牌");

    private String name;
    private String desc;
}
