package top.accidia.handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import top.accidia.constant.PropUtil;
import top.accidia.pojo.salmon.Salmon;
import top.accidia.pojo.salmon.SalmonDetail;
import top.accidia.pojo.salmon.SalmonWeapon;
import top.accidia.util.CacheUtils;
import top.accidia.util.DateUtils;
import top.accidia.util.ResourceUtils;

/**
 * 查询打工时间安排
 *
 * @author accidia
 */
public class SalmonCommandHandler implements CommandHandler {
    private static final Map<String, BufferedImage> SALMON_PIC = new HashMap<>(4);

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.SALMON;
    }

    @Override
    public void process(Bot bot, MessageEvent event, MessageChainBuilder messages) {
        Salmon salmon = CacheUtils.getSalmon();
        getSalmonInfo(event, messages, salmon);
        event.getSubject().sendMessage(messages.build());
    }

    private void getSalmonInfo(MessageEvent event, MessageChainBuilder messages, Salmon salmon) {
        int bgWidth = 760;
        int bgHeight = 820;
        BufferedImage bufferedImage = SALMON_PIC
                .get(DateUtils.formatDate(salmon.getDetails().get(0).getStartTime(), "MM-dd HH:mm"));
        if (bufferedImage != null) {
            messages.append(Contact.uploadImage(event.getSubject(), ResourceUtils.scale(bufferedImage)));
            return;
        }
        bufferedImage = new BufferedImage(bgWidth, bgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        // 填充背景色
        graphics.setColor(Color.decode("#ff5600"));
        graphics.fillRect(0, 0, bgWidth, bgHeight);

        buildSalmonPicture(bufferedImage, salmon.getDetails().get(0), 0);
        buildSalmonPicture(bufferedImage, salmon.getDetails().get(1), 400);
        SALMON_PIC.clear();
        SALMON_PIC.put(DateUtils.formatDate(salmon.getDetails().get(0).getStartTime(), "MM-dd HH:mm"), bufferedImage);
        messages.append(Contact.uploadImage(event.getSubject(), ResourceUtils.scale(bufferedImage)));
    }

    /**
     *
     * @param bufferedImage
     *            需要生成的图片
     * @param beginHeight
     *            开始绘制的高度
     * 
     */
    private void buildSalmonPicture(BufferedImage bufferedImage, SalmonDetail salmonDetail, int beginHeight) {
        BufferedImage image = ResourceUtils
                .scaleSizeToBufferImage(PropUtil.getPicDir() + salmonDetail.getStage().getImage());
        Font f = new Font("Fira Code", Font.BOLD, 25);
        Color mycolor = Color.WHITE;
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(mycolor);
        graphics.setFont(f);
        String time = DateUtils.formatDate(salmonDetail.getStartTime(), "MM-dd HH:mm") + " - "
                + DateUtils.formatDate(salmonDetail.getEndTime(), "MM-dd HH:mm");
        // 编写文字
        graphics.drawString(time, 20, 30 + beginHeight);
        // 绘制地图
        graphics.drawImage(image, 10, 40 + beginHeight, null);

        // 在地图右边绘制武器
        for (SalmonWeapon weapon : salmonDetail.getWeapons()) {
            BufferedImage weaponImage = ResourceUtils.scaleToImage(weapon.getWeapon().getImage(), 90, 90);
            graphics.drawImage(weaponImage, 10 + image.getWidth(),
                    40 + beginHeight + weaponImage.getHeight() * salmonDetail.getWeapons().indexOf(weapon), null);
        }
    }

}
