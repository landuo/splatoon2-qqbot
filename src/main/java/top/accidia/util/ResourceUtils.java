package top.accidia.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpUtil;
import net.coobird.thumbnailator.Thumbnails;
import top.accidia.constant.PropUtil;
import top.accidia.constant.SpApiConstant;

/**
 * @author accidia
 */
public class ResourceUtils {
    /**
     * 缩放图片,按比例缩放
     * 
     * @param filePath
     *            资源文件的路径
     */
    public static InputStream scale(String filePath) {
        return scale(filePath, 0.5f);
    }

    /**
     * 缩放图片,按比例缩放
     *
     */
    public static InputStream scale(BufferedImage image) {
        try {
            BufferedImage bufferedImage = Thumbnails.of(image).scale(0.5d).asBufferedImage();
            return IoUtil.toStream(ImgUtil.toBytes(bufferedImage, ImgUtil.IMAGE_TYPE_PNG));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩放图片,按比例缩放
     *
     * @param filePath
     *            资源文件的路径
     */
    public static BufferedImage scaleToImage(String filePath, float scale) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filePath.substring(1));
        try {
            return Thumbnails.of(resourceAsStream).scale(scale).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩放图片,按比例缩放
     *
     * @param filePath
     *            资源文件的路径
     */
    public static BufferedImage scaleToImage(String filePath, int width, int height) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filePath.substring(1));
        try {
            return Thumbnails.of(resourceAsStream).width(width).height(height).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩放图片,按比例缩放
     * 
     * @param filePath
     *            资源文件的路径
     */
    public static InputStream scale(String filePath, float scale) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filePath.substring(1));
        Image newImage = null;
        try {
            newImage = Thumbnails.of(resourceAsStream).scale(scale).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert newImage != null;
        return IoUtil.toStream(ImgUtil.toBytes(newImage, ImgUtil.IMAGE_TYPE_PNG));
    }

    /**
     * 缩放图片,按长宽缩放
     * 
     * @param filePath
     *            资源文件的路径
     */
    public static InputStream scaleSize(String filePath) {
        return scaleSize(filePath, 640, 360);
    }

    /**
     * 缩放图片,按长宽缩放
     *
     * @param absolutePath
     *            文件的绝对路径
     */
    public static BufferedImage scaleSizeToBufferImage(String absolutePath, int width, int height) {
        try {
            return Thumbnails.of(absolutePath).width(width).height(height).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩放图片,按长宽缩放
     *
     * @param absolutePath
     *            文件的绝对路径
     */
    public static BufferedImage scaleSizeToBufferImage(String absolutePath) {
        return scaleSizeToBufferImage(absolutePath, 640, 360);
    }

    /**
     * 缩放图片,按长宽缩放
     * 
     * @param filePath
     *            资源文件的路径
     */
    public static InputStream scaleSize(String filePath, Integer width, Integer height) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(filePath.substring(1));
        Image newImage = null;
        try {
            newImage = Thumbnails.of(resourceAsStream).width(width).height(height).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert newImage != null;
        return IoUtil.toStream(ImgUtil.toBytes(newImage, ImgUtil.IMAGE_TYPE_PNG));
    }

    /**
     * 获取外部文件夹的图片
     *
     * @param picSuffix
     *            图片地址后缀
     */
    public static InputStream getExternalPicture(String picSuffix) {
        String picDir = PropUtil.getPicDir();
        File file = FileUtil.file(picDir + picSuffix);
        if (file.exists()) {
            return FileUtil.getInputStream(file);
        }
        // 图片不存在的话就去远程下载
        HttpUtil.downloadFile(SpApiConstant.PICTURE + picSuffix, picDir + picSuffix);
        return FileUtil.getInputStream(picDir + picSuffix);
    }
}
