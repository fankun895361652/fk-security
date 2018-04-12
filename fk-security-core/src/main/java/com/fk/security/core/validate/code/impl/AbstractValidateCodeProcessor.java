package com.fk.security.core.validate.code.impl;

import com.fk.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author fankun
 * @date 2018/3/15 10:05
 */

public abstract class AbstractValidateCodeProcessor < C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成校验码
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
//        List<ValidateCodeGenerator> list = (List<ValidateCodeGenerator>) validateCodeGenerators.values();
//        for (ValidateCodeGenerator v :list){
//            System.out.println(v.getClass().getSimpleName()+"---------------");
//        }
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 发送校验码由其子类实现
     * @param request
     * @param validateCode
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 保存校验码
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
        validateCodeRepository.save(request, code,getValidateCodeType(request));
//        sessionStrategy.setAttribute(request,getSessionKey(request),code );
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
private ValidateCodeType getValidateCodeType(ServletWebRequest request){
    String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
    return ValidateCodeType.valueOf(type.toUpperCase());
}
    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType validateCodeType = getValidateCodeType(request);

//        String sessionKey = getSessionKey(request);
//        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);
        C codeInSession = (C) validateCodeRepository.get(request, validateCodeType);
        String codeInRequest ;
        try {
             codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), validateCodeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(validateCodeType+"验证码不能为空1");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException(validateCodeType+"验证码不存在2");
        }
        if (codeInSession.isExpried()) {
//            sessionStrategy.removeAttribute(request, sessionKey);
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException(validateCodeType+"验证码已过期3");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(validateCodeType+"验证码不正确4");
        }
//        sessionStrategy.removeAttribute(request, sessionKey);
        validateCodeRepository.remove(request, validateCodeType);
    }

}
