package top.accidia.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import top.accidia.api.Splatoon2Service;
import top.accidia.enums.GameModeEnum;
import top.accidia.pojo.battle.Gachi;
import top.accidia.pojo.battle.League;
import top.accidia.pojo.battle.Regular;
import top.accidia.pojo.battle.Schedule;
import top.accidia.pojo.salmon.Salmon;
import top.accidia.pojo.salmon.SalmonDetail;
import top.accidia.pojo.salmon.SalmonWeapon;

/**
 * @author accidia
 */
public class CacheUtils extends Splatoon2Service {
    private static final Map<String, BufferedImage> SCHEDULE_PIC = new HashMap<>(16, 0.8f);
    private static BufferedImage SALMON_PIC = null;
    private static Schedule schedule;
    private static Salmon salmon;

    static {
        buildBattleSchedules();
        buildSalmonSchedule();
    }

    /**
     * 构建打工图片并缓存起来
     */
    private static void buildSalmonSchedule() {
        salmon = getSalmon();
        int bgWidth = 760;
        int bgHeight = 820;
        SALMON_PIC = new BufferedImage(bgWidth, bgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = SALMON_PIC.getGraphics();
        // 填充背景色
        graphics.setColor(Color.decode("#ff5600"));
        graphics.fillRect(0, 0, bgWidth, bgHeight);
        SalmonDetail nowSalmon = salmon.getDetails().stream().min(Comparator.comparing(SalmonDetail::getStartTime))
                .get();
        SalmonDetail nextSalmon = salmon.getDetails().stream().max(Comparator.comparing(SalmonDetail::getStartTime))
                .get();
        buildSalmonPicture(nowSalmon, 0);
        buildSalmonPicture(nextSalmon, 400);
    }

    /**
     * 根据时间获取对战安排
     * 
     * @param startTime
     *            对战开始时间，格式: MM-dd HH
     */
    public static BufferedImage getScheduleByTime(String startTime) {
        boolean containsKey = SCHEDULE_PIC.containsKey(startTime);
        if (!containsKey) {
            SCHEDULE_PIC.clear();
            buildBattleSchedules();
        }
        return SCHEDULE_PIC.get(startTime);
    }

    /**
     * 根据时间获取打工安排
     *
     * @param nowTime
     *            当前时间
     */
    public static BufferedImage getSalmonByTime(DateTime nowTime) {
        boolean dataExpired = salmon.getDetails().stream()
                .anyMatch(e -> e.getEndTime().compareTo(nowTime.getTime() / 1000) <= 0);
        if (dataExpired) {
            buildSalmonSchedule();
        }
        return SALMON_PIC;
    }

    /**
     * 构建对战图片并缓存起来
     */
    private static void buildBattleSchedules() {
        schedule = Splatoon2Service.getSchedules();
        int bgWidth = 1400;
        int bgHeight = 1200;
        int picWidth = 640;
        int picHeight = 360;
        int paddingLeft = 70;
        int paddingTop = 50;
        int picPaddingTop = 15;
        int picPaddingLeft = 20;
        // 预生成12张时刻图存放到map中
        for (int i = 0; i < schedule.getGachi().size(); i++) {
            Regular regular = schedule.getRegular().get(i);
            Long startTime = regular.getStartTime();
            Gachi gachi = schedule.getGachi().stream().filter(e -> e.getStartTime().equals(startTime)).findFirst()
                    .get();
            League league = schedule.getLeague().stream().filter(e -> e.getStartTime().equals(startTime)).findFirst()
                    .get();

            // 创建空白画布
            BufferedImage bufferedImage = new BufferedImage(bgWidth, bgHeight, BufferedImage.TYPE_INT_RGB);
            drawSchedule(picWidth, picHeight, paddingLeft, paddingTop, picPaddingTop, picPaddingLeft, regular, gachi,
                    league, bufferedImage);
            // 将生成的图片保存到map中
            SCHEDULE_PIC.put(DateUtils.formatDate(regular.getStartTime(), "MM-dd HH"), bufferedImage);
        }
    }

    private static void drawSchedule(int picWidth, int picHeight, int paddingLeft, int paddingTop, int picPaddingTop,
            int picPaddingLeft, Regular regular, Gachi gachi, League league, BufferedImage bufferedImage) {
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(ImgUtil.read(ResourceUtil.getResourceObj("images/bg-squids.png")), 0, 0, null);

        // 设置文字字体和画笔颜色
        Font font = new Font("Fira code", Font.BOLD, 50);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        // 打印时间
        graphics.drawString(DateUtils.formatDate(regular.getStartTime(), "MM-dd HH:mm") + " - "
                + DateUtils.formatDate(regular.getEndTime(), "MM-dd HH:mm"), paddingLeft, paddingTop);

        font = new Font("Fira code", Font.BOLD, 50);
        graphics.setFont(font);

        // 打印游戏模式
        String gameMode = GameModeEnum.getZhNameByKey(regular.getRule().getKey());
        // 竖排文字
        for (int j = 0; j < gameMode.length(); j++) {
            graphics.drawString(String.valueOf(gameMode.charAt(j)), 10, 200 + 100 * j);
        }

        // 添加涂地模式的地图
        BufferedImage regularStageA = ImgUtil
                .read(ResourceUtil.getResource(regular.getStageA().getImage().substring(1)));
        BufferedImage regularStageB = ImgUtil
                .read(ResourceUtil.getResource(regular.getStageB().getImage().substring(1)));
        graphics.drawImage(regularStageA, paddingLeft, paddingTop + picPaddingTop, picWidth, picHeight, null);
        graphics.drawImage(regularStageB, paddingLeft + picPaddingLeft + picWidth, paddingTop + picPaddingTop, picWidth,
                picHeight, null);

        // 打印游戏模式
        gameMode = GameModeEnum.getZhNameByKey(gachi.getRule().getKey());
        // 竖排文字
        for (int j = 0; j < gameMode.length(); j++) {
            graphics.drawString(String.valueOf(gameMode.charAt(j)), 10, (gameMode.length() == 1 ? 650 : 570) + 100 * j);
        }
        // 添加排位模式的地图
        BufferedImage gachiStageA = ImgUtil.read(ResourceUtil.getResource(gachi.getStageA().getImage().substring(1)));
        BufferedImage gachiStageB = ImgUtil.read(ResourceUtil.getResource(gachi.getStageB().getImage().substring(1)));
        graphics.drawImage(gachiStageA, paddingLeft, paddingTop + picPaddingTop * 2 + picHeight, picWidth, picHeight,
                null);
        graphics.drawImage(gachiStageB, paddingLeft + picPaddingLeft + picWidth,
                paddingTop + picPaddingTop * 2 + picHeight, picWidth, picHeight, null);

        // 打印游戏模式
        gameMode = GameModeEnum.getZhNameByKey(league.getRule().getKey());
        // 竖排文字
        for (int j = 0; j < gameMode.length(); j++) {
            graphics.drawString(String.valueOf(gameMode.charAt(j)), 10, (gameMode.length() == 1 ? 1000 : 950) + j * 50);
        }
        // 添加真格模式的地图
        BufferedImage leagueStageA = ImgUtil.read(ResourceUtil.getResource(league.getStageA().getImage().substring(1)));
        BufferedImage leagueStageB = ImgUtil.read(ResourceUtil.getResource(league.getStageB().getImage().substring(1)));
        graphics.drawImage(leagueStageA, paddingLeft, paddingTop + picPaddingTop * 3 + picHeight * 2, picWidth,
                picHeight, null);
        graphics.drawImage(leagueStageB, paddingLeft + picPaddingLeft + picWidth,
                paddingTop + picPaddingTop * 3 + picHeight * 2, picWidth, picHeight, null);

        // 添加对战模式图标
        graphics.drawImage(ImgUtil.read(ResourceUtil.getResourceObj("images/battle-regular.png")), 640, 180, 156, 156,
                null);
        graphics.drawImage(ImgUtil.read(ResourceUtil.getResourceObj("images/battle-ranked.png")), 640, 540, 156, 156,
                null);
        graphics.drawImage(ImgUtil.read(ResourceUtil.getResourceObj("images/battle-league.png")), 640, 880, 156, 156,
                null);
    }

    /**
     *
     * @param beginHeight
     *            开始绘制的高度
     *
     */
    private static void buildSalmonPicture(SalmonDetail salmonDetail, int beginHeight) {
        BufferedImage image = ResourceUtils.getExternalPicture(salmonDetail.getStage().getImage());
        Font f = new Font("Fira Code", Font.BOLD, 25);
        Color mycolor = Color.WHITE;
        Graphics graphics = SALMON_PIC.getGraphics();
        graphics.setColor(mycolor);
        graphics.setFont(f);
        String time = DateUtils.formatDate(salmonDetail.getStartTime(), "MM-dd HH:mm") + " - "
                + DateUtils.formatDate(salmonDetail.getEndTime(), "MM-dd HH:mm");
        // 编写文字
        graphics.drawString(time, 20, 30 + beginHeight);
        // 绘制地图
        graphics.drawImage(image, 10, 40 + beginHeight, 640, 360, null);

        // 在地图右边绘制武器
        for (SalmonWeapon weapon : salmonDetail.getWeapons()) {
            BufferedImage weaponImage = null;
            if (weapon.getWeapon() != null) {
                weaponImage = ResourceUtils.getInternalPicture(weapon.getWeapon().getImage());
            } else {
                weaponImage = ResourceUtils
                        .getInternalPicture("/images/coop_weapons/746f7e90bc151334f0bf0d2a1f0987e311b03736.png");
            }
            graphics.drawImage(weaponImage, 10 + 640, 40 + beginHeight + 90 * salmonDetail.getWeapons().indexOf(weapon),
                    90, 90, null);
        }
    }

}
