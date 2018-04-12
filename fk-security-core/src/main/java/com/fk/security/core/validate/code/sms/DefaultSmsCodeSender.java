package com.fk.security.core.validate.code.sms;

/**
 * @author fankun
 * @date 2018/3/14 15:34
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机"+mobile+"发送短信验证码:"+code);
    }
}
