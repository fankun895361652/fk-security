package com.fk.security.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;
/**
 * @author fankun
 * @date 2018/3/29 11:14
 */
@Data
public class WeixinProperties extends SocialProperties {
    private String providerId = "weixin";
}
