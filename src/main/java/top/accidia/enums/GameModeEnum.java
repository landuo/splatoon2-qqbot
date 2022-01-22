package top.accidia.enums;

import java.util.Arrays;
import java.util.Optional;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 游戏模式枚举类
 * 
 * @author accidia
 */
@Getter
@AllArgsConstructor
public enum GameModeEnum {
    SPLAT_ZONES("splat_zones", "区域"), RAINMAKER("rainmaker", "鱼"), CLAM_BLITZ("clam_blitz", "蛤蜊"),
    TOWER_CONTROL("tower_control", "塔"), TURF_WAR("turf_war", "涂地");

    private final String key;
    private final String zhName;

    public static String getZhNameByKey(String key) {
        Optional<GameModeEnum> gameModeEnum = Arrays.stream(values()).filter(e -> e.getKey().equals(key)).findFirst();
        return gameModeEnum.isPresent() ? gameModeEnum.get().getZhName() : StrUtil.EMPTY;
    }
}
