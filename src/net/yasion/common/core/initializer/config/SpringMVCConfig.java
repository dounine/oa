package net.yasion.common.core.initializer.config;

import java.util.Arrays;

import net.yasion.common.core.http.interceptor.HttpInternalObjectInterceptor;
import net.yasion.common.web.interceptor.LoginInterceptor;
import net.yasion.common.web.interceptor.PermissionControllInterceptor;
import net.yasion.common.web.interceptor.SharedSessionInterceptor;
import net.yasion.common.web.resolver.AnnotationArgumentResolver;
import net.yasion.common.web.resolver.ExtInternalResourceViewResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
/* 注意:useDefaultFilters非常重要,不加的话后果严重,不加的时候这个会扫描所有的类,会造成部分功能失效,因为这个是子容器,如果不配置useDefaultFilters,连sessionFactory也有可能会被他再次注入导致延迟加载失效 */
@ComponentScan(basePackages = "net.yasion", useDefaultFilters = false, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Service.class, Repository.class, /* 避免重复加载 START */Configuration.class /* 避免重复加载 END */}) }, includeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
public class SpringMVCConfig extends WebMvcConfigurationSupport {

	@Bean
	@Override
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
		handlerAdapter.setCustomArgumentResolvers(Arrays.asList(new HandlerMethodArgumentResolver[] { new AnnotationArgumentResolver() }));
		handlerAdapter.setOrder(-1);// 设置优先级,数值越低优先级越高
		return handlerAdapter;
	}

	@Bean
	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
		HttpInternalObjectInterceptor httpInternalObjectInterceptor = new HttpInternalObjectInterceptor();
		SharedSessionInterceptor sharedSessionInterceptor = new SharedSessionInterceptor();
		LoginInterceptor loginInterceptor = new LoginInterceptor();
		// loginInterceptor.setExcludeUrls(new HashSet<String>(Arrays.asList(new String[] { "login.do", "logout.do" })));
		PermissionControllInterceptor permissionControllInterceptor = new PermissionControllInterceptor();
		// permissionControllInterceptor.setExcludeUrls(new HashSet<String>(Arrays.asList(new String[] { "/login.do", "/logout.do", "/index.do" })));
		// permissionControllInterceptor.setDefBlackUrls(new HashSet<String>(Arrays.asList(new String[] { "/[A-Za-z0-9]*/edit.do" })));
		handlerMapping.setInterceptors(new Object[] { httpInternalObjectInterceptor, sharedSessionInterceptor, loginInterceptor, permissionControllInterceptor });
		handlerMapping.setOrder(-1);// 设置优先级,数值越低优先级越高
		return handlerMapping;
	}

	@Bean
	@Override
	public ViewResolver mvcViewResolver() {
		ExtInternalResourceViewResolver viewResolver = new ExtInternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/pages/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(-1);// 设置优先级,数值越低优先级越高
		return viewResolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10000000);
		multipartResolver.setDefaultEncoding("UTF-8");
		return multipartResolver;
	}
}