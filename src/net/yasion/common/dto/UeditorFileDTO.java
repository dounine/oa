package net.yasion.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

public class UeditorFileDTO extends FileBaseDTO {

	private String uploadTime;
	private String filePath;
	private String ueditorType;

	public UeditorFileDTO() {
		super();
	}

	public UeditorFileDTO(String uploadTime, String filePath, String ueditorType) {
		super();
		this.uploadTime = uploadTime;
		this.filePath = filePath;
		this.ueditorType = ueditorType;

	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUeditorType() {
		return ueditorType;
	}

	public void setUeditorType(String ueditorType) {
		this.ueditorType = ueditorType;
	}

	@Override
	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		HashMap<String, OperatorType> operateRelationMap = new HashMap<String, OperatorType>();
		operateRelationMap.put("fileName", OperatorType.LIKE);
		operateRelationMap.put("originalName", OperatorType.LIKE);
		operateRelationMap.put("fileType", OperatorType.LIKE);
		operateRelationMap.put("createUserId", OperatorType.EQ);
		operateRelationMap.put("size", OperatorType.EQ);
		operateRelationMap.put("filePath", OperatorType.LIKE);
		operateRelationMap.put("uploadTime", OperatorType.BETWEEN);
		this.setOperateRelation(operateRelationMap);
	}

	@Override
	public boolean isExcludedProperty(String propertyName, Object propertyValue) {
		return false;
	}
}