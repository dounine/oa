package net.yasion.common.web.validator;

import net.yasion.common.core.bean.wrapper.impl.ExtendedBeanWrapperImpl;

import org.springframework.beans.BeanWrapper;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;

public class ExtendedBeanPropertyBindingResult extends BeanPropertyBindingResult {

	private static final long serialVersionUID = -4540997296712064147L;

	public ExtendedBeanPropertyBindingResult(Object target, String objectName, boolean autoGrowNestedPaths, int autoGrowCollectionLimit) {
		super(target, objectName, autoGrowNestedPaths, autoGrowCollectionLimit);
	}

	public ExtendedBeanPropertyBindingResult(Object target, String objectName) {
		super(target, objectName);
	}

	protected BeanWrapper createBeanWrapper() {
		Assert.state(getTarget() != null, "Cannot access properties on null bean instance '" + getObjectName() + "'!");
		return new ExtendedBeanWrapperImpl(getTarget());
	}
}