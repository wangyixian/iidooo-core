package com.iidooo.core.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.iidooo.core.constant.DictConstant;
import com.iidooo.core.dao.extend.DictItemDao;
import com.iidooo.core.dto.extend.DictItemDto;
import com.iidooo.core.util.MybatisUtil;

public class DictInitListener extends HttpServlet implements ServletContextListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(DictInitListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            ServletContext sc = arg0.getServletContext();

            this.setDictAttributes(sc);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    private void setDictAttributes(ServletContext sc) {
        try {
            SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
            DictItemDao dictItemDao = sqlSession.getMapper(DictItemDao.class);
            this.setAttribute(sc, dictItemDao, DictConstant.DICT_CLASS_CORE_PROPERTIES);
            this.setAttribute(sc, dictItemDao, DictConstant.DICT_CLASS_CORE_PAGE);
            this.setAttribute(sc, dictItemDao, DictConstant.DICT_CLASS_CORE_UPLOAD);
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
}
