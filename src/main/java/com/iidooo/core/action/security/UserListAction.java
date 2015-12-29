package com.iidooo.core.action.security;

import java.util.List;

import org.apache.log4j.Logger;

import com.iidooo.core.action.BaseAction;
import com.iidooo.core.dto.PageDto;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.service.security.UserListService;
import com.iidooo.core.service.security.impl.UserListServiceImpl;
import com.iidooo.core.util.ValidateUtil;

public class UserListAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserListAction.class);

    private UserListService userListService;

    private List<SecurityUserDto> userList;

    private SecurityUserDto user;

    public List<SecurityUserDto> getUserList() {
        return userList;
    }

    public void setUserList(List<SecurityUserDto> userList) {
        this.userList = userList;
    }

    public SecurityUserDto getUser() {
        return user;
    }

    public void setUser(SecurityUserDto user) {
        this.user = user;
    }
    
    public UserListAction(){
        this.userListService = new UserListServiceImpl();
    }

    public String init() {
        try {
            int count = userListService.getUserListCount();
            this.executePage(count);

            this.userList = userListService.getUserList(this.getPage());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

    public String delete() {
        try {
            if (!userListService.deleteUser(this.user)) {
                addActionError(getText("MSG_USER_DELETE_FAILED", new String[] { user.getUserName() }));
                return INPUT;
            }
            addActionMessage(getText("MSG_USER_DELETE_SUCCESS", new String[] { user.getUserName() }));
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

    public void validateDelete() {
        try {
            if (user == null || ValidateUtil.isEmpty(user.getUserID())) {
                addActionError(getText("MSG_USER_ID_REQUIRE"));
            }          
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
