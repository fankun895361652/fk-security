package com.fk.security.core.properties;

/**
 * @author fankun
 * @date 2018/3/15 9:47
 */
public class SecurityConstants {
    /**
     * 默认的处理验证码的url前缀
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 默认的用户名密码登录请求处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/security/login";
    /**
     * 默认的手机验证码登录请求处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/security/sms";

    /**
     * 默认的OPENID登录请求处理url
     */
    public static final   String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/security/openid";
    /**
     * 当请求需要身份认证时，默认跳转的url
     *
     * @see
     */
    public static final String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
    /**
     * session失效默认的跳转地址由于代码做了处理，所以最后表现的是 resources/session/invalid.html 页面
     */
    public static final String DEFAULT_SESSION_INVALID_URL = "/session/invalid";
    /**
     * 默认登录页面
     *
     * @see
     */
    public static final String DEFAULT_LOGIN_PAGE_URL = "/defaultLogin.html";
    /**
     * openid参数名
     */
    public static final String DEFAULT_PARAMETER_NAME_OPENID = "openId";
    /**
     * providerId参数名
     */
    public static final String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
}
