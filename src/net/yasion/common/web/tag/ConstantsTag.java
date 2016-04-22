package net.yasion.common.web.tag;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.utils.AfxBeanUtils;

import org.apache.commons.lang3.StringUtils;

public class ConstantsTag extends TagSupport {

	private static final long serialVersionUID = -7617738664610676261L;

	private String clazz = CommonConstants.class.getName();// 常量类

	private String var;// 指定常量，多个间用逗号隔开。不指定代表所有常量

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			Class<?> clazzInst = Class.forName(this.getClazz());
			if (StringUtils.isNotBlank(this.getVar())) {
				String[] vars = StringUtils.split(this.getVar(), ",");
				for (int i = 0, len = vars.length; i < len; i++) {
					String nowVar = StringUtils.trim(vars[i]);
					Object objVal = AfxBeanUtils.getFieldValue(clazzInst, nowVar);
					this.pageContext.setAttribute(nowVar, objVal);
				}
			} else {
				Field[] allFields = AfxBeanUtils.getAllFields(clazzInst);
				for (int i = 0, len = allFields.length; i < len; i++) {
					Field field = allFields[i];
					if (Modifier.isPublic(field.getModifiers())) {
						this.pageContext.setAttribute(field.getName(), AfxBeanUtils.getFieldValue(null, field));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}