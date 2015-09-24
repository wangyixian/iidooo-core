package com.iidooo.core.action.security;

import org.apache.log4j.Logger;

import com.iidooo.core.action.common.BaseAction;

public class IndexAction extends BaseAction{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(IndexAction.class);
    
    public String init() {

        return SUCCESS;
    }
}
