package com.fk.security.core.properties;

import lombok.Data;

/**
 * @author fankun
 * @date 2018/4/4 17:39
 */
@Data
public class OAuth2Properties {
    private OAuth2ClientProperties [] oauth2Client = {};
    private String jwtSignKey = "fankun";
}
