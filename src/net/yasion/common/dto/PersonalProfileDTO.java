package net.yasion.common.dto;

public class PersonalProfileDTO extends BaseDTO<String> {

	private String realName;
	private String englishName;
	private String birthday;
	private String bloodType;
	private String residence;
	private String hometown;
	private String detailedAddress;
	private String qq;
	private String wechat;
	private String individualResume;
	private String photo;
	private String userId;

	public PersonalProfileDTO() {
		super();
	}

	public PersonalProfileDTO(String realName, String englishName, String birthday, String bloodType, String residence, String hometown, String detailedAddress, String qq, String wechat, String individualResume, String photo, String userId) {
		super();
		this.realName = realName;
		this.englishName = englishName;
		this.birthday = birthday;
		this.bloodType = bloodType;
		this.residence = residence;
		this.hometown = hometown;
		this.detailedAddress = detailedAddress;
		this.qq = qq;
		this.wechat = wechat;
		this.individualResume = individualResume;
		this.photo = photo;
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getIndividualResume() {
		return individualResume;
	}

	public void setIndividualResume(String individualResume) {
		this.individualResume = individualResume;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}