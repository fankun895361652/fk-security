package com.fk.security.app.validate.code;

import com.fk.security.core.validate.code.ValidateCode;
import com.fk.security.core.validate.code.ValidateCodeException;
import com.fk.security.core.validate.code.ValidateCodeRepository;
import com.fk.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author fankun
 * @date 2018/4/3 9:59
 */
@Component
@EnableCaching
public class RedisValidateCodeRepository implements ValidateCodeRepository {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        redisTemplate.opsForValue().set(buildKey(request,validateCodeType),code,30, TimeUnit.MINUTES);
    }



    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        Object codeObj = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
        if(null == codeObj){
            return null;
        }
     return (ValidateCode) codeObj;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisTemplate.delete(buildKey(request, validateCodeType));
    }
    private String buildKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
throw new ValidateCodeException("请求头中没有device参数");
        }
        return "code:"+validateCodeType.toString().toLowerCase()+":"+deviceId;
    }
}
