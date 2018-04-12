package com.fk.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fankun
 * @date 2018/3/12 10:13
 */
@Data
@ConfigurationProperties(prefix = "fk.security")
public class SecurityProperties {

    private BrowserProperties browserProperties = new BrowserProperties();
    private ValidateCodeProperties code = new ValidateCodeProperties();
    private SocialProperties social = new SocialProperties();
private OAuth2Properties oauth2 = new OAuth2Properties();
}
