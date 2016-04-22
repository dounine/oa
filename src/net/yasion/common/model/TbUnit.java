package net.yasion.common.model;

import java.util.HashSet;
import java.util.Set;

public class TbUnit extends BaseModel<String> {

	private static final long serialVersionUID = 4657080002958934334L;

	// Fields
	private String name;
	private String descr;
	private String code;
	private String address;
	private String postcode;
	private String phone;
	// 开放项目申报的时间
	private String startTime;
	private String endTime;
	// 多对多
	// private Set<TbUserUnitRelating> tbUserUnitRelatings = new HashSet<TbUserUnitRelating>(0);
	private TbUnit parent;
	private Set<TbUser> tbUsers = new HashSet<TbUser>(0);
	private Set<TbUnit> subUnits = new HashSet<TbUnit>(0);

	public TbUnit() {
	}

	public TbUnit(String name, String descr, String code, String address, String postcode, String phone, String startTime, String endTime/* , Set<TbUserUnitRelating> tbUserUnitRelatings */) {
		super();
		this.name = name;
		this.descr = descr;
		this.code = code;
		this.address = address;
		this.postcode = postcode;
		this.phone = phone;
		this.startTime = startTime;
		this.endTime = endTime;
		// this.tbUserUnitRelatings = tbUserUnitRelatings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public TbUnit getParent() {
		return parent;
	}

	public void setParent(TbUnit parent) {
		this.parent = parent;
	}

	public Set<TbUser> getTbUsers() {
		return tbUsers;
	}

	public void setTbUsers(Set<TbUser> tbUsers) {
		this.tbUsers = tbUsers;
	}

	public Set<TbUnit> getSubUnits() {
		return subUnits;
	}

	public void setSubUnits(Set<TbUnit> subUnits) {
		this.subUnits = subUnits;
	}

	// public Set<TbUserUnitRelating> getTbUserUnitRelatings() {
	// return tbUserUnitRelatings;
	// }
	//
	// public void setTbUserUnitRelatings(Set<TbUserUnitRelating> tbUserUnitRelatings) {
	// this.tbUserUnitRelatings = tbUserUnitRelatings;
	// }
}