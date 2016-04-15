package com.iidooo.aliyun.util;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;

public class OSSUtil {

    private static final Logger logger = Logger.getLogger(OSSUtil.class);

    public static OSSClient getOSSClient(Properties aliyunProperties) {
        try {
            String endpoint = aliyunProperties.getProperty("ALIYUN_OSS_END_POINT");
            String accessKeyId = aliyunProperties.getProperty("ALIYUN_OSS_ACCESS_KEY_ID");
            String accessKeySecret = aliyunProperties.getProperty("ALIYUN_OSS_ACCESS_KEY_SECRET");
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            return client;
        } catch (Exception e) {
            logger.fatal(e);
            return null;
        }
    }

    public static String uploadFile(Properties aliyunProperties, OSSClient client, String folder, String filePath) {
        try {
            String result = "";
            String bucketName = aliyunProperties.getProperty("ALIYUN_OSS_BUCKET_NAME");
            String domain = aliyunProperties.getProperty("ALIYUN_OSS_DOMAIN");

            File file = new File(filePath);
            String newKey = folder + file.getName();

            client.putObject(bucketName, newKey, file);
            
            // 返回上传后的文件URL
            result = domain + newKey;
            return result;
        } catch (Exception e) {
            logger.fatal(e);
            return null;
        } finally {
            client.shutdown();
        }
    }
}
