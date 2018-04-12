package com.fk.security.core.validate.code.sms;

/**
 * @author fankun
 * @date 2018/3/14 15:33
 */
public interface SmsCodeSender {

   void send(String mobile,String code);
}
