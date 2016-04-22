package net.yasion.common.core.http.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.yasion.common.core.http.manager.HttpInternalObjectManager;

public class SessionListener implements HttpSessionListener {

	public SessionListener() {
		super();
		System.out.println("SessionListener Create");
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpInternalObjectManager.putSession(event.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpInternalObjectManager.removeSession(event.getSession());
	}
}