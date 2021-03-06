/**
 * 
 */
package com.fk.security.core.validate.code;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

/**
 * @author zhailiang
 *
 */
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	/**
	 * spring 会根据此属性名称validateCodeFilter 自动寻找所有Filter的实现类中@Component("validateCodeFilter")为这个的类并将其装载进来。
	 */
	@Autowired
	private Filter validateCodeFilter;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
	}
	
}
