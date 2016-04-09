package com.iidooo.core.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

import sun.awt.image.ImageFormatException;

public class PictureUtil {

    private static final Logger logger = Logger.getLogger(PictureUtil.class);

    /**
     * 按照指定长宽比压缩图片
     * 
     * @param inputFilePath 输入的文件路径
     * @param outputFilePath 压缩后输出的文件路径
     * @param width 缩放目标宽度
     * @param height 缩放目标高度
     * @param proportion 是否等比例，默认为true
     */
    public static void compress(String inputFilePath, String outputFilePath, int width, int height, boolean proportion) {
        try {
            // 获得源文件
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                throw new FileNotFoundException();
            }

            Image inputImg = ImageIO.read(inputFile);
            // 判断图片格式是否正确
            if (inputImg.getWidth(null) == -1) {
                throw new ImageFormatException(inputFilePath);
            }

            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (proportion == true) {
                // 为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) inputImg.getWidth(null)) / (double) width + 0.1;
                double rate2 = ((double) inputImg.getHeight(null)) / (double) height + 0.1;
                // 根据缩放比率大的进行缩放控制
                double rate = rate1 > rate2 ? rate1 : rate2;
                newWidth = (int) (((double) inputImg.getWidth(null)) / rate);
                newHeight = (int) (((double) inputImg.getHeight(null)) / rate);
            } else {
                newWidth = width; // 输出的图片宽度
                newHeight = height; // 输出的图片高度
            }

            // 如果图片小于目标图片的宽和高则不进行转换
            if (inputImg.getWidth(null) < width && inputImg.getHeight(null) < height) {
                newWidth = inputImg.getWidth(null);
                newHeight = inputImg.getHeight(null);
            }
            BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

            // Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的,优先级比速度高 生成的图片质量比较好 但速度慢
            tag.getGraphics().drawImage(inputImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

//            File outputFolder = new File(outputFilePath);
//            // 判断文件夹是否存在,如果不存在则创建文件夹
//            if (!outputFolder.exists()) {
//                outputFolder.mkdir();
//            }

            // 得到图片的扩展名，也就是图片的格式
            String format = FileUtil.getFileSuffix(outputFilePath);

            FileOutputStream out = new FileOutputStream(outputFilePath);
            // JPEGImageEncoder可适用于其他图片类型的转换
            // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            // encoder.encode(tag);
            ImageIO.write(tag, format, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
    
    /**
     * 不改变长宽的压缩图片
     * 
     * @param inputFilePath 输入的文件路径
     * @param outputFilePath 压缩后输出的文件路径
     */
    public static void compress(String inputFilePath, String outputFilePath) {
        try {
            // 获得源文件
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                throw new FileNotFoundException();
            }

            Image inputImg = ImageIO.read(inputFile);
            // 判断图片格式是否正确
            if (inputImg.getWidth(null) == -1) {
                throw new ImageFormatException(inputFilePath);
            }

            int newWidth = inputImg.getWidth(null);
            int newHeight = inputImg.getHeight(null);           
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            // Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的,优先级比速度高 生成的图片质量比较好 但速度慢
            tag.getGraphics().drawImage(inputImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

            File outputFolder = new File(outputFilePath);
            // 判断文件夹是否存在,如果不存在则创建文件夹
            if (!outputFolder.exists()) {
                outputFolder.mkdir();
            }

            // 得到图片的扩展名，也就是图片的格式
            String format = FileUtil.getFileSuffix(outputFilePath);

            FileOutputStream out = new FileOutputStream(outputFilePath);
            // JPEGImageEncoder可适用于其他图片类型的转换
            // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            // encoder.encode(tag);
            ImageIO.write(tag, format, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    /**
     * 根据指定坐标和长宽裁剪图片
     * 
     * @param inputFilePath 源图片
     * @param outputFilePath 产生新图片路径
     * @param x x轴的开始坐标
     * @param y y轴的开始坐标
     * @param width 所裁剪的宽度
     * @param height 所裁剪的高度
     */
    public static void cut(String inputFilePath, String outputFilePath, int x, int y, int width, int height) {

        FileInputStream fileInputStream = null;
        ImageInputStream imageInputStream = null;
        try {
            // 获得源文件
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                throw new FileNotFoundException();
            }

            // 读取图片文件
            fileInputStream = new FileInputStream(inputFilePath);

            // 获取文件格式
            String format = FileUtil.getFileSuffix(inputFilePath);

            // ImageReader声称能够解码指定格式
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(format);
            ImageReader reader = it.next();

            // 获取图片流
            imageInputStream = ImageIO.createImageInputStream(fileInputStream);

            // 输入源中的图像将只按顺序读取
            reader.setInput(imageInputStream, true);

            // 描述如何对流进行解码
            ImageReadParam param = reader.getDefaultReadParam();

            // 图片裁剪区域
            Rectangle rect = new Rectangle(x, y, width, height);

            // 提供一个 BufferedImage，将其用作解码像素数据的目标
            param.setSourceRegion(rect);

            // 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象
            BufferedImage bi = reader.read(0, param);

            // 保存新图片
            // File tempOutFile = new File(outputFilePath);
            // if (!tempOutFile.exists()) {
            // tempOutFile.mkdirs();
            // }
            ImageIO.write(bi, format, new File(outputFilePath));
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (imageInputStream != null) {
                    imageInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.fatal(e);
            }
        }
    }

    /**
     * 裁剪出图片的中间正方形
     * 
     * @param inputFilePath
     * @param outputFilePath
     */
    public static void cutCenterSquare(String inputFilePath, String outputFilePath) {
        try {
            // 获得源文件
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                throw new FileNotFoundException();
            }
            BufferedImage bufferedImage = ImageIO.read(inputFile);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int stand = width >= height ? height : width;
            if (stand == height) {
                int x = Math.round((width - height) / 2);
                int y = Math.round(0);
                cut(inputFilePath, outputFilePath, x, y, stand, stand);
            } else {
                int x = Math.round(0);
                int y = Math.round((height - width) / 2);
                cut(inputFilePath, outputFilePath, x, y, stand, stand);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public static void main(String[] arg) {
        try {
            String inputFilePath = "/Users/Ethan/workspace/upload/201604/sample.png";
            String outputFilePath = "/Users/Ethan/workspace/upload/201604/sample.png";
            PictureUtil.compress(inputFilePath, outputFilePath, 200, 200, true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
