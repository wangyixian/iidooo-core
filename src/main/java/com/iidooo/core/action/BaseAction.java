package com.iidooo.core.action;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.iidooo.core.dto.PageDto;
import com.iidooo.core.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * The abstract of common base action
 * 
 * @author wangyixian
 * 
 */
public abstract class BaseAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(BaseAction.class);

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public static String RESULT_SECURITY = "security";
    
    private String actionName;

    private String actionURL;
    
    private PageDto page;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }
    
    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    /**
     * Get the session
     * 
     * @return Map<String, Object> session
     */
    public Map<String, Object> getSession() {

        ActionContext ac = ActionContext.getContext();
        Map<String, Object> session = ac.getSession();
        return session;
    }

    public Map<String, Object> getApplication() {
        try {
            ActionContext ac = ActionContext.getContext();
            Map<String, Object> application = ac.getApplication();
            return application;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public String getRequestParameter(String key) {
        try {
            HttpServletRequest request = this.getRequest();
            String value = request.getParameter(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public String getRequestMethod() {
        try {
            HttpServletRequest request = this.getRequest();
            String value = request.getMethod();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public String getRequestURL() {
        try {
            HttpServletRequest request = this.getRequest();

            StringBuffer url = request.getRequestURL();
            String queryString = request.getQueryString();
            if (StringUtil.isNotBlank(queryString)) {
                url.append("?").append(queryString);
            }
            return url.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return "";
        }
    }

    /**
     * Get the object from session by key
     * 
     * @param key The key to get the session object
     * @return The object gotten.
     */
    public Object getSessionValue(String key) {
        try {
            Map<String, Object> session = this.getSession();
            Object value = session.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    /**
     * Set the value into the session with a key.
     * 
     * @param key This key is as the session map key.
     * @param value This value will be set into the session with the key.
     */
    public void setSessionValue(String key, Object value) {
        try {
            ActionContext ac = ActionContext.getContext();
            Map<String, Object> session = ac.getSession();
            session.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public void removeSessionKey(String key) {
        try {
            ActionContext ac = ActionContext.getContext();
            Map<String, Object> session = ac.getSession();
            if (session.containsKey(key)) {
                session.remove(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public boolean sessionContainKey(String key) {
        try {
            ActionContext ac = ActionContext.getContext();
            Map<String, Object> session = ac.getSession();
            if (session.containsKey(key)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return false;
        }
    }

    public Object getApplicationValue(String key) {
        try {
            ActionContext ac = ActionContext.getContext();
            Map<String, Object> application = ac.getApplication();
            Object value = application.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    /**
     * Get the HttpServletRequest
     * 
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ServletActionContext.getRequest();
        return request;
    }

    /**
     * Get the HttpServletResponse
     * 
     * @return HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        HttpServletResponse response = ServletActionContext.getResponse();
        return response;
    }

    public ServletContext getServletContext() {
        ServletContext servletContext = ServletActionContext.getServletContext();
        return servletContext;
    }
    
    /**
     * Execute the page action.
     * 
     * @param count The record count of data
     * @param page The input page object
     * @return The new page object
     */
    protected void executePage(int count) {
        try {

            if (page == null) {
                page = new PageDto(); 
                
                // Set the page default size
//                @SuppressWarnings("unchecked")
//                Map<String, DictItemDto> pageDictItemMap = (Map<String, DictItemDto>) sc.getAttribute(DictConstant.DICT_CLASS_CORE_PAGE);
//                DictItemDto pageSize = pageDictItemMap.get(DictConstant.DICT_ITEM_DEFAULT_PAGE_SIZE);
//                page.setPageSize(Integer.parseInt(pageSize.getDictItemValue()));
//            } else {
//                result.setSortField(page.getSortField());
//                result.setSortType(page.getSortType());
//                result.setCurrentPage(page.getCurrentPage());
            }

            // Set paging dto fields.
            page.setPageSize(page.getPageSize());
            page.setRecordSum(count);

            int pageSum = count / page.getPageSize();
            if (count % page.getPageSize() > 0) {
                pageSum++;
            }
            page.setPageSum(pageSum);

            page.setStart((page.getCurrentPage() - 1) * page.getPageSize());
            page.setEnd(page.getStart() + page.getPageSize());

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e.toString());
        }
    }
}
