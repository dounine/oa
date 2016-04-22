package net.yasion.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.yasion.common.support.common.service.enumeration.OperatorType;
import net.yasion.common.utils.AfxBeanUtils;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract class BaseDTO<ID extends Serializable> {

	private ID id;

	private String operatedUserId;

	private String operatedDate;

	private String operatedUnitId;

	/** 兼容DTO搜索而添加 START */
	private String createUserId;

	private String modifiedUserId;

	private String createDate;

	private String modifiedDate;

	private String createUnitId;

	private String modifiedUnitId;
	/** 兼容DTO搜索而添加 END */

	private String status;

	private String flag;

	/** And/Or表达式(例如:And(DTO字段名1,Or(And(DTO字段名2,DTO字段名3),DTO字段名4)),service层调用getPropertyCriterionByDTO时候,根据有无表达式自动处理) */
	private String criteriaLogicExpression;

	/** 操作关系(例如:字段名1要一个LIKE操作,直接put入("字段名1",OperatorType.LIKE),在service层调用getPropertyCriterionByDTO可以处理) */
	private Map<String, List<OperatorType>> operateRelationMap = new HashMap<String, List<OperatorType>>();

	/** 操作值(例如:指定字段名1要一个值,直接put入("字段名1",val),在service层调用getPropertyCriterionByDTO可以处理) */
	private Map<String, Object> operateValue = new HashMap<String, Object>();

	/** 默认的Criterion查询条件 */
	private Criterion defCriterion = null;

	/** 复制值时候只复制的字段的名称 */
	private List<String> onlyCopyValueFieldNames = new ArrayList<String>();

	/** 复制值时候排除的字段的名称 */
	private List<String> excludeCopyValueFieldNames = this.getDefExcludeCopyValueFieldNames();

	/** 查询时候使用到的别名,作为默认实体的别名 */
	private String criterionAlias = null;

	/** 查询时候使用到的排序方式,默认为null */
	private Order[] criterionOrders = null;

	/** 默认情况下ModelDTOSearch在获取搜索值的时候只在operateValue中查找,当这个标志为true,则在operateValue中找不到值时候会在Field中也进行查找 */
	private boolean modelDTOSearchOnField = false;

	/** 是否处理在processCriteriaExpression中剩余的条件,默认是处理 */
	private boolean dealRemaind = true;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public String getOperatedUserId() {
		return operatedUserId;
	}

	public void setOperatedUserId(String operatedUserId) {
		this.operatedUserId = operatedUserId;
	}

	public String getOperatedDate() {
		return operatedDate;
	}

	public void setOperatedDate(String operatedDate) {
		this.operatedDate = operatedDate;
	}

	public String getOperatedUnitId() {
		return operatedUnitId;
	}

	public void setOperatedUnitId(String operatedUnitId) {
		this.operatedUnitId = operatedUnitId;
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

	public final String getCriteriaLogicExpression() {
		return criteriaLogicExpression;
	}

	public final void setCriteriaLogicExpression(String criteriaLogicExpression) {
		this.criteriaLogicExpression = criteriaLogicExpression;
	}

	public final Map<String, List<OperatorType>> getOperateRelation() {
		return operateRelationMap;
	}

	public final Map<String, List<OperatorType>> getOperateRelationMap() {
		return operateRelationMap;
	}

	public final void setOperateRelation(Map<String, OperatorType> operateRelation) {
		this.operateRelationMap = new HashMap<String, List<OperatorType>>();
		Set<String> keySet = operateRelation.keySet();
		Iterator<String> keyIt = keySet.iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			this.operateRelationMap.put(key, new ArrayList<OperatorType>(Arrays.asList(new OperatorType[] { operateRelation.get(key) })));
		}
	}

	public final void setOperateRelationMap(Map<String, List<OperatorType>> operateRelationMap) {
		this.operateRelationMap = operateRelationMap;
	}

	public final Map<String, Object> getOperateValue() {
		return operateValue;
	}

	public final void setOperateValue(Map<String, Object> operateValue) {
		this.operateValue = operateValue;
	}

	public final Criterion getDefCriterion() {
		return defCriterion;
	}

	public final void setDefCriterion(Criterion defCriterion) {
		this.defCriterion = defCriterion;
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

	public String getCriterionAlias() {
		return criterionAlias;
	}

	public void setCriterionAlias(String criterionAlias) {
		this.criterionAlias = criterionAlias;
	}

	public Order[] getCriterionOrders() {
		return criterionOrders;
	}

	public void setCriterionOrders(Order[] criterionOrders) {
		this.criterionOrders = criterionOrders;
	}

	public final boolean isModelDTOSearchOnField() {
		return modelDTOSearchOnField;
	}

	public final void setModelDTOSearchOnField(boolean modelDTOSearchOnField) {
		this.modelDTOSearchOnField = modelDTOSearchOnField;
	}

	public final boolean isDealRemaind() {
		return dealRemaind;
	}

	public final void setDealRemaind(boolean dealRemaind) {
		this.dealRemaind = dealRemaind;
	}

	public boolean isExcludedProperty(String propertyName, Object propertyValue) {
		return (propertyName.toLowerCase().endsWith("id"));
	}

	public final boolean isDefExcludedProperty(String propertyName, Object propertyValue) {
		return ("operateRelationMap".equals(propertyName) || "criteriaLogicExpression".equals(propertyName) || "operateValue".equals(propertyName) || "defCriterion".equals(propertyName) || "excludeCopyValueFieldNames".equals(propertyName)
				|| "onlyCopyValueFieldNames".equals(propertyName) || "criterionAlias".equals(propertyName) || "criterionOrders".equals(propertyName) || "modelDTOSearchOnField".equals(propertyName) || "dealRemaind".equals(propertyName));
	}

	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		this.operateRelationMap = new HashMap<String, List<OperatorType>>();
	}

	public void generateDefCriterion(String requestURL, Map<String, String[]> paramsMap) {
		this.defCriterion = null;
	}

	@Override
	public boolean equals(Object obj) {
		if (null != obj) {
			if (obj.getClass().isAssignableFrom(this.getClass())) {
				BaseDTO<?> otherObj = (BaseDTO<?>) obj;
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
		return new ArrayList<String>(Arrays.asList(new String[] { "operatedUnitId", "operatedDate", "operatedUserId", "criteriaLogicExpression", "operateRelationMap", "operateValue", "defCriterion", "excludeCopyValueFieldNames", "onlyCopyValueFieldNames", "criterionAlias",
				"criterionOrders", "modelDTOSearchOnField", "dealRemaind", "createUserId", "modifiedUserId", "createDate", "modifiedDate", "createUnitId", "modifiedUnitId" }));
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