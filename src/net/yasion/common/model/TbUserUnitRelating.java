package net.yasion.common.model;

public class TbUserUnitRelating extends BaseModel<String> {

	private static final long serialVersionUID = 7861713471044479341L;

	// Fields
	private Boolean disable;
	// 一对多
	private TbUser tbUser;
	private TbUnit tbUnit;

	public TbUserUnitRelating() {
	}

	public TbUserUnitRelating(Boolean disable, TbUser tbUser, TbUnit tbUnit) {
		super();
		this.disable = disable;
		this.tbUser = tbUser;
		this.tbUnit = tbUnit;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public TbUser getTbUser() {
		return tbUser;
	}

	public void setTbUser(TbUser tbUser) {
		this.tbUser = tbUser;
	}

	public TbUnit getTbUnit() {
		return tbUnit;
	}

	public void setTbUnit(TbUnit tbUnit) {
		this.tbUnit = tbUnit;
	}
}