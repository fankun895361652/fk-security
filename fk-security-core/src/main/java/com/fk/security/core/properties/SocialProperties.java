package com.fk.security.core.properties;

import lombok.Data;

/**
 * @author fankun
 * @date 2018/3/19 15:59
 */
@Data
public class SocialProperties {
    private String filterProcessesUrl = "/auth";
    QQProperties qq = new QQProperties();
    WeixinProperties weixin = new WeixinProperties();
}
