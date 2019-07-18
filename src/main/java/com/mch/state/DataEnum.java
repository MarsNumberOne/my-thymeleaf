package com.mch.state;

import lombok.Getter;

/**
 * @description: Copyright 2011-2018 B5M.COM. All rights reserved
 * @author: yangren
 * @version: 1.0
 * @createdate: 2018/10/12
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2018/10/12      yangren       1.0             Why & What is modified
 */

public enum DataEnum {

    BAND(0000001,"坏的"),


    ;
    @Getter
    private Integer code;

    @Getter
    private String msg;

    DataEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
