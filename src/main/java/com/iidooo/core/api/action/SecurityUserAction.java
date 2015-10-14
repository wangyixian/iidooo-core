package com.iidooo.core.api.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.iidooo.core.action.common.BaseAPIAction;
import com.iidooo.core.api.service.SecurityUserService;
import com.iidooo.core.constant.ClassConstant;
import com.iidooo.core.constant.HttpConstant;
import com.iidooo.core.dto.extend.SecurityUserDto;

public class SecurityUserAction extends BaseAPIAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SecurityUserAction.class);

    @Autowired
    private SecurityUserService securityUserService;

    public void user() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            
            String method = request.getMethod();
            switch (method) {
            case HttpConstant.METHOD_GET:
                String userID = request.getParameter(ClassConstant.FIELD_SECURITY_USER_ID);
                if (userID == null || userID.isEmpty()) {
                    return;
                }  

                SecurityUserDto securityUser = securityUserService.getSecurityUser(Integer.parseInt(userID));
                JSONObject jsonObject = JSONObject.fromObject(securityUser);
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setContentType(HttpConstant.CONTENT_TYPE_JSON);
                response.setCharacterEncoding(HttpConstant.CHARACTER_ENCODING_UTF8);
                PrintWriter writer = response.getWriter();
                writer.write(jsonObject.toString());
                writer.close();                
                break;

            default:
                break;
            }

            

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

}
