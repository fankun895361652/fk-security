package com.fk.security.core.validate.code;


import com.fk.security.core.properties.SecurityConstants;

/**
 * @author fankun
 * @date 2018/3/15 10:09
 */
public enum ValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     * @return
     */
    public abstract String getParamNameOnValidate();

}
