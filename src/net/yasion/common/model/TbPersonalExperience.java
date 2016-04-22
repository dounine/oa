package net.yasion.common.model;

/**
 * TbPersonalExperience entity. @author MyEclipse Persistence Tools
 */

public class TbPersonalExperience extends BaseModel<String> {

	private static final long serialVersionUID = -7994325807648978009L;
	// Fields
	private String startTime;
	private String endTime;
	private String descr;
	private TbUser tbUser;

	// Constructors

	/** default constructor */
	public TbPersonalExperience() {
	}

	/** full constructor */
	public TbPersonalExperience(String startTime, String endTime, String descr) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.descr = descr;
	}

	// Property accessors
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public TbUser getTbUser() {
		return tbUser;
	}

	public void setTbUser(TbUser tbUser) {
		this.tbUser = tbUser;
	}
}