package com.iidooo.core.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class);

    /**
     * 文件保存.
     *
     * @param byte[] 文件字节流
     * @param folderPath 保存的文件夹路径
     * @param fileName 要保存的文件名
     * 
     * @return 保存以后的文件路径
     */
    public static String save(byte[] fileByte, String folderPath, String fileName) {
        try {
            
            // 新的文件路径
            String newFilePath = folderPath + File.separator + fileName;

            // 数据流
            FileOutputStream out = new FileOutputStream(newFilePath);
            out.write(fileByte);
            out.close();
            return newFilePath;
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }

    /**
     * 通过在文件名后加上时间毫秒来获取唯一文件名
     *
     * @param fileName 原文件名
     * @return 新文件名
     */
    public static String getUniqueFileName(String fileName) {
        try {
            int dotPosition = fileName.lastIndexOf('.');
            // 获取文件类型
            String suffix = fileName.substring(dotPosition + 1, fileName.length());
            // 获取文件名
            String name = fileName.substring(0, dotPosition);
            String date = DateUtil.getNow(DateUtil.DATE_TIME_FULL_SIMPLE);
            String newName = name + "_" + date + "." + suffix;
            return newName;
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }
}
