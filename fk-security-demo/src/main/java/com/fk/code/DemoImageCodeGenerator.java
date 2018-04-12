package com.fk.code;

import com.fk.security.core.validate.code.ValidateCodeGenerator;
import com.fk.security.core.validate.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author fankun
 * @date 2018/3/14 10:04
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("DemoImageCodeGenerator is begin..");
        return null;
    }
}
