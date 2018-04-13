package com.fk.security.app;

import com.fk.security.app.jwt.FkJwtTokenEnhancer;
import com.fk.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 将产生的Token保存在redis中，而不是内存中（默认）
 *
 * @author fankun
 * @date 2018/4/4 17:57
 */
@Configuration
@ConditionalOnProperty(prefix = "fk.security.oauth2",name = "storeType",havingValue = "redis")
public class TokenStoreConfiger {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
    @Configuration
    @ConditionalOnProperty(prefix = "fk.security.oauth2",name = "storeType",havingValue = "jwt",matchIfMissing = true)
    public static class JwtTokenStoreConfiger{
        @Autowired
        private SecurityProperties securityProperties;
        @Bean
        public TokenStore JwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(securityProperties.getOauth2().getJwtSignKey());
            return converter;
        }
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            FkJwtTokenEnhancer fkJwtTokenEnhancer = new FkJwtTokenEnhancer();
            return fkJwtTokenEnhancer;
        }
    }
}
