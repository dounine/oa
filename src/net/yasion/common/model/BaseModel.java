package net.yasion.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import net.yasion.common.annotation.BeanUtilsNoCopyValue;
import net.yasion.common.utils.AfxBeanUtils;

import org.apache.commons.collections.CollectionUtils;

public abstract class BaseModel<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = -7110184319645280900L;

	private ID id;

	@BeanUtilsNoCopyValue
	private String createUserId;

	private String modifiedUserId;

	@BeanUtilsNoCopyValue
	private String createDate;

	private String modifiedDate;

	@BeanUtilsNoCopyValue
	private String createUnitId;

	private String modifiedUnitId;

	private String status;

	private String flag;

	/** 复制值时候只复制的字段的名称 */
	@Transient
	private List<String> onlyCopyValueFieldNames = new ArrayList<String>();

	/** 复制值时候排除的字段的名称 */
	@Transient
	private List<String> excludeCopyValueFieldNames = this.getDefExcludeCopyValueFieldNames();

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreateUnitId() {
		return createUnitId;
	}

	public void setCreateUnitId(String createUnitId) {
		this.createUnitId = createUnitId;
	}

	public String getModifiedUnitId() {
		return modifiedUnitId;
	}

	public void setModifiedUnitId(String modifiedUnitId) {
		this.modifiedUnitId = modifiedUnitId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public final List<String> getOnlyCopyValueFieldNames() {
		return onlyCopyValueFieldNames;
	}

	public final void setOnlyCopyValueFieldNames(List<String> onlyCopyValueFieldNames) {
		this.onlyCopyValueFieldNames = onlyCopyValueFieldNames;
	}

	public final List<String> getExcludeCopyValueFieldNames() {
		return excludeCopyValueFieldNames;
	}

	public final void setExcludeCopyValueFieldNames(List<String> excludeCopyValueFieldNames) {
		this.excludeCopyValueFieldNames = excludeCopyValueFieldNames;
	}

	@Override
	public boolean equals(Object obj) {
		if (null != obj) {
			if (obj.getClass().isAssignableFrom(this.getClass())) {
				BaseModel<?> otherObj = (BaseModel<?>) obj;
				if (null == this.getId()) {
					if (null == otherObj.getId()) {
						return true;
					} else {
						return otherObj.getId().equals(this.getId());
					}
				} else {
					return this.getId().equals(otherObj.getId());
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		return result;
	}

	/** 获取默认的排除字段的名称 */
	public List<String> getDefExcludeCopyValueFieldNames() {
		return new ArrayList<String>(Arrays.asList(new String[] { "createUserId", "modifiedUserId", "createDate", "modifiedDate", "createUnitId", "modifiedUnitId", "excludeCopyValueFieldNames", "onlyCopyValueFieldNames" }));
	}

	/**
	 * 根据字段名，将当前对象的字段值复制到obj对象对应的字段,在excludeCopyValueFieldNames指定了的字段将不进行复制,如果指定了onlyCopyValueFieldNames,那么只复制onlyCopyValueFieldNames指定的字段
	 * 
	 * @param obj
	 *            指定对象
	 */
	public void copyValuesTo(Object obj) {
		if (null != obj) {
			Map<String, Object> allFieldsValue = AfxBeanUtils.getAllFieldsValue(this, true);
			if (CollectionUtils.isEmpty(this.onlyCopyValueFieldNames)) {
				if (CollectionUtils.isNotEmpty(this.excludeCopyValueFieldNames)) {
					for (int i = 0, len = this.excludeCopyValueFieldNames.size(); i < len; i++) {
						allFieldsValue.remove(this.excludeCopyValueFieldNames.get(i));
					}
				}
			} else {
				Map<String, Object> newAllFieldsValue = new HashMap<String, Object>();
				for (int i = 0, len = this.onlyCopyValueFieldNames.size(); i < len; i++) {
					String name = this.onlyCopyValueFieldNames.get(i);
					if (allFieldsValue.containsKey(name)) {
						Object val = allFieldsValue.get(name);
						newAllFieldsValue.put(name, val);
					}
				}
				allFieldsValue = newAllFieldsValue;
			}
			Iterator<String> keyIt = allFieldsValue.keySet().iterator();
			while (keyIt.hasNext()) {
				String name = keyIt.next();
				if (AfxBeanUtils.hasField(obj.getClass(), name)) {
					Object val = allFieldsValue.get(name);
					AfxBeanUtils.setFieldValue(obj, name, val);
				}
			}
		}
	}
}