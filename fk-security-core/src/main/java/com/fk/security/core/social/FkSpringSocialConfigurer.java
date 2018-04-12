package com.fk.security.core.social;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author fankun
 * @date 2018/3/22 16:02
 */
@Data
public class FkSpringSocialConfigurer extends SpringSocialConfigurer {
    private String filterProcessesUrl;
    private String signupUrl;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SocialConfig socialConfig;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    private SocialAuthenticationFilterPostProcess socialAuthenticationFilterPostProcess;

    public FkSpringSocialConfigurer (String filterProcessesUrl,String signupUrl){
        this.filterProcessesUrl = filterProcessesUrl;
        this.signupUrl = signupUrl;
    }

    public FkSpringSocialConfigurer (String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
//        filter.setSignupUrl(signupUrl);
        if(socialAuthenticationFilterPostProcess != null){
            socialAuthenticationFilterPostProcess.process(filter);
        }
        return (T) filter;
    }

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//        ApplicationContext applicationContext = (ApplicationContext)http.getSharedObject(ApplicationContext.class);
//        UsersConnectionRepository usersConnectionRepository = (JdbcUsersConnectionRepository)socialConfig.getUsersConnectionRepository(connectionFactoryLocator);
//        SocialAuthenticationServiceLocator authServiceLocator = (SocialAuthenticationServiceLocator)applicationContext.getBean(SocialAuthenticationServiceLocator.class);
//        SocialUserDetailsService socialUsersDetailsService = (SocialUserDetailsService) userDetailsService;
//        SocialAuthenticationFilter filter = new SocialAuthenticationFilter((AuthenticationManager)http.getSharedObject(AuthenticationManager.class),  new AuthenticationNameUserIdSource(), usersConnectionRepository, authServiceLocator);
//        RememberMeServices rememberMe = (RememberMeServices)http.getSharedObject(RememberMeServices.class);
//        if (rememberMe != null) {
//            filter.setRememberMeServices(rememberMe);
//        }
//        if (this.signupUrl != null) {
//            filter.setSignupUrl(this.signupUrl);
//        }
////        QQSocialAuthenticationProvider provider = new QQSocialAuthenticationProvider(usersConnectionRepository,socialUsersDetailsService);
//        http.authenticationProvider(new UserSocialAuthenticationProvider(usersConnectionRepository, socialUsersDetailsService)).addFilterBefore((Filter)this.postProcess(filter), AbstractPreAuthenticatedProcessingFilter.class);
//    }

//    @Override
//    public SpringSocialConfigurer signupUrl(String signupUrl) {
//        this.signupUrl = signupUrl;
//        return this;
//    }
}
