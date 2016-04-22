package net.yasion.common.model;

/**
 * TbAttachment entity. @author MyEclipse Persistence Tools
 */

public class TbUeditorFile extends FileBaseModel {

	private static final long serialVersionUID = -4371784797513554778L;

	// Fields
	private String uploadTime;
	private String filePath;
	private String ueditorType;

	// Constructors

	/** default constructor */
	public TbUeditorFile() {
	}

	/** full constructor */
	public TbUeditorFile(String uploadTime, String filePath, String ueditorType) {
		super();
		this.uploadTime = uploadTime;
		this.filePath = filePath;
		this.ueditorType = ueditorType;
	}

	// Property accessors

	public String getUploadTime() {
		return this.uploadTime;
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
}