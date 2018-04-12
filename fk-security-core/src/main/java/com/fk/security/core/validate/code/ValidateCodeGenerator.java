package com.fk.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author fankun
 * @date 2018/3/13 16:35
 */
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
