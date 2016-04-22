package net.yasion.common.model;

/**
 * TbRole entity. @author MyEclipse Persistence Tools
 */

public class TbRole extends BaseModel<String> {

	private static final long serialVersionUID = -176041840638230291L;

	// Fields

	private String name;
	private String code;
	private String descr;
	private String disable;

	// Constructors

	/** default constructor */
	public TbRole() {
	}

	/** full constructor */
	public TbRole(String name, String code, String descr, String disable) {
		this.name = name;
		this.code = code;
		this.descr = descr;
		this.disable = disable;
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
}