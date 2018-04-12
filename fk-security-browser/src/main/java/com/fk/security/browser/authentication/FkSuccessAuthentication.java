package com.fk.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fk.security.core.properties.LoginType;
import com.fk.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功页面处理
 * @author fankun
 * @date 2018/3/12 15:40
 */
@Component("fkSuccessAuthentication")
public class FkSuccessAuthentication extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(LoginType.JSON.equals(securityProperties.getBrowserProperties().getLoginType())){
            response.setContentType("application/json,charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }
        else {
            super.onAuthenticationSuccess(httpServletRequest, response, authentication);
        }

    }
}
