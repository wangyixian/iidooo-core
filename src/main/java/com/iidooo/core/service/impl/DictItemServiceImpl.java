package com.iidooo.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.core.mapper.DictItemMapper;
import com.iidooo.core.model.po.DictItem;
import com.iidooo.core.service.DictItemService;

@Service
public class DictItemServiceImpl implements DictItemService{
    private static final Logger logger = Logger.getLogger(DictItemServiceImpl.class);

    @Autowired
    private DictItemMapper dictItemMapper;
    
    @Override
    public List<DictItem> getDictItemsByClassCode(String dictClassCode) {
        try {
            List<DictItem> result = dictItemMapper.selectByClassCode(dictClassCode);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            throw e;
        }
    }
}
