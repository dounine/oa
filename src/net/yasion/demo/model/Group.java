package net.yasion.demo.model;

import java.util.HashSet;
import java.util.Set;

import net.yasion.common.model.BaseModel;

public class Group extends BaseModel<String> {

	private static final long serialVersionUID = -4932414847756585196L;

	private String name;

	private Set<User> userSet = new HashSet<User>(0);

	public Group() {
		super();
	}

	public Group(String name) {
		super();
		this.name = name;
	}

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}