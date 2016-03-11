package com.iidooo.core.enums;

public enum MessageCode {

    Exception("Exception", 40001),
    
    FieldRequired("FieldRequired", 50001);
    
    private String name;
    
    private int value;
    
    private MessageCode(String name, int value){
        this.name = name;
        this.value = value;
    }
}
