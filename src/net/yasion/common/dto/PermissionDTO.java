package net.yasion.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

public class PermissionDTO extends BaseDTO<String> {

	// Fields

	private String name;
	private String code;
	private String descr;
	private String disable;
	private String blackUrls;
	private String whiteUrls;

	// Constructors
	/** default constructor */
	public PermissionDTO() {
	}

	/** full constructor */
	public PermissionDTO(String name, String code, String descr, String disable, String blackUrls, String whiteUrls) {
		this.name = name;
		this.code = code;
		this.descr = descr;
		this.disable = disable;
		this.blackUrls = blackUrls;
		this.whiteUrls = whiteUrls;
	}

	// Property accessors

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDisable() {
		return this.disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getBlackUrls() {
		return blackUrls;
	}

	public void setBlackUrls(String blackUrls) {
		this.blackUrls = blackUrls;
	}

	public String getWhiteUrls() {
		return whiteUrls;
	}

	public void setWhiteUrls(String whiteUrls) {
		this.whiteUrls = whiteUrls;
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