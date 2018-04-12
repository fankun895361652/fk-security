package com.fk.security.core.validate.code;

import com.fk.security.core.properties.SecurityConstants;
import com.fk.security.core.properties.SecurityProperties;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义验证码过滤器
 * @author fankun
 * @date 2018/3/13 10:49
 */
@Data
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{
    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }


    protected void addUrlToMap(String urlString, ValidateCodeType type){
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(request);

        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request,httpServletResponse));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(request, httpServletResponse);
    }

//    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
//        ImageCode codeInsession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
//        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
//        if (StringUtils.isBlank(codeInRequest)) {
//            throw new ValidateCodeException("验证码不能为空1");
//        }
//        if (codeInsession == null) {
//            throw new ValidateCodeException("验证码不存在2");
//        }
//        if (codeInsession.isExpried()) {
//            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
//            throw new ValidateCodeException("验证码已过期3");
//        }
//        if (!StringUtils.equals(codeInsession.getCode(), codeInRequest)) {
//            throw new ValidateCodeException("验证码不正确4");
//        }
//        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
//    }
    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }

}
