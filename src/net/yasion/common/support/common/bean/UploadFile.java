package net.yasion.common.support.common.bean;

import java.io.File;

public class UploadFile {

	private String fileId;
	private String origFileName;
	private String realFileName;
	private long fileLength;
	private String fileType;
	private String absolutePath;
	private File file;

	public UploadFile(String fileId, String origFileName, String realFileName, long fileLength, String fileType, String absolutePath, File file) {
		super();
		this.fileId = fileId;
		this.origFileName = origFileName;
		this.realFileName = realFileName;
		this.fileLength = fileLength;
		this.fileType = fileType;
		this.absolutePath = absolutePath;
		this.file = file;
	}

	public String getFileId() {
		return fileId;
	}

	public String getOrigFileName() {
		return origFileName;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public long getFileLength() {
		return fileLength;
	}

	public String getFileType() {
		return fileType;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public File getFile() {
		return file;
	}
}