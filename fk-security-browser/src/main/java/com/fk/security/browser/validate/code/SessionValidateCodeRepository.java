package com.fk.security.browser.validate.code;

import com.fk.security.core.validate.code.ValidateCode;
import com.fk.security.core.validate.code.ValidateCodeRepository;
import com.fk.security.core.validate.code.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author fankun
 * @date 2018/4/3 9:53
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    String SESSION_KEY_PREFIX ="SESSION_KEY_FOR_CODE_";
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request,getSessionKey(request,validateCodeType),code );
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
                String sessionKey = getSessionKey(request,validateCodeType);
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, sessionKey);
        return codeInSession;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String sessionKey = getSessionKey(request,validateCodeType);
        sessionStrategy.removeAttribute(request, sessionKey);
    }
    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }
}
