package net.yasion.demo.model;

import java.util.HashSet;
import java.util.Set;

import net.yasion.common.model.BaseModel;

public class User extends BaseModel<String> {

	private static final long serialVersionUID = -3612677492587772622L;

	private String name;

	private Set<Orders> ordersSet = new HashSet<Orders>(0);

	private IdCard idCard;

	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User() {
		super();
	}

	public User(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Orders> getOrdersSet() {
		return ordersSet;
	}

	public void setOrdersSet(Set<Orders> ordersSet) {
		this.ordersSet = ordersSet;
	}

	public IdCard getIdCard() {
		return idCard;
	}

	public void setIdCard(IdCard idCard) {
		this.idCard = idCard;
	}
}