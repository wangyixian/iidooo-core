package com.iidooo.core.action.security;

import java.util.Map;

import org.apache.log4j.Logger;

import com.iidooo.core.action.BaseAction;
import com.iidooo.core.constant.DateTimeFormat;
import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dto.extend.SecurityResDto;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.service.security.UserinfoService;
import com.iidooo.core.service.security.impl.UserinfoServiceImpl;
import com.iidooo.core.util.ValidateUtil;
import com.opensymphony.xwork2.ActionContext;

public class UserinfoAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserinfoAction.class);

    private UserinfoService userInfoService;

    private SecurityUserDto user;

    private SecurityResDto resource;

    public SecurityUserDto getUser() {
        return user;
    }

    public void setUser(SecurityUserDto user) {
        this.user = user;
    }

    public SecurityResDto getResource() {
        return resource;
    }

    public void setResource(SecurityResDto resource) {
        this.resource = resource;
    }
    
    public UserinfoAction(){
        this.userInfoService = new UserinfoServiceImpl();
    }

    public String init() {
        try {
            Map<String, Object> sessionMap = ActionContext.getContext().getSession();
            SecurityUserDto sessionUser = (SecurityUserDto) sessionMap.get(SessionConstant.LOGIN_USER);
            user = userInfoService.getUser(sessionUser.getLoginID());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

    public String save() {
        try {
            if (userInfoService.saveUser(user)) {
                addActionMessage(getText("MSG_SAVE_SUCCESS", new String[] { getText("LABEL_USERINFO_BASIC") }));
                user = userInfoService.getUser(user.getLoginID());
            } else {
                addActionError(getText("MSG_SAVE_FAILED", new String[] { getText("LABEL_USERINFO_BASIC") }));
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

    public void validateSave() {
        try {
            String birthday = user.getBirthday();
            if (!ValidateUtil.validateDateFormat(birthday, DateTimeFormat.DATE_HYPHEN)) {
                addActionError(this.getText("MSG_FIELD_FORMAT_ERROR", new String[] { getText("LABEL_USERINFO_BIRTHDAY") }));
            }

            String email = user.getEmail();
            if (!ValidateUtil.validateEmail(email)) {
                addActionError(this.getText("MSG_FIELD_FORMAT_ERROR", new String[] { getText("LABEL_USERINFO_EMAIL") }));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

}
