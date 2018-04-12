package com.fk.security.browser;


import com.fk.security.browser.authentication.FkFailueAuthentication;
import com.fk.security.browser.authentication.FkSuccessAuthentication;
import com.fk.security.core.authentication.AbstractChannelSecurityConfig;
import com.fk.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fk.security.core.properties.SecurityConstants;
import com.fk.security.core.properties.SecurityProperties;
import com.fk.security.core.validate.code.ValidateCodeProcessorHolder;
import com.fk.security.core.validate.code.ValidateCodeSecurityConfig;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;


/**
 * @author fankun
 * @date 2018/3/7 14:37
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private FkSuccessAuthentication fkSuccessAuthentication;
    @Autowired
    private FkFailueAuthentication fkFailueAuthentication;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
@Autowired
private SpringSocialConfigurer fkSocialSecurityConfigurer;
@Autowired
private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
@Autowired
private InvalidSessionStrategy invalidSessionStrategy;
@Autowired
private LogoutSuccessHandler logoutSuccessHandler;
    /**
     * security记住我功能，用户第一次登录成功后会将用户名token等存入数据库persistent_logins表.
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        validateCodeFilter.setAuthenticationFailureHandler(fkFailueAuthentication);
//        validateCodeFilter.setSecurityProperties(securityProperties);
//        validateCodeFilter.afterPropertiesSet();
//        validateCodeFilter.setValidateCodeProcessorHolder(validateCodeProcessorHolder);
        //在调用UsernamePasswordAuthenticationFilter之前进行验证码验证

            //登录页面的配置

//            http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()//使用表单验证
//                    //表单验证采用自定义的认证
//                    .loginPage("/authentication/require")
//                    //表单认证路径
//                    .loginProcessingUrl("/security/login")
//                    //登录成功跳转
//                    .successHandler(fkSuccessAuthentication)
//                    //登录失败处理
//                    .failureHandler(fkFailueAuthentication)
//                    .and()
 applyPasswordAuthenticationConfig(http);
            //校验码相关配置
            http.apply(validateCodeSecurityConfig)
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .apply(fkSocialSecurityConfigurer)
                    .and()
                    .sessionManagement()
                        .invalidSessionStrategy(invalidSessionStrategy)
                        .maximumSessions(securityProperties.getBrowserProperties().getSession().getMaximumSessions())
                        .maxSessionsPreventsLogin(securityProperties.getBrowserProperties().getSession().isMaxSessio0nPreventsLogin())
                        .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                    .and()
                    .logout()
                        .logoutUrl("/signOut")
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .deleteCookies("JSESSIONID")
                    .deleteCookies("SESSION")
                        .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowserProperties().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                .and()//并且
                    .authorizeRequests()//对请求授权
                    .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                            securityProperties.getBrowserProperties().getLoginPage(),
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                            securityProperties.getBrowserProperties().getSignUpUrl(),
//                            securityProperties.getBrowserProperties().getSession().getSessionInvalidUrl()+".json",
//                            securityProperties.getBrowserProperties().getSession().getSessionInvalidUrl()+".html",
                            securityProperties.getBrowserProperties().getSession().getSessionInvalidUrl(),
                            securityProperties.getBrowserProperties().getSignOutUrl(),
                            "/user/regist").permitAll()
                    .anyRequest()//对任何请求
                    .authenticated()//需要身份认证
                    .and()
                 .csrf().disable();
    }
}
