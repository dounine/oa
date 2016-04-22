package net.yasion.common.dto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

public class WebFileDTO extends BaseDTO<String> {

	private String fileName;
	private String originalFileName;
	private String fileType;
	private String md5;
	private BigInteger size;
	private String filePath;
	private String uploadUrl;
	private String ipAddress;

	public WebFileDTO() {
		super();
	}

	public WebFileDTO(String fileName, String originalFileName, String fileType, String md5, BigInteger size, String filePath, String uploadUrl, String ipAddress) {
		super();
		this.fileName = fileName;
		this.originalFileName = originalFileName;
		this.fileType = fileType;
		this.md5 = md5;
		this.size = size;
		this.filePath = filePath;
		this.uploadUrl = uploadUrl;
		this.ipAddress = ipAddress;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		HashMap<String, OperatorType> operateRelationMap = new HashMap<String, OperatorType>();
		operateRelationMap.put("fileName", OperatorType.LIKE);
		operateRelationMap.put("originalFileName", OperatorType.LIKE);
		operateRelationMap.put("fileType", OperatorType.LIKE);
		operateRelationMap.put("md5", OperatorType.LIKE);
		operateRelationMap.put("size", OperatorType.EQ);
		operateRelationMap.put("filePath", OperatorType.LIKE);
		operateRelationMap.put("uploadUrl", OperatorType.LIKE);
		operateRelationMap.put("ipAddress", OperatorType.LIKE);
		operateRelationMap.put("createDate", OperatorType.BETWEEN);
		operateRelationMap.put("createUserId", OperatorType.EQ);
		this.setOperateRelation(operateRelationMap);
	}

	@Override
	public boolean isExcludedProperty(String propertyName, Object propertyValue) {
		return false;
	}
}