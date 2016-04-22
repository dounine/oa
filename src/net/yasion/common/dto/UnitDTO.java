package net.yasion.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

public class UnitDTO extends BaseDTO<String> {

	// Fields
	private String name;
	private String descr;
	private String code;
	private String address;
	private String postcode;
	private String phone;
	// 开放项目申报的时间
	private String startTime;
	private String endTime;
	private String parentId;

	// 多对多
	// private Integer[] userIds;

	public UnitDTO() {
	}

	public UnitDTO(String name, String descr, String code, String address, String postcode, String phone, String startTime, String endTime/* , Integer[] userIds */) {
		super();
		this.name = name;
		this.descr = descr;
		this.code = code;
		this.address = address;
		this.postcode = postcode;
		this.phone = phone;
		this.startTime = startTime;
		this.endTime = endTime;
		// this.userIds = userIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	// public Integer[] getUserIds() {
	// return userIds;
	// }
	//
	// public void setUserIds(Integer[] userIds) {
	// this.userIds = userIds;
	// }

	@Override
	public boolean isExcludedProperty(String propertyName, Object propertyValue) {
		return (super.isExcludedProperty(propertyName, propertyValue) || "userIds".equals(propertyValue));
	}

	@Override
	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		HashMap<String, OperatorType> operateRelationMap = new HashMap<String, OperatorType>();
		operateRelationMap.put("name", OperatorType.LIKE);
		operateRelationMap.put("code", OperatorType.LIKE);
		operateRelationMap.put("address", OperatorType.LIKE);
		operateRelationMap.put("phone", OperatorType.LIKE);
		operateRelationMap.put("postcode", OperatorType.LIKE);
		this.setOperateRelation(operateRelationMap);
	}
}