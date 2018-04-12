package com.fk.security.core.validate.code;

import com.fk.security.core.properties.SecurityProperties;
import com.fk.security.core.validate.code.image.ImageCodeGenerator;
import com.fk.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.fk.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fankun
 * @date 2018/3/13 16:52
 */
@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        ImageCodeGenerator validateCode = new ImageCodeGenerator();
        validateCode.setSecurityProperties(securityProperties);
        return validateCode;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
