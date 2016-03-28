package com.iidooo.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {

    private static final Logger logger = Logger.getLogger(PropertiesUtil.class);

    public static Properties loadProperties(String file) {

        Properties pro = null;
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(file);
            pro = new Properties();
            pro.load(in);

        } catch (Exception e) {
            logger.fatal(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.fatal(e);
            }
        }
        return pro;
    }
}
