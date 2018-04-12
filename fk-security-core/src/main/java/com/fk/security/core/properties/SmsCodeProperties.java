package com.fk.security.core.properties;

import lombok.Data;

/**
 * @author fankun
 * @date 2018/3/13 15:14
 */
@Data
public class SmsCodeProperties {

    private int length = 6;
    private int experieIn = 60;
    private String url = "";
}
