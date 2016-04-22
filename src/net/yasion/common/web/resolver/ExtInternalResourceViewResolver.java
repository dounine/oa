package net.yasion.common.web.resolver;

import net.yasion.common.web.view.ExtInternalResourceView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class ExtInternalResourceViewResolver extends InternalResourceViewResolver {
	/**
	 * Set default viewClass
	 */
	public ExtInternalResourceViewResolver() {
		setViewClass(ExtInternalResourceView.class);
	}

	/**
	 * if viewName start with / , then ignore prefix.
	 */
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);
		// start with / ignore prefix
		if (viewName.startsWith("/")) {
			view.setUrl(viewName + getSuffix());
		}
		return view;
	}
}