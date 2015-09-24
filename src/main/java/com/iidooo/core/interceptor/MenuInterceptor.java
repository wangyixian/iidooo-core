package com.iidooo.core.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dto.extend.SecurityResDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class MenuInterceptor extends AbstractInterceptor {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(MenuInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        try {
            HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
            String path = request.getServletPath();

            @SuppressWarnings("static-access")
            ActionContext actionContext = invocation.getInvocationContext().getContext();
            Map<String, Object> application = actionContext.getApplication();
            @SuppressWarnings("unchecked")
            Map<String, SecurityResDto> securityResMap = (Map<String, SecurityResDto>) application.get(SessionConstant.RESOURCE_URL_MAP);

            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            
            SecurityResDto currentSecurityResDto = securityResMap.get(path);
            if (currentSecurityResDto == null) {
                logger.warn(request.getServletPath() + " is not contained in the system!");
                return invocation.invoke();
            }
            application.put(SessionConstant.CURRENT_RESOURCE, currentSecurityResDto);
            
            @SuppressWarnings("unchecked")
            List<SecurityResDto> securityResList = (List<SecurityResDto>) application.get(SessionConstant.RESOURCE_LIST);
            // Set the parent resource selected
            for (SecurityResDto item : securityResList) {
                item.setIsSelected(false);
                if (item.getResURL().equals(currentSecurityResDto.getResURL()) || 
                        item.equals(currentSecurityResDto) || item.getOffspring().contains(currentSecurityResDto)) {
                    item.setIsSelected(true);

                    if (item.getParentID() <= 0) {
                        application.put(SessionConstant.CURRENT_ROOT_RESOURCE, item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return invocation.invoke();
    }

}
