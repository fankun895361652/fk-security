package com.fk.security.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author fankun
 * @date 2018/3/19 15:58
 */
@Data
public class QQProperties extends SocialProperties {
    private String providerId = "qq";
}
