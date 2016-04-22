package net.yasion.common.core.initializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import net.yasion.common.core.http.listener.ServletListener;
import net.yasion.common.core.http.listener.SessionListener;
import net.yasion.common.core.initializer.config.ApplicationContextConfig;
import net.yasion.common.core.initializer.config.SpringMVCConfig;

import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;
import org.springframework.web.util.Log4jWebConfigurer;

public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// OpenSessionInViewFilter Filter
		OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
		FilterRegistration.Dynamic dynamicOpenSessionInViewFilter = servletContext.addFilter("osivFilter", openSessionInViewFilter);
		dynamicOpenSessionInViewFilter.addMappingForUrlPatterns(null, false, "*.do");
		// CharacterEncodingFilter
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		FilterRegistration.Dynamic dynamicCharacterEncodingFilter = servletContext.addFilter("encodingFilter", characterEncodingFilter);
		dynamicCharacterEncodingFilter.addMappingForUrlPatterns(null, false, "*.do");
		// applicationContext
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.setServletContext(servletContext);
		applicationContext.register(ApplicationContextConfig.class);
		servletContext.addListener(new ContextLoaderListener(applicationContext));
		// ServletListener
		servletContext.addListener(new ServletListener());
		// SessionListener
		servletContext.addListener(new SessionListener());
		// Log4jConfigListener
		servletContext.setInitParameter(Log4jWebConfigurer.EXPOSE_WEB_APP_ROOT_PARAM, "false");
		servletContext.setInitParameter(Log4jWebConfigurer.CONFIG_LOCATION_PARAM, "/WEB-INF/config/properties/logger/log4j.properties");
		servletContext.addListener(new Log4jConfigListener());
		// springMVCApplicationContext
		AnnotationConfigWebApplicationContext springMVCApplicationContext = new AnnotationConfigWebApplicationContext();
		springMVCApplicationContext.setServletContext(servletContext);
		springMVCApplicationContext.setParent(applicationContext);
		springMVCApplicationContext.register(SpringMVCConfig.class);
		// DispatcherServlet Servlet
		DispatcherServlet dispatcherServlet = new DispatcherServlet(springMVCApplicationContext);
		// dispatcherServlet.setDetectAllHandlerAdapters(false);
		// dispatcherServlet.setDetectAllHandlerMappings(false);
		// dispatcherServlet.setDetectAllViewResolvers(false);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("yaheen", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.do");
	}
}