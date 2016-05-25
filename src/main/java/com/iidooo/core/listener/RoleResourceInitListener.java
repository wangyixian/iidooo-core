package com.iidooo.core.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.iidooo.core.constant.ServletConstant;
import com.iidooo.core.mapper.SecurityRoleMapper;
import com.iidooo.core.model.po.SecurityResource;
import com.iidooo.core.model.po.SecurityRole;
import com.iidooo.core.util.SpringUtil;

public class RoleResourceInitListener extends HttpServlet implements ServletContextListener {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(RoleResourceInitListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            ServletContext sc = arg0.getServletContext();
            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringUtil.getBean(sc, "sqlSessionFactory");

            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            SecurityRoleMapper roleMapper = sqlSession.getMapper(SecurityRoleMapper.class);
            List<SecurityRole> roleList = roleMapper.selectAll();

            // key: roleID value:List<SecurityResource>
            Map<Integer, List<SecurityResource>> roleResourceMap = new HashMap<Integer, List<SecurityResource>>();
            for (SecurityRole item : roleList) {
                roleResourceMap.put(item.getRoleID(), item.getResourceList());
            }
            sc.setAttribute(ServletConstant.ROLE_RESOURCE_MAP, roleResourceMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

}
