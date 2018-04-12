package com.fk.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fk.security.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fankun
 * @date 2018/3/31 10:50
 */
public class FkLogoutSuccesshandler implements LogoutSuccessHandler {
    private String signOutUrl ;

    private ObjectMapper objectMapper = new ObjectMapper();
    public FkLogoutSuccesshandler ( String signOutUrl){
        this.signOutUrl = signOutUrl;
    }
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(StringUtils.isBlank(signOutUrl)){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功了哦~")));
        }else {
            response.sendRedirect(signOutUrl);
        }
    }
}
