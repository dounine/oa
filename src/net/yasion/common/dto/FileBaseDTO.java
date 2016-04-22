package net.yasion.common.dto;

import java.math.BigInteger;

public abstract class FileBaseDTO extends BaseDTO<String> {

	private String fileName;
	private String originalName;
	private String fileType;
	private BigInteger size;
	private String fileId;

	public FileBaseDTO() {
		super();
	}

	public FileBaseDTO(String fileName, String originalName, String fileType, BigInteger size, String fileId) {
		super();
		this.fileName = fileName;
		this.originalName = originalName;
		this.fileType = fileType;
		this.size = size;
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}