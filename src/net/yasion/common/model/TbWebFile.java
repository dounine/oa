package net.yasion.common.model;

import java.math.BigInteger;
import java.util.Set;

/**
 * TbWebFile entity. @author MyEclipse Persistence Tools
 */

public class TbWebFile extends BaseModel<String> {

	private static final long serialVersionUID = -908651619912472935L;

	// Fields
	private String fileName;
	private String originalFileName;
	private String fileType;
	private String md5;
	private BigInteger size;
	private String filePath;
	private String uploadUrl;
	private String ipAddress;
	private Set<FileBaseModel> dummyFiles;

	// Constructors
	public TbWebFile() {
		super();
	}

	public TbWebFile(String fileName, String originalFileName, String fileType, String md5, BigInteger size, String filePath, String uploadUrl, String ipAddress) {
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

	// Property accessors
	public String getFileName() {
		return this.fileName;
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
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMd5() {
		return this.md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public BigInteger getSize() {
		return this.size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUploadUrl() {
		return this.uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Set<FileBaseModel> getDummyFiles() {
		return dummyFiles;
	}

	public void setDummyFiles(Set<FileBaseModel> dummyFiles) {
		this.dummyFiles = dummyFiles;
	}
}