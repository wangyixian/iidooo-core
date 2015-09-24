package com.iidooo.core.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.iidooo.core.constant.HttpConstant;
import com.iidooo.core.constant.RestfulConstant;
import com.iidooo.core.dto.PageDto;

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
    
    public static void responseObjectArray(Object array, HttpServletResponse response ){
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = JSONArray.fromObject(array);
            jsonObject.put(RestfulConstant.REST_API_RESULT_LIST, jsonArray);
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
    
    public static void responseObjectArray(Object array, PageDto page, HttpServletResponse response ){
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = JSONArray.fromObject(array);
            jsonObject.put(RestfulConstant.REST_API_RESULT_PAGE, page);
            jsonObject.put(RestfulConstant.REST_API_RESULT_LIST, jsonArray);
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
