package com.iidooo.core.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
            File file = new File(folderPath);

            // 判断文件夹是否存在,如果不存在则创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }

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
     * 修改文件名
     * 
     * @param filePath 文件路径
     * @param newFileName 新的文件名
     * @return 重命名后的文件路径
     */
    public static String changeFileName(String filePath, String newFileName) {
        try {
            int dotPosition = filePath.lastIndexOf('.');
            if (dotPosition < 0) {
                return filePath;
            }
            // 获取文件类型
            String suffix = filePath.substring(dotPosition + 1, filePath.length());

            int slashPosition = filePath.lastIndexOf(File.separator);
            if (slashPosition < 0) {
                return filePath;
            }

            String folderPath = filePath.substring(0, slashPosition);
            filePath = folderPath + File.separator + newFileName + "." + suffix;
            return filePath;
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
            String date = DateUtil.getNow(DateUtil.DATE_TIME_FULL_SIMPLE);
            String newName = "";
            int dotPosition = fileName.lastIndexOf('.');
            if (dotPosition < 0) {
                newName = fileName + "_" + date;
            } else {
                // 获取文件类型
                String suffix = fileName.substring(dotPosition + 1, fileName.length());
                // 获取文件名
                String name = fileName.substring(0, dotPosition);

                newName = name + "_" + date + "." + suffix;
            }

            return newName;
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }

    public static String getNewFileName(String fileName, String appendStr) {
        try {
            int dotPosition = fileName.lastIndexOf('.');
            // 获取文件类型
            String suffix = fileName.substring(dotPosition + 1, fileName.length());
            // 获取文件名
            String name = fileName.substring(0, dotPosition);

            String newName = name + appendStr + "." + suffix;
            return newName;
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }

    public static String getFileSuffix(String fileName) {
        try {
            int dotPosition = fileName.lastIndexOf('.');
            // 获取文件类型
            String suffix = fileName.substring(dotPosition + 1, fileName.length());

            return suffix;
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }

    public static String getFileName(String filePath) {
        try {
            int dotPosition = filePath.lastIndexOf('.');
            if (dotPosition < 0) {
                return "";
            }
            int slashPosition = filePath.lastIndexOf(File.separator);
            if (slashPosition < 0) {
                return "";
            }
            String fileName = filePath.substring(slashPosition + 1, dotPosition);
            return fileName;
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }
    
    /**
     * 得到完整的文件名字，包括扩展名
     * @param filePath 文件路径
     * @return 包括扩展名的文件名
     */
    public static String getFullFileName(String filePath){
        try {
            int slashPosition = filePath.lastIndexOf(File.separator);
            if (slashPosition < 0) {
                return "";
            }
            String fileName = filePath.substring(slashPosition + 1);
            return fileName;
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }

    public static byte[] file2Byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "http://iidooo-toxic-wave-test.oss-cn-hangzhou.aliyuncs.com/201604/baobao_20160428205000035.png";
        String newFilePath = FileUtil.changeFileName(filePath, DateUtil.getNow(DateUtil.DATE_TIME_FULL_SIMPLE));
        System.out.println(newFilePath);
    }
}
