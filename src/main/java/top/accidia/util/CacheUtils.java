package top.accidia.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
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
        int bgWidth = 1050;
        int bgHeight = 900;
        SALMON_PIC = new BufferedImage(bgWidth, bgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = SALMON_PIC.getGraphics();
        // 填充背景色
        graphics.setColor(Color.decode("#ff5600"));
        graphics.fillRect(0, 0, bgWidth, bgHeight);
        buildSalmonPicture(salmon.getDetails());
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
        int bgWidth = 1450;
        int bgHeight = 1250;
        int picWidth = 640;
        int picHeight = 360;
        int paddingLeft = 120;
        int paddingTop = 70;
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
            Graphics graphics = bufferedImage.getGraphics();
            graphics.drawImage(ImgUtil.read(ResourceUtil.getResourceObj("images/bg-squids.png")), 0, 0, bgWidth,
                    bgHeight, null);
            graphics.dispose();
            drawSchedule(picWidth, picHeight, paddingLeft, paddingTop, picPaddingTop, picPaddingLeft, regular, gachi,
                    league, bufferedImage);
            // 将生成的图片保存到map中
            SCHEDULE_PIC.put(DateUtils.formatDate(regular.getStartTime(), "MM-dd HH"),
                    ResourceUtils.radius(bufferedImage));
        }
    }

    private static void drawSchedule(int picWidth, int picHeight, int paddingLeft, int paddingTop, int picPaddingTop,
            int picPaddingLeft, Regular regular, Gachi gachi, League league, BufferedImage bufferedImage) {
        Graphics graphics = bufferedImage.getGraphics();
        // 设置文字字体和画笔颜色
        Font font = new Font("Baoli SC", Font.BOLD, 100);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        // 打印时间
        graphics.drawString(DateUtils.formatDate(regular.getStartTime(), "MM-dd HH:mm") + " - "
                + DateUtils.formatDate(regular.getEndTime(), "MM-dd HH:mm"), paddingLeft, paddingTop);
        font = new Font("Baoli SC", Font.BOLD, 150);
        // 打印游戏模式
        String gameMode = GameModeEnum.getZhNameByKey(regular.getRule().getKey());
        // 竖排文字
        for (int j = 0; j < gameMode.length(); j++) {
            graphics.drawString(String.valueOf(gameMode.charAt(j)), 10, 200 + 150 * j);
        }

        // 添加涂地模式的地图
        BufferedImage regularStageA = ResourceUtils
                .radius(ImgUtil.read(ResourceUtil.getResource(regular.getStageA().getImage().substring(1))));
        BufferedImage regularStageB = ResourceUtils
                .radius(ImgUtil.read(ResourceUtil.getResource(regular.getStageB().getImage().substring(1))));
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
        BufferedImage gachiStageA = ResourceUtils
                .radius(ImgUtil.read(ResourceUtil.getResource(gachi.getStageA().getImage().substring(1))));
        BufferedImage gachiStageB = ResourceUtils
                .radius(ImgUtil.read(ResourceUtil.getResource(gachi.getStageB().getImage().substring(1))));
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
        BufferedImage leagueStageA = ResourceUtils
                .radius(ImgUtil.read(ResourceUtil.getResource(league.getStageA().getImage().substring(1))));
        BufferedImage leagueStageB = ResourceUtils
                .radius(ImgUtil.read(ResourceUtil.getResource(league.getStageB().getImage().substring(1))));
        graphics.drawImage(leagueStageA, paddingLeft, paddingTop + picPaddingTop * 3 + picHeight * 2, picWidth,
                picHeight, null);
        graphics.drawImage(leagueStageB, paddingLeft + picPaddingLeft + picWidth,
                paddingTop + picPaddingTop * 3 + picHeight * 2, picWidth, picHeight, null);

        int iconMarginLeft = 695;
        // 添加对战模式图标
        graphics.drawImage(ImgUtil.read(ResourceUtil.getResourceObj("images/battle-regular.png")), iconMarginLeft, 180,
                156, 156, null);
        graphics.drawImage(ImgUtil.read(ResourceUtil.getResourceObj("images/battle-ranked.png")), iconMarginLeft, 540,
                156, 156, null);
        graphics.drawImage(ImgUtil.read(ResourceUtil.getResourceObj("images/battle-league.png")), iconMarginLeft, 910,
                156, 156, null);
    }

    /**
     *
     * @param salmonDetailList
     *            打工安排数组
     *
     */
    private static void buildSalmonPicture(List<SalmonDetail> salmonDetailList) {
        salmonDetailList.sort(Comparator.comparing(SalmonDetail::getStartTime));
        int mapWidth = 640;
        int mapHeight = 360;
        int weaponWH = 180;
        int fontSize = 70;
        for (int i = 0; i < salmonDetailList.size(); i++) {
            int beginHeight = i * 450;
            SalmonDetail salmonDetail = salmonDetailList.get(i);
            BufferedImage mapImage = ResourceUtils.getExternalPicture(salmonDetail.getStage().getImage());
            Font f = new Font("Baoli SC", Font.BOLD, fontSize);
            Color mycolor = Color.WHITE;
            Graphics graphics = SALMON_PIC.getGraphics();
            graphics.setColor(mycolor);
            graphics.setFont(f);
            String time = DateUtils.formatDate(salmonDetail.getStartTime(), "MM-dd HH:mm") + " - "
                    + DateUtils.formatDate(salmonDetail.getEndTime(), "MM-dd HH:mm");
            // 编写文字
            graphics.drawString(time, 20, beginHeight += fontSize);
            // 绘制地图
            graphics.drawImage(ResourceUtils.radius(mapImage), 10, beginHeight += 10, mapWidth, mapHeight, null);

            // 在地图右边绘制武器
            int weaponBeginWidth = 10 + mapWidth;
            int weaponBeginHeight = beginHeight - 10;
            for (int j = 0; j < salmonDetail.getWeapons().size(); j++) {
                SalmonWeapon weapon = salmonDetail.getWeapons().get(j);
                BufferedImage weaponImage;
                if (weapon.getWeapon() != null) {
                    weaponImage = ResourceUtils.getInternalPicture(weapon.getWeapon().getImage());
                } else {
                    weaponImage = ResourceUtils
                            .getInternalPicture("/images/coop_weapons/746f7e90bc151334f0bf0d2a1f0987e311b03736.png");
                }

                graphics.drawImage(weaponImage, (weaponBeginWidth + (j % 2 * weaponWH)),
                        (weaponBeginHeight + (j > 1 ? weaponWH : 0)), weaponWH, weaponWH, null);
            }
        }
        SALMON_PIC = ResourceUtils.radius(SALMON_PIC);
    }

}
