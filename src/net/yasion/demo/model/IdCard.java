package net.yasion.demo.model;

import net.yasion.common.model.BaseModel;

public class IdCard extends BaseModel<String> {

	private static final long serialVersionUID = -2480410455383311060L;

	private String serial;

	private String info;

	private User user;

	public IdCard() {
		super();
	}

	public IdCard(String serial, String info) {
		super();
		this.serial = serial;
		this.info = info;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}