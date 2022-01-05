package top.accidia.util;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IoUtil;

import java.awt.*;
import java.io.InputStream;

public class ResourceUtil {
    /**
     * 缩放图片,按比例缩放
     */
    public static InputStream scale(String filePath) {
        return scale(filePath, 0.5f);
    }

    /**
     * 缩放图片,按比例缩放
     */
    public static InputStream scale(String filePath, float scale) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filePath.substring(1));
        Image newImage = ImgUtil.scale(ImgUtil.read(resourceAsStream), scale);
        return IoUtil.toStream(ImgUtil.toBytes(newImage, "png"));
    }

    /**
     * 缩放图片,按长宽缩放
     */
    public static InputStream scaleSize(String filePath) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filePath.substring(1));
        Image cut = ImgUtil.scale(ImgUtil.read(resourceAsStream), 640, 360);
        return IoUtil.toStream(ImgUtil.toBytes(cut, "png"));
    }
}
