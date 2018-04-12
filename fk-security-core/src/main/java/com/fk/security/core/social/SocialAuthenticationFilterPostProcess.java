package com.fk.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author fankun
 * @date 2018/4/4 10:05
 */
public interface SocialAuthenticationFilterPostProcess {
    void process(SocialAuthenticationFilter authenticationFilter);
}
