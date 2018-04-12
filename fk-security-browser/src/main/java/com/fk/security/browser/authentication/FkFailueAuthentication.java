package com.fk.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fk.security.core.properties.LoginType;
import com.fk.security.core.properties.SecurityProperties;
import com.fk.security.core.support.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败页面处理
 * @author fankun
 * @date 2018/3/12 15:47
 */
@Component("fkFailueAuthentication")
public class FkFailueAuthentication extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if(LoginType.JSON.equals(securityProperties.getBrowserProperties().getLoginType())){
            response.setContentType("application/json,charset=UTF-8");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        }else {
           super.onAuthenticationFailure(httpServletRequest, response, e);
        }
    }
}
