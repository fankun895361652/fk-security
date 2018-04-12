package com.fk.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 将产生的Token保存在redis中，而不是内存中（默认）
 * @author fankun
 * @date 2018/4/4 17:57
 */
@Configuration
public class TokenStoreConfiger {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore redisTokenStore(){
return new RedisTokenStore(redisConnectionFactory);
    }
}
