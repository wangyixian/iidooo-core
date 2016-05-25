package com.iidooo.core.util;

import org.apache.log4j.Logger;

import com.iidooo.core.model.Page;

public class PageUtil {
    private static final Logger logger = Logger.getLogger(PageUtil.class);
    
    public static Page executePage(int recordSum, Page page) {
        Page result = page;
        try {           
            if (recordSum <= 0) {
                return result;
            }
            
            result.setRecordSum(recordSum);    
            
            // 算出总页数
            int pageSum = result.getRecordSum() / result.getPageSize();
            if (result.getRecordSum() % result.getPageSize() > 0) {
                pageSum++;
            }                
            result.setPageSum(pageSum);
            
            if (result.getCurrentPage() <= result.getPageSum()) {
                result.setStart((result.getCurrentPage() - 1) * result.getPageSize());
            } else {
                result.setStart((result.getPageSum() - 1) * result.getPageSize());
            }
            result.setEnd(result.getStart() + result.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e.toString());
        }
        return result;
    }
}
