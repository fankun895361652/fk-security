package com.fk.security.app.social;

import com.fk.security.app.AppSecretException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author fankun
 * @date 2018/4/4 11:09
 */
@Component
public class AppSignUtils {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    /**
     * 缓存社交网站用户信息
     *
     * @param request
     * @param data
     */
    public void saveConnectionData(ServletWebRequest request, ConnectionData data) {
        redisTemplate.opsForValue().set(getKey(request), data, 10, TimeUnit.MINUTES);
        System.out.println("git test");
    }

    /**
     * 将缓存的社交网站用户信息与系统注册用户信息绑定
     *
     * @param request
     * @param userId
     */
    public void doPostSignUp(ServletWebRequest request, String userId) {
        String key = getKey(request);
        if (null == key) {
            throw new AppSecretException("无法找到缓存的用户社交账号信息");
        }
        ConnectionData data = (ConnectionData) redisTemplate.opsForValue().get(key);
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(data.getProviderId()).createConnection(data);
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
        redisTemplate.delete(key);
    }

    private String getKey(ServletWebRequest request) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new AppSecretException("设备id参数不能为空");
        }
        return "fk:security:social.connect." + deviceId;
    }
}
