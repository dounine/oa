package net.yasion.common.model;

/**
 * TbRolePermission entity. @author MyEclipse Persistence Tools
 */

public class TbRolePermission extends BaseModel<String> {

	private static final long serialVersionUID = 8682188274928650395L;

	// Fields

	private TbRole tbRole;
	private TbPermission tbPermission;

	// Constructors

	/** default constructor */
	public TbRolePermission() {
	}

	/** full constructor */
	public TbRolePermission(TbRole tbRole, TbPermission tbPermission) {
		this.tbRole = tbRole;
		this.tbPermission = tbPermission;
	}

	// Property accessors

	public TbRole getTbRole() {
		return tbRole;
	}

	public void setTbRole(TbRole tbRole) {
		this.tbRole = tbRole;
	}

	public TbPermission getTbPermission() {
		return tbPermission;
	}

	public void setTbPermission(TbPermission tbPermission) {
		this.tbPermission = tbPermission;
	}
}