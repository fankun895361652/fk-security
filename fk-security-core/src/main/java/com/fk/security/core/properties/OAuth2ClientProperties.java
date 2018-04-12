package com.fk.security.core.properties;

import lombok.Data;

/**
 * @author fankun
 * @date 2018/4/4 17:37
 */
@Data
public class OAuth2ClientProperties {
    private String clientId;
    private String clientSecret;
    private int experTime ;//当默认为0时，Token的有效期为永久单位秒

}
