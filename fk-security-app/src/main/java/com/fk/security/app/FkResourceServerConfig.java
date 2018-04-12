package com.fk.security.app;

import com.fk.security.app.authentication.openId.OpenIdAuthenticationSecurityConfig;
import com.fk.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fk.security.core.properties.SecurityConstants;
import com.fk.security.core.properties.SecurityProperties;
import com.fk.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author fankun
 * @date 2018/4/2 15:05
 */
@Configuration
@EnableResourceServer
public class FkResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    protected AuthenticationSuccessHandler fkAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler fkAuthenticationFailureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    private SpringSocialConfigurer fkSocialSecurityConfigurer;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(fkAuthenticationSuccessHandler)
                .failureHandler(fkAuthenticationFailureHandler);
        //校验码相关配置
        http
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(fkSocialSecurityConfigurer)
                .and()//并且
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()//对请求授权
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowserProperties().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowserProperties().getSignUpUrl(),
//                            securityProperties.getBrowserProperties().getSession().getSessionInvalidUrl()+".json",
//                            securityProperties.getBrowserProperties().getSession().getSessionInvalidUrl()+".html",
                        securityProperties.getBrowserProperties().getSession().getSessionInvalidUrl(),
                        securityProperties.getBrowserProperties().getSignOutUrl(),
                        "/user/regist",
                        "/social/signUp").permitAll()
                .anyRequest()//对任何请求
                .authenticated()//需要身份认证
                .and()
                .csrf().disable();
    }
}
