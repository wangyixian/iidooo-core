package com.iidooo.core.listener;

import java.io.Reader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.iidooo.core.util.MybatisUtil;
import com.iidooo.core.util.StringUtil;

public class MybatisInitListener extends HttpServlet implements ServletContextListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(MybatisInitListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            ServletContext sc = arg0.getServletContext();
            String resource = sc.getInitParameter("resource");

            // 未配置mybatis的启动参数，配置文件路径
            if (StringUtil.isBlank(resource)) {
                logger.error("reource configuration error, please check carefully.");
            } else {
                Reader reader = Resources.getResourceAsReader(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                MybatisUtil.setSqlSessionFactory(sqlSessionFactory);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }

    }

}
