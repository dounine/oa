package net.yasion.common.model;

import java.math.BigInteger;

public abstract class FileBaseModel extends BaseModel<String> {

	private static final long serialVersionUID = 4966080638493816516L;

	private String fileName;
	private String originalName;
	private String fileType;
	private BigInteger size;
	private TbWebFile tbWebFile;

	public FileBaseModel() {
		super();
	}

	public FileBaseModel(String fileName, String originalName, String fileType, BigInteger size, TbWebFile tbWebFile) {
		super();
		this.fileName = fileName;
		this.originalName = originalName;
		this.fileType = fileType;
		this.size = size;
		this.tbWebFile = tbWebFile;
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

	public TbWebFile getTbWebFile() {
		return tbWebFile;
	}

	public void setTbWebFile(TbWebFile tbWebFile) {
		this.tbWebFile = tbWebFile;
	}
}