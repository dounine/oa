package net.yasion.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

public class UserDTO extends BaseDTO<String> {

	private String username;
	private String oldPassword;
	private String password;
	private String confirmPassword;
	private String name;
	private String jobTitle;
	private String education;
	private String mobile;
	private String phone;
	private String email;
	private String sex;
	private String disable;
	private String regiTime;
	private Integer level;
	private String disableDatetime;
	// 多对多
	// private String[] unitIds;
	private String unitId;
	private String personalProfileId;
	private String[] personalExperiencesIds;

	public UserDTO() {
		super();
	}

	public UserDTO(String username, String password, String name, String jobTitle, String education, String mobile, String phone, String email, String sex, String disable, String regiTime, Integer level, String disableDatetime/* , String[] unitIds */) {
		super();
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
		this.level = level;
		this.disableDatetime = disableDatetime;
		// this.unitIds = unitIds;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(String regiTime) {
		this.regiTime = regiTime;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getDisableDatetime() {
		return disableDatetime;
	}

	public void setDisableDatetime(String disableDatetime) {
		this.disableDatetime = disableDatetime;
	}

	// public String[] getUnitIds() {
	// return unitIds;
	// }
	//
	// public void setUnitIds(String[] unitIds) {
	// this.unitIds = unitIds;
	// }

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getPersonalProfileId() {
		return personalProfileId;
	}

	public void setPersonalProfileId(String personalProfileId) {
		this.personalProfileId = personalProfileId;
	}

	public String[] getPersonalExperiencesIds() {
		return personalExperiencesIds;
	}

	public void setPersonalExperiencesIds(String[] personalExperiencesIds) {
		this.personalExperiencesIds = personalExperiencesIds;
	}

	@Override
	public boolean isExcludedProperty(String propertyName, Object propertyValue) {
		return (super.isExcludedProperty(propertyName, propertyValue) || "unitId".equals(propertyValue) || "personalProfileId".equals(propertyValue) || "personalExperiencesIds".equals(propertyValue));
	}

	@Override
	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		HashMap<String, OperatorType> operateRelationMap = new HashMap<String, OperatorType>();
		operateRelationMap.put("username", OperatorType.LIKE);
		operateRelationMap.put("name", OperatorType.LIKE);
		operateRelationMap.put("mobile", OperatorType.LIKE);
		operateRelationMap.put("phone", OperatorType.LIKE);
		operateRelationMap.put("email", OperatorType.LIKE);
		operateRelationMap.put("disable", OperatorType.EQ);
		this.setOperateRelation(operateRelationMap);
	}
}