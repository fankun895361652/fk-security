package com.fk.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author fankun
 * @date 2018/3/13 11:00
 */
public class ValidateCodeException extends AuthenticationException  {

    private static final long serialVersionUID = -4932828011240097364L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
