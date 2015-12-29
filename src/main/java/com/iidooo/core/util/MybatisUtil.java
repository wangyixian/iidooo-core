package com.iidooo.core.util;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

public class MybatisUtil {

    private static final Logger logger = Logger.getLogger(MybatisUtil.class);

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public static void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        MybatisUtil.sqlSessionFactory = sqlSessionFactory;
    }

}
