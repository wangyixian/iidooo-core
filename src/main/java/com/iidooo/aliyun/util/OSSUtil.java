package com.iidooo.aliyun.util;

import java.io.File;

import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;

public class OSSUtil {

    private static final Logger logger = Logger.getLogger(OSSUtil.class);
    
    public static OSSClient getOSSClient(String endpoint, String accessKeyId, String accessKeySecret){
        try {
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            return client;
        } catch (Exception e) {
            logger.fatal(e);
            return null;
        }
    }
    
    public static String uploadFile(OSSClient client, String bucketName, String folder, String filePath) {
        try {
            File file = new File(filePath);
            String newKey = folder + file.getName();
            
            client.putObject(bucketName, newKey, file);
            return newKey;
        } catch (Exception e) {
            logger.fatal(e);
            return null;
        } finally{
            client.shutdown();
        }
    }
}
