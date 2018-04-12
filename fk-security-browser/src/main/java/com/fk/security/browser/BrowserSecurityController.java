package com.fk.security.browser;

import com.fk.security.core.properties.SecurityProperties;
import com.fk.security.core.support.SimpleResponse;
import com.fk.security.core.support.SocialUserInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fankun
 * @date 2018/3/9 18:07
 */
@RestController
public class BrowserSecurityController {

    /**
     * 发送的请求会放在此类中
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 用于发送重定向请求
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义表单验证：如果跳转过来的是".html"请求则直接重定向到登录页面，如果不是则抛出异常信息前台根据异常处理
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SimpleResponse rquiredAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            String redirectUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是:" + redirectUrl);
            if(StringUtils.endsWithIgnoreCase(redirectUrl,".html")){
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowserProperties().getLoginPage());
            }
        }
return  new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }
@GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
    Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
    SocialUserInfo socialUserInfo = new SocialUserInfo();
    socialUserInfo.setProviderId(connection.getKey().getProviderId());
    socialUserInfo.setProviderUserId(connection.getKey().getProviderUserId());
    socialUserInfo.setNickname(connection.getDisplayName());
    socialUserInfo.setHeadimg(connection.getImageUrl());
    return socialUserInfo;
}
}
