package com.fk.security.core.social.qq.connect;


import com.fk.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author fankun
 * @date 2018/3/19 15:38
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {


    public QQConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());

    }

}
