package com.fk.security.app;

import com.fk.security.core.properties.OAuth2ClientProperties;
import com.fk.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author fankun
 * @date 2018/4/2 12:12
 */
@Configuration
@EnableAuthorizationServer
public class FkAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //clients.inMemory() 表示clients信息是存在内存中，如果是存在数据库则clients.jdbc(datasource)
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        //自己配置的client信息，表示允许哪些client获取token
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getOauth2Client())) {
            for (OAuth2ClientProperties properties : securityProperties.getOauth2().getOauth2Client()){
                builder.withClient(properties.getClientId())
                        .secret(properties.getClientSecret())
                        .accessTokenValiditySeconds(properties.getExperTime())
                        .scopes("all","write","read")
                        .authorizedGrantTypes("refresh_token","password");
            }
        }
    }
}
