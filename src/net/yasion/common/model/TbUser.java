package net.yasion.common.model;

import java.util.Set;

/**
 * TbUser entity. @author MyEclipse Persistence Tools
 */

public class TbUser extends BaseModel<String> {

	private static final long serialVersionUID = 5646145467751526785L;

	// Fields

	private String username;
	private String password;
	private String name;
	private String jobTitle;
	private String education;
	private String mobile;
	private String phone;
	private String email;
	private String sex;
	private String disable;
	private String regiTime;
	private String disableDatetime;
	// 多对多
	// private Set<TbUserUnitRelating> tbUserUnitRelatings = new HashSet<TbUserUnitRelating>(0);
	private TbUnit tbUnit;
	private TbPersonalProfile tbPersonalProfile;
	private Set<TbPersonalExperience> tbPersonalExperiences;

	// Constructors

	/** default constructor */
	public TbUser() {
	}

	/** full constructor */
	public TbUser(String username, String password, String name, String jobTitle, String education, String mobile, String phone, String email, String sex, String disable, String regiTime, String disableDatetime/* , Set<TbUserUnitRelating> tbUserUnitRelatings */) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.jobTitle = jobTitle;
		this.education = education;
		this.mobile = mobile;
		this.phone = phone;
		this.email = email;
		this.sex = sex;
		this.disable = disable;
		this.regiTime = regiTime;
		this.disableDatetime = disableDatetime;
		// this.tbUserUnitRelatings = tbUserUnitRelatings;
	}

	// Property accessors

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDisable() {
		return this.disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getRegiTime() {
		return this.regiTime;
	}

	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}

	public String getDisableDatetime() {
		return this.disableDatetime;
	}

	public void setDisableDatetime(String disableDatetime) {
		this.disableDatetime = disableDatetime;
	}

	public TbUnit getTbUnit() {
		return tbUnit;
	}

	public void setTbUnit(TbUnit tbUnit) {
		this.tbUnit = tbUnit;
	}

	public TbPersonalProfile getTbPersonalProfile() {
		return tbPersonalProfile;
	}

	public void setTbPersonalProfile(TbPersonalProfile tbPersonalProfile) {
		this.tbPersonalProfile = tbPersonalProfile;
	}

	public Set<TbPersonalExperience> getTbPersonalExperiences() {
		return tbPersonalExperiences;
	}

	public void setTbPersonalExperiences(Set<TbPersonalExperience> tbPersonalExperiences) {
		this.tbPersonalExperiences = tbPersonalExperiences;
	}

	// public Set<TbUserUnitRelating> getTbUserUnitRelatings() {
	// return tbUserUnitRelatings;
	// }
	//
	// public void setTbUserUnitRelatings(Set<TbUserUnitRelating> tbUserUnitRelatings) {
	// this.tbUserUnitRelatings = tbUserUnitRelatings;
	// }
}