package net.yasion.common.support.json.processor;

import java.lang.reflect.Constructor;

import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.JSONUtils;

public class DefualtNullProcessor implements DefaultValueProcessor {

	private boolean nullDefault = false;

	public DefualtNullProcessor() {
		super();
	}

	public DefualtNullProcessor(boolean nullDefault) {
		super();
		this.nullDefault = nullDefault;
	}

	public boolean isNullDefault() {
		return nullDefault;
	}

	public void setNullDefault(boolean nullDefault) {
		this.nullDefault = nullDefault;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getDefaultValue(Class type) {
		if (this.nullDefault) {
			return null;
		} else {
			if (JSONUtils.isArray(type)) {
				return new Object[0];
			} else if (JSONUtils.isNumber(type)) {
				try {
					Constructor constructor = type.getConstructor();
					return constructor.newInstance();
				} catch (Exception e) {
					try {
						Constructor constructor = type.getConstructor(String.class);
						return constructor.newInstance("0");
					} catch (Exception e1) {
						return null;
					}
				}
			} else if (JSONUtils.isBoolean(type)) {
				return Boolean.FALSE;
			} else if (JSONUtils.isString(type)) {
				return "";
			} else {
				return null;
			}
		}
	}
}