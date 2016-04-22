package net.yasion.common.core.http.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;

public class ServletListener implements ServletContextAttributeListener, ServletContextListener {

	public ServletListener() {
		super();
		System.out.println("ServletListener Create");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		HttpInternalObjectManager.setServletContext(event.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		SpringBeanManager.getAppContextSet().clear();// 销毁context集合
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {

	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {

	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {

	}
}