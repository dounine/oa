package net.yasion.demo.model;

import net.yasion.common.model.BaseModel;

public class Orders extends BaseModel<String> {

	private static final long serialVersionUID = 4185328031052780899L;

	private String name;

	private User user;

	public Orders() {
		super();
	}

	public Orders(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
