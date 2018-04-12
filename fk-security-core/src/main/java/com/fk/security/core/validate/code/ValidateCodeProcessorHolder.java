package com.fk.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fankun
 * @date 2018/3/15 10:01
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type){
    return findValidateCodeProcessor(type.toString().toLowerCase());
}

    public ValidateCodeProcessor findValidateCodeProcessor(String type){
        String name = type.toLowerCase()+ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if(null == processor){
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }

}
