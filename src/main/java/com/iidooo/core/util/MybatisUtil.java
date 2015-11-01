package com.iidooo.core.util;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class MybatisUtil {

    private static final Logger logger = Logger.getLogger(MybatisUtil.class);

    private static MybatisUtil instance;

    private SqlSessionFactory sqlSessionFactory;

    public static MybatisUtil Instance() {
        if (instance == null) {
            instance = new MybatisUtil();
        }
        return instance;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    private MybatisUtil() {
        this.initialize();
    }

    private void initialize() {
        try {
            String resource = "mybatis/Configuration.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
