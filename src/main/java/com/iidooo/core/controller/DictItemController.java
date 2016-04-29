package com.iidooo.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iidooo.core.enums.MessageLevel;
import com.iidooo.core.enums.MessageType;
import com.iidooo.core.enums.ResponseStatus;
import com.iidooo.core.model.Message;
import com.iidooo.core.model.ResponseResult;
import com.iidooo.core.model.po.DictItem;
import com.iidooo.core.service.DictItemService;
import com.iidooo.core.util.StringUtil;

@Controller
public class DictItemController {
    private static final Logger logger = Logger.getLogger(DictItemController.class);
    

    @Autowired
    private DictItemService dictItemService;
    
    @ResponseBody
    @RequestMapping(value = "/core/getDictItemList", method = RequestMethod.POST)
    public ResponseResult getDictItemList(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String classCode = request.getParameter("dictClassCode");
            if (StringUtil.isBlank(classCode)) {
                Message message = new Message(MessageType.FieldRequired.getCode(), MessageLevel.WARN, "dictClassCode");
                message.setDescription("The filed of dictClassCode is required!");
                result.getMessages().add(message);
                result.setStatus(ResponseStatus.Failed.getCode());
                return result;
            }
            
            List<DictItem> dictItems = dictItemService.getDictItemsByClassCode(classCode);

            // 返回找到的内容对象
            result.setStatus(ResponseStatus.OK.getCode());
            result.setData(dictItems);

        } catch (Exception e) {
            logger.fatal(e);
            Message message = new Message(MessageType.Exception.getCode(), MessageLevel.FATAL);
            message.setDescription(e.toString());
            result.getMessages().add(message);
            result.setStatus(ResponseStatus.Failed.getCode());
        }
        return result;
    }
}
