package net.yasion.common.web.binder;

import net.yasion.common.web.validator.ExtendedBeanPropertyBindingResult;

import org.springframework.beans.SimpleTypeConverter;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;
import org.springframework.validation.AbstractPropertyBindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

public class SuperExtendedServletRequestDataBinder extends ExtendedServletRequestDataBinder {

	private AbstractPropertyBindingResult bindingResult;

	private boolean autoGrowNestedPaths = true;

	private ConversionService conversionService;

	private SimpleTypeConverter typeConverter;

	public SuperExtendedServletRequestDataBinder(Object target) {
		super(target);
	}

	public SuperExtendedServletRequestDataBinder(Object target, String objectName) {
		super(target, objectName);
	}

	public void setAutoGrowNestedPaths(boolean autoGrowNestedPaths) {
		Assert.state(this.bindingResult == null, "DataBinder is already initialized - call setAutoGrowNestedPaths before other configuration methods");
		this.autoGrowNestedPaths = autoGrowNestedPaths;
	}

	public boolean isAutoGrowNestedPaths() {
		return this.autoGrowNestedPaths;
	}

	public void initBeanPropertyAccess() {
		Assert.state(this.bindingResult == null, "DataBinder is already initialized - call initBeanPropertyAccess before other configuration methods");
		this.bindingResult = new ExtendedBeanPropertyBindingResult(getTarget(), getObjectName(), isAutoGrowNestedPaths(), getAutoGrowCollectionLimit());
		if (this.conversionService != null) {
			this.bindingResult.initConversion(this.conversionService);
		}
	}

	public void initDirectFieldAccess() {
		Assert.state(this.bindingResult == null, "DataBinder is already initialized - call initDirectFieldAccess before other configuration methods");
		this.bindingResult = new DirectFieldBindingResult(getTarget(), getObjectName());
		if (this.conversionService != null) {
			this.bindingResult.initConversion(this.conversionService);
		}
	}

	protected AbstractPropertyBindingResult getInternalBindingResult() {
		if (this.bindingResult == null) {
			initBeanPropertyAccess();
		}
		return this.bindingResult;
	}

	public ConversionService getConversionService() {
		return this.conversionService;
	}

	public void setConversionService(ConversionService conversionService) {
		Assert.state(this.conversionService == null, "DataBinder is already initialized with ConversionService");
		this.conversionService = conversionService;
		if (this.bindingResult != null && conversionService != null) {
			this.bindingResult.initConversion(conversionService);
		}
	}

	protected SimpleTypeConverter getSimpleTypeConverter() {
		if (this.typeConverter == null) {
			this.typeConverter = new SimpleTypeConverter();
			if (this.conversionService != null) {
				this.typeConverter.setConversionService(this.conversionService);
			}
		}
		return this.typeConverter;
	}
}