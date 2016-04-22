package net.yasion.common.model;

/**
 * TbPermission entity. @author MyEclipse Persistence Tools
 */

public class TbPermission extends BaseModel<String> {

	private static final long serialVersionUID = 7963478801006207201L;

	// Fields

	private String name;
	private String code;
	private String descr;
	private String disable;
	private String blackUrls;
	private String whiteUrls;

	// Constructors
	/** default constructor */
	public TbPermission() {
	}

	/** full constructor */
	public TbPermission(String name, String code, String descr, String disable, String blackUrls, String whiteUrls) {
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
}