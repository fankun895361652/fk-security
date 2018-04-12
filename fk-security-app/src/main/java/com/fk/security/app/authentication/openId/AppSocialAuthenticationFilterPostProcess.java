package com.fk.security.app.authentication.openId;

import com.fk.security.core.social.SocialAuthenticationFilterPostProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author fankun
 * @date 2018/4/4 10:12
 */
@Component
public class AppSocialAuthenticationFilterPostProcess implements SocialAuthenticationFilterPostProcess {
    @Autowired
    private AuthenticationSuccessHandler fkSuccessAuthentication;
    @Override
    public void process(SocialAuthenticationFilter authenticationFilter) {
        authenticationFilter.setAuthenticationSuccessHandler(fkSuccessAuthentication);
    }
}
