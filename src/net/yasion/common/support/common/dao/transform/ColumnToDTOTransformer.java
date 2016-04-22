package net.yasion.common.support.common.dao.transform;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.yasion.common.dto.TransformBaseDTO;
import net.yasion.common.model.BaseModel;
import net.yasion.common.support.common.dao.interfaces.ITransformCompositeDTO;
import net.yasion.common.support.common.dao.interfaces.ITransformMixDTO;
import net.yasion.common.utils.AfxBeanUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.ResultTransformer;

public class ColumnToDTOTransformer implements ResultTransformer {

	private static final long serialVersionUID = -5312424160589974071L;

	private final Class<? extends TransformBaseDTO> resultClass;

	private Boolean isCriteria = false;

	private List<String[]> aliasList = new ArrayList<String[]>();

	private String[] groupAliases = null;

	public Class<? extends TransformBaseDTO> getResultClass() {
		return resultClass;
	}

	public ColumnToDTOTransformer(Class<? extends TransformBaseDTO> resultClass) {
		super();
		this.resultClass = resultClass;
	}

	public ColumnToDTOTransformer(Class<? extends TransformBaseDTO> resultClass, String[] groupAliases) {
		super();
		this.resultClass = resultClass;
		this.groupAliases = groupAliases;
	}

	public Boolean getIsCriteria() {
		return isCriteria;
	}

	public void setIsCriteria(Boolean isCriteria) {
		this.isCriteria = isCriteria;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List transformList(List collection) {
		if (this.getIsCriteria()) {
			List<Object> newCollection = new ArrayList<Object>();
			for (int i = 0, len = collection.size(); i < len; i++) {
				Object tuple = processTuple(new Object[] { collection.get(i) }, aliasList.get(i));
				newCollection.add(tuple);
			}
			collection = newCollection;
		}
		return collection;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		Object result = (1 == values.length ? values[0] : values);// 默认值
		if (!this.getIsCriteria()) {
			result = processTuple(values, aliases);
		} else {
			aliasList.add(aliases);
		}
		return result;
	}

	protected Object processTuple(Object[] values, String[] aliases) {
		Object result = null;
		try {
			result = this.resultClass.newInstance();
			if (null != result) {
				for (int i = 0, len = values.length; i < len; i++) {
					try {
						Object value = values[i];
						if (result instanceof ITransformMixDTO && BaseModel.class.isAssignableFrom(value.getClass())) {// 混合DTO
							String alias = ArrayUtils.isNotEmpty(aliases) ? aliases[i] : null;
							alias = (ArrayUtils.isNotEmpty(this.groupAliases) ? this.groupAliases[i] : alias);
							String prefix = ((ITransformMixDTO) result).getPrefix(value, (alias), i);
							prefix = (StringUtils.isNotBlank(prefix) ? prefix : "");
							if (null != value) {
								Map<String, Object> allFieldNVMap = AfxBeanUtils.getAllFieldsValue(value);
								Iterator<String> allFieldNIt = allFieldNVMap.keySet().iterator();
								while (allFieldNIt.hasNext()) {
									String name = allFieldNIt.next();
									if (StringUtils.isNotBlank(prefix)) {
										name = StringUtils.upperCase(StringUtils.substring(name, 0, 1)) + StringUtils.substring(name, 1, name.length());
									}
									AfxBeanUtils.setFieldValue(result, prefix + name, allFieldNVMap.get(name));
								}
							}
						} else if (result instanceof ITransformCompositeDTO && BaseModel.class.isAssignableFrom(value.getClass())) {// 复合DTO
							String alias = ArrayUtils.isNotEmpty(aliases) ? aliases[i] : null;
							alias = (ArrayUtils.isNotEmpty(this.groupAliases) ? this.groupAliases[i] : alias);
							String fieldName = ((ITransformCompositeDTO) result).getFieldName(value, (alias), i);
							if (null != value) {
								AfxBeanUtils.setFieldValue(result, fieldName, value);
							}
						} else {// 普通直接的DTO
							String alias = ArrayUtils.isNotEmpty(aliases) ? aliases[i] : null;
							alias = (ArrayUtils.isNotEmpty(this.groupAliases) ? this.groupAliases[i] : alias);
							Field field = getFieldByColumnName(alias);
							if (null != field) {
								AfxBeanUtils.setFieldValue(result, field, value);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();// 只显示出错
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected Field getFieldByColumnName(String alias) {
		if (StringUtils.isNotBlank(alias)) {
			Field field = null;
			field = AfxBeanUtils.getField(this.resultClass, alias);
			if (null == field) {
				// 把字段名中所有的下杠去除
				String newAliasName = this.columnNameToFieldName(alias);
				field = AfxBeanUtils.getField(this.resultClass, newAliasName);
			}
			return field;
		}
		return null;
	}

	protected String columnNameToFieldName(String column) {
		String newAliasName = column;
		if (StringUtils.isNotBlank(column) && StringUtils.contains(column, "_")) {
			// 把字段名中所有的下杠去除
			StringBuilder builder = new StringBuilder();
			String[] aliasSubNameArr = StringUtils.split(column, "_");
			for (int i = 0, len = aliasSubNameArr.length; i < len; i++) {
				String aliasSubName = aliasSubNameArr[i];
				if (StringUtils.isNotBlank(aliasSubName)) {
					String newAliasSubName = StringUtils.upperCase(StringUtils.substring(aliasSubName, 0, 1)) + StringUtils.substring(aliasSubName, 1, aliasSubName.length());
					builder.append(newAliasSubName);
				}
			}
			newAliasName = builder.toString();
			newAliasName = (StringUtils.lowerCase(StringUtils.substring(newAliasName, 0, 1)) + StringUtils.substring(newAliasName, 1, newAliasName.length()));
		}
		return newAliasName;
	}
}