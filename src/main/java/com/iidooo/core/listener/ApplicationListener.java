package com.iidooo.core.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.iidooo.core.constant.ClassConstant;
import com.iidooo.core.constant.DictConstant;
import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dao.extend.DictItemDao;
import com.iidooo.core.dao.extend.SecurityResDao;
import com.iidooo.core.dto.extend.DictItemDto;
import com.iidooo.core.dto.extend.SecurityResDto;
import com.iidooo.core.util.SpringUtil;

public class ApplicationListener extends BaseListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ApplicationListener.class);

    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void contextInitialized(ServletContextEvent arg0) {
        try {
            ServletContext sc = arg0.getServletContext();

            this.setDictAttributes(sc);
            this.setResAttribute(sc);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    private void setDictAttributes(ServletContext sc) {
        try {
            DictItemDao dictItemDao = (DictItemDao) SpringUtil.getBean(sc, ClassConstant.BEAN_DICT_ITEM_DAO);
            this.setAttribute(sc, dictItemDao, DictConstant.DICT_CLASS_CORE_PROPERTIES);
            this.setAttribute(sc, dictItemDao, DictConstant.DICT_CLASS_CORE_PAGE);
            this.setAttribute(sc, dictItemDao, DictConstant.DICT_CLASS_CORE_UPLOAD);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    private void setResAttribute(ServletContext sc) {
        try {
            SecurityResDao securityResDao = (SecurityResDao) SpringUtil.getBean(sc, ClassConstant.BEAN_SECURITY_RES_DAO);
            List<SecurityResDto> resourceList = securityResDao.selectAll();
            this.constructResourceRelation(resourceList);

            // Save the resource list and will be used in MenuInterceptor
            sc.setAttribute(SessionConstant.RESOURCE_LIST, resourceList);

            // Key: ResourceID
            // Value: ResourceDto
            Map<Integer, SecurityResDto> resourceIDMap = new HashMap<Integer, SecurityResDto>();
            // Key: ResourceURL
            // Value: ResourceDto
            Map<String, SecurityResDto> resourceURLMap = new HashMap<String, SecurityResDto>();
            // Key: ResourceURL
            // Value: ResourceDto
            Map<String, SecurityResDto> rootResourceURLMap = new HashMap<String, SecurityResDto>();

            for (SecurityResDto item : resourceList) {
                if (item.getParentID() <= 0) {
                    rootResourceURLMap.put(item.getResURL(), item);
                }
                resourceIDMap.put(item.getResID(), item);
                resourceURLMap.put(item.getResURL(), item);
            }
            sc.setAttribute(SessionConstant.RESOURCE_ID_MAP, resourceIDMap);
            // Save the resource map and will be used in MenuInterceptor
            sc.setAttribute(SessionConstant.RESOURCE_URL_MAP, resourceURLMap);
            sc.setAttribute(SessionConstant.ROOT_RESOURCE_URL_MAP, rootResourceURLMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    private void setAttribute(ServletContext sc, DictItemDao dictItemDao, String classCode) {
        try {
            List<DictItemDto> dictItemList = dictItemDao.selectByClassCode(classCode);
            // Save the page properties into the application context
            Map<String, DictItemDto> dictItemMap = new HashMap<String, DictItemDto>();
            for (DictItemDto dictItemDto : dictItemList) {
                dictItemMap.put(dictItemDto.getDictItemCode(), dictItemDto);
            }
            sc.setAttribute(classCode, dictItemMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    private void constructResourceRelation(List<SecurityResDto> resourceList) {
        try {

            // Key: ResourceID
            // Value: ResourceDto
            Map<Integer, SecurityResDto> resourceIDMap = new HashMap<Integer, SecurityResDto>();

            // Construct the result map first.
            for (SecurityResDto item : resourceList) {
                resourceIDMap.put(item.getResID(), item);
            }

            // Set the security res's children list
            for (SecurityResDto item : resourceList) {
                if (item.getParentID() > 0) {
                    // Set the child into the parent module.
                    SecurityResDto parent = resourceIDMap.get(item.getParentID());
                    if (parent == null) {
                        continue;
                    }
                    parent.getChildren().add(item);
                }
            }

            // Set the security res's off spring list.
            for (SecurityResDto item : resourceList) {
                SecurityResDto parent = resourceIDMap.get(item.getParentID());
                while (parent != null) {
                    parent.getOffspring().add(item);
                    parent = resourceIDMap.get(parent.getParentID());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
