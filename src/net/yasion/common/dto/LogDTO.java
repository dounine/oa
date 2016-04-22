package net.yasion.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

public class LogDTO extends BaseDTO<String> {

	// Fields
	private String title;
	private String loggerType;
	private String userId;
	private String userName;
	private String ipAddress;
	private String url;
	private String logTime;
	private String content;

	// Property accessors

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLoggerType() {
		return this.loggerType;
	}

	public void setLoggerType(String loggerType) {
		this.loggerType = loggerType;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogTime() {
		return this.logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		HashMap<String, OperatorType> operateRelationMap = new HashMap<String, OperatorType>();
		operateRelationMap.put("title", OperatorType.LIKE);
		operateRelationMap.put("loggerType", OperatorType.EQ);
		operateRelationMap.put("userId", OperatorType.EQ);
		operateRelationMap.put("userName", OperatorType.LIKE);
		operateRelationMap.put("ipAddress", OperatorType.LIKE);
		operateRelationMap.put("url", OperatorType.LIKE);
		operateRelationMap.put("logTime", OperatorType.BETWEEN);
		operateRelationMap.put("content", OperatorType.LIKE);
		this.setOperateRelation(operateRelationMap);
	}
}