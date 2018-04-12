package com.fk.security.core.social.weixin.api;

/**
 * @author fankun
 * @date 2018/3/29 10:55
 */
public interface Weixin {
    WeixinUserInfo getUserInfo(String openId);
}
