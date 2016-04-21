package com.iidooo.core.service;

import java.util.List;

import com.iidooo.core.model.po.DictItem;

public interface DictItemService {
    List<DictItem> getDictItemsByClassCode(String dictClassCode);
}
