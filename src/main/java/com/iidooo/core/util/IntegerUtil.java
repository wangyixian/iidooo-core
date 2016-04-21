package com.iidooo.core.util;

import org.apache.log4j.Logger;

public class IntegerUtil {
    private static final Logger logger = Logger.getLogger(IntegerUtil.class);

    /**
     * 得到一个指定范围内的随机数
     * @param rank 指定的范围
     * @return 输出的值包含0的可能性，并且< rank
     */
    public static Integer getRandomNumber(Integer rank) {
        try {
            int n = (int) (Math.random() * rank);
            return n;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            throw e;
        }
    }

    /**
     * 获取一个随机数
     * 
     * @param rank 随机数的范围，例如10以内就填写10
     * @param isIncludeZero 是否包含0的Flag
     * @param isIncludeRank 是否包含自己，例如10以内，是否包含10自身
     * @return 获取到的随机数
     */
    public static Integer getRandomNumber(Integer rank, boolean isIncludeZero, boolean isIncludeRank) {
        try {
            if (isIncludeRank) {
                rank = rank + 1;
            }
            int n = (int) (Math.random() * rank);
            if (!isIncludeZero && n == 0) {
                while (n == 0) {
                    n = (int) (Math.random() * rank);
                }
            }

            return n;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            throw e;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(IntegerUtil.getRandomNumber(2, true, true));
        }
    }
}
