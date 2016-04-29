package com.iidooo.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtil {
    private static final Logger logger = Logger.getLogger(DateUtil.class);

    public static final String DATE_TIME_FULL_HYPHEN = "yyyy-MM-dd HH:mm:ss:sss";

    public static final String DATE_TIME_HYPHEN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_HYPHEN = "yyyy-MM-dd";

    public static final String DATE_TIME_FULL_SLASH = "yyyy/MM/dd HH:mm:ss:sss";

    public static final String DATE_TIME_SLASH = "yyyy/MM/dd HH:mm:ss";

    public static final String DATE_SLASH = "yyyy/MM/dd";

    public static final String DATE_SIMPLE = "yyyyMMdd";

    public static final String DATE_YEAR_MONTH_SIMPLE = "yyyyMM";

    public static final String DATE_TIME_FULL_SIMPLE = "yyyyMMddHHmmsssss";

    public static final String TIME_COLON = "HH:mm:ss";

    public static String getNow(String format) {
        try {
            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String now = sdf.format(nowDate);
            return now;
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }

    public static String format(String inputStr, String inputFormat, String outputFormat) throws Exception {
        try {
            if (inputStr == null || inputStr.isEmpty()) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
            Date inputDate = sdf.parse(inputStr);

            sdf = new SimpleDateFormat(outputFormat);
            String outputStr = sdf.format(inputDate);
            return outputStr;
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

    public static boolean isFormat(String inputStr, String format) throws Exception {
        try {
            if (inputStr == null || inputStr.isEmpty()) {
                return false;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (sdf.parse(inputStr) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.fatal(e);
            return false;
        }
    }

    /**
     * 两个日期相减
     * 
     * @param subtrahend 减数日期，格式：yyyyMMddHHmmssssssss
     * @param minuend 被减数日期，格式：yyyyMMddHHmmssssssss
     * @return 相差多少毫秒
     */
    public static int subtract(String subtrahendDate, String subtrahendFormat, String minuendDate, String minuendFormat) {
        try {
            // 得到减数日期值
            int subtrahend = 0;
            if (!subtrahendFormat.equals(DateUtil.DATE_TIME_FULL_SIMPLE)) {
                subtrahendDate = DateUtil.format(subtrahendDate, subtrahendFormat, DateUtil.DATE_TIME_FULL_SIMPLE);
                subtrahend = Integer.parseInt(subtrahendDate);
            }

            // 得到被减数日期值
            int minuend = 0;
            if (!minuendFormat.equals(DateUtil.DATE_TIME_FULL_SIMPLE)) {
                minuendDate = DateUtil.format(minuendDate, minuendFormat, DateUtil.DATE_TIME_FULL_SIMPLE);
                minuend = Integer.parseInt(minuendDate);
            }

            return subtrahend - minuend;

        } catch (Exception e) {
            logger.fatal(e);
            return 0;
        }
    }

    /**
     * 把字符串日期格式转换成Date类型
     *
     * @param dateString 字符串日期
     * @param format 格式化
     * @return 变换后Date
     * @throws Exception
     */
    public static Date getDate(String dateString, String format) throws Exception {
        try {
            DateFormat df = new SimpleDateFormat(format);
            Date result = df.parse(dateString);
            return result;
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

    public static void main(String[] args) {
        Calendar calendar = GregorianCalendar.getInstance();
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(DateUtil.getNow(DateUtil.TIME_COLON));
    }
}
