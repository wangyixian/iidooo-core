package com.iidooo.core.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
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

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

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
                logger.fatal(new FileNotFoundException());
                return;
            }

            Image inputImg = ImageIO.read(inputFile);
            // 判断图片格式是否正确
            if (inputImg.getWidth(null) == -1) {
                logger.fatal(new ImageFormatException(inputFilePath));
                return;
            }

            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (proportion == true) {
                // 为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) inputImg.getWidth(null)) / (double) width;
                double rate2 = ((double) inputImg.getHeight(null)) / (double) height;
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

            // File outputFolder = new File(outputFilePath);
            // // 判断文件夹是否存在,如果不存在则创建文件夹
            // if (!outputFolder.exists()) {
            // outputFolder.mkdir();
            // }

            FileOutputStream out = new FileOutputStream(outputFilePath);
            String format = FileUtil.getFileSuffix(outputFilePath);
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
                logger.fatal(new FileNotFoundException());
                return;
            }

            Image inputImg = ImageIO.read(inputFile);
            // 判断图片格式是否正确
            if (inputImg.getWidth(null) == -1) {
                logger.fatal(new ImageFormatException(inputFilePath));
                return;
            }

            int newWidth = inputImg.getWidth(null);
            int newHeight = inputImg.getHeight(null);
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            // Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的,优先级比速度高 生成的图片质量比较好 但速度慢
            tag.getGraphics().drawImage(inputImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

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
                logger.fatal(new FileNotFoundException());
                return;
            }

            // 读取图片文件
            fileInputStream = new FileInputStream(inputFilePath);

            String format = FileUtil.getFileSuffix(inputFilePath);
            // System.out.println(format);
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
    public static void cutSquare(String inputFilePath, String outputFilePath) {
        try {
            // 获得源文件
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                logger.fatal(new FileNotFoundException());
                return;
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

    /**
     * 保持上传照片的图片方向不发生调整
     * 
     * @param filePath 图片的文件路径
     */
    public static void MaintainOrientation(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                logger.fatal(new FileNotFoundException());
                return;
            }

            Metadata metadata = ImageMetadataReader.readMetadata(file);
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            int orientation = 0;
            if (null != directory && directory.containsTag(ExifSubIFDDirectory.TAG_ORIENTATION)) {
                orientation = directory.getInt(ExifSubIFDDirectory.TAG_ORIENTATION);
                logger.debug(filePath + " 's orientation is :" + orientation);
            }
            String format = FileUtil.getFileSuffix(filePath);
            switch (orientation) {
            // case 1:
            // return "Top, left side (Horizontal / normal)";
            // case 2:
            // return "Top, right side (Mirror horizontal)";
            case 3:
                // return "Bottom, right side (Rotate 180)";
                BufferedImage old_img3 = (BufferedImage) ImageIO.read(file);
                int w3 = old_img3.getWidth();
                int h3 = old_img3.getHeight();

                BufferedImage new_img3 = new BufferedImage(w3, h3, BufferedImage.TYPE_INT_BGR);
                Graphics2D g2d3 = new_img3.createGraphics();

                AffineTransform origXform3 = g2d3.getTransform();
                AffineTransform newXform3 = (AffineTransform) (origXform3.clone());
                // center of rotation is center of the panel
                // double xRot3 = w3/2.0;
                newXform3.rotate(Math.toRadians(180.0), w3 / 2.0, h3 / 2.0); // 旋转180度

                g2d3.setTransform(newXform3);
                // draw image centered in panel
                g2d3.drawImage(old_img3, 0, 0, null);
                // Reset to Original
                g2d3.setTransform(origXform3);
                // 写到新的文件
                FileOutputStream out3 = new FileOutputStream(file);
                try {
                    ImageIO.write(new_img3, format, out3);
                } finally {
                    out3.close();
                }

                break;
            // case 4:
            // return "Bottom, left side (Mirror vertical)";
            // case 5:
            // return "Left side, top (Mirror horizontal and rotate 270
            // CW)";
            case 6:
                // return "Right side, top (Rotate 90 CW)";

                BufferedImage old_img6 = (BufferedImage) ImageIO.read(file);
                int w6 = old_img6.getWidth();
                int h6 = old_img6.getHeight();

                BufferedImage new_img6 = new BufferedImage(h6, w6, BufferedImage.TYPE_INT_BGR);
                Graphics2D g2d6 = new_img6.createGraphics();

                AffineTransform origXform6 = g2d6.getTransform();
                AffineTransform newXform6 = (AffineTransform) (origXform6.clone());
                // center of rotation is center of the panel
                double xRot6 = h6 / 2.0;
                newXform6.rotate(Math.toRadians(90.0), xRot6, xRot6); // 旋转90度

                g2d6.setTransform(newXform6);
                // draw image centered in panel
                g2d6.drawImage(old_img6, 0, 0, null);
                // Reset to Original
                g2d6.setTransform(origXform6);
                // 写到新的文件
                FileOutputStream out6 = new FileOutputStream(file);
                try {
                    ImageIO.write(new_img6, format, out6);
                } finally {
                    out6.close();
                }

                break;
            // case 7:
            // return "Right side, bottom (Mirror horizontal and rotate 90
            // CW)";
            case 8:
                // return "Left side, bottom (Rotate 270 CW)";
                BufferedImage old_img8 = (BufferedImage) ImageIO.read(file);
                int w8 = old_img8.getWidth();
                int h8 = old_img8.getHeight();

                BufferedImage new_img8 = new BufferedImage(h8, w8, BufferedImage.TYPE_INT_BGR);
                Graphics2D g2d8 = new_img8.createGraphics();

                AffineTransform origXform8 = g2d8.getTransform();
                AffineTransform newXform8 = (AffineTransform) (origXform8.clone());
                // center of rotation is center of the panel
                double xRot8 = w8 / 2.0;
                newXform8.rotate(Math.toRadians(270.0), xRot8, xRot8); // 旋转90度

                g2d8.setTransform(newXform8);
                // draw image centered in panel
                g2d8.drawImage(old_img8, 0, 0, null);
                // Reset to Original
                g2d8.setTransform(origXform8);
                // 写到新的文件
                FileOutputStream out8 = new FileOutputStream(file);
                try {
                    ImageIO.write(new_img8, format, out8);
                } finally {
                    out8.close();
                }

                break;
            // default:
            // return String.valueOf(orientation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    /**
     * 判断是否是图片文件
     * 
     * @param filePath 文件路径
     * @return 是否是文件的boolean
     */
    public static boolean isImage(String filePath) {
        try {
            // 获得源文件
            File inputFile = new File(filePath);
            if (!inputFile.exists()) {
                return false;
            }

            BufferedImage bufferedImage = ImageIO.read(inputFile);
            if (bufferedImage == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return false;
        }
    }

    public static void main(String[] arg) {
        try {
            String inputFilePath = "/Users/Ethan/百度云同步盘/MyProjects/iidooo-cms/03.test/PictureException/IE9  win7 x64 CHS.jpg";
            String outputFilePath = "/Users/Ethan/百度云同步盘/MyProjects/iidooo-cms/04.executable/test/image_20160424002100000029_mini.jpg";
            // PictureUtil.MaintainOrientation(inputFilePath);
            // PictureUtil.compress(inputFilePath, outputFilePath, 200, 200, false);
            if (isImage(inputFilePath)) {
                System.out.println(true);
            } else {
                System.out.println(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
