package net.yasion.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

public class RoleDTO extends BaseDTO<String> {

	private String name;
	private String code;
	private String descr;
	private String disable;

	public RoleDTO() {
	}

	public RoleDTO(String name, String code, String descr, String disable) {
		super();
		this.name = name;
		this.code = code;
		this.descr = descr;
		this.disable = disable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	@Override
	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		HashMap<String, OperatorType> operateRelationMap = new HashMap<String, OperatorType>();
		operateRelationMap.put("name", OperatorType.LIKE);
		operateRelationMap.put("code", OperatorType.LIKE);
		operateRelationMap.put("descr", OperatorType.LIKE);
		operateRelationMap.put("disable", OperatorType.EQ);
		this.setOperateRelation(operateRelationMap);
	}
}