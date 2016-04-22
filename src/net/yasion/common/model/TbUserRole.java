package net.yasion.common.model;

/**
 * TbUserRole entity. @author MyEclipse Persistence Tools
 */

public class TbUserRole extends BaseModel<String> {

	private static final long serialVersionUID = 8262601989292881527L;

	// Fields

	private TbUser tbUser;
	private TbRole tbRole;

	// Constructors

	/** default constructor */
	public TbUserRole() {
	}

	/** full constructor */
	public TbUserRole(TbUser tbUser, TbRole tbRole) {
		this.tbUser = tbUser;
		this.tbRole = tbRole;
	}

	// Property accessors

	public TbUser getTbUser() {
		return tbUser;
	}

	public void setTbUser(TbUser tbUser) {
		this.tbUser = tbUser;
	}

	public TbRole getTbRole() {
		return tbRole;
	}

	public void setTbRole(TbRole tbRole) {
		this.tbRole = tbRole;
	}
}