package net.yasion.common.web.action;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.utils.AfxBeanUtils;

public abstract class BaseAction {

	protected void setToPageContext(Object... objs) {
		HttpServletRequest currentRequest = HttpInternalObjectManager.getCurrentRequest();
		for (int i = 0, len = objs.length; i < len; i++) {
			Map<String, Object> allFieldsValue = AfxBeanUtils.getAllFieldsValue(objs[i]);
			Iterator<String> iterator = allFieldsValue.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object val = allFieldsValue.get(key);
				currentRequest.setAttribute(key, val);
			}
		}
	}
}