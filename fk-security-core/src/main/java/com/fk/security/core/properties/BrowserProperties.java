package com.fk.security.core.properties;

import lombok.Data;

/**
 * @author fankun
 * @date 2018/3/12 12:11
 */
@Data
public class BrowserProperties {

    private SessionProperties session = new SessionProperties();
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    private String signUpUrl = "/regist.html";
private String signOutUrl ;
    private  LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 3600;

}
