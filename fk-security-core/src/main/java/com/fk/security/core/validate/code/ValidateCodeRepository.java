package com.fk.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author fankun
 * @date 2018/4/3 9:44
 */
public interface ValidateCodeRepository {
    /**
     * 保存校验码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request,ValidateCode code,ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request,ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request,ValidateCodeType validateCodeType);
}
