package com.iidooo.core.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.iidooo.core.constant.HttpConstant;

public class JsonUtil {
    
    private static final Logger logger = Logger.getLogger(JsonUtil.class);
    
    public static void responseObject(Object object, HttpServletResponse response){
        try {
            JSONObject jsonObject = JSONObject.fromObject(object);
            response.setContentType(HttpConstant.CONTENT_TYPE_JSON);
            response.setCharacterEncoding(HttpConstant.CHARACTER_ENCODING_UTF8);
            PrintWriter writer = response.getWriter();
            writer.write(jsonObject.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
