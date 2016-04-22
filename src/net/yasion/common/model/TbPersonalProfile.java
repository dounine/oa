package net.yasion.common.model;

/**
 * TbPersonalProfile entity. @author MyEclipse Persistence Tools
 */

public class TbPersonalProfile extends BaseModel<String> {

	private static final long serialVersionUID = -8364193536150533079L;
	// Fields
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
	private TbUser tbUser;

	// Constructors

	/** default constructor */
	public TbPersonalProfile() {
	}

	/** full constructor */
	public TbPersonalProfile(String realName, String englishName, String birthday, String bloodType, String residence, String hometown, String detailedAddress, String qq, String wechat, String photo, String individualResume) {
		this.realName = realName;
		this.englishName = englishName;
		this.birthday = birthday;
		this.bloodType = bloodType;
		this.residence = residence;
		this.hometown = hometown;
		this.detailedAddress = detailedAddress;
		this.qq = qq;
		this.wechat = wechat;
		this.photo = photo;
		this.individualResume = individualResume;
	}

	// Property accessors
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBloodType() {
		return this.bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getResidence() {
		return this.residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getHometown() {
		return this.hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getDetailedAddress() {
		return this.detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return this.wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getIndividualResume() {
		return this.individualResume;
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

	public TbUser getTbUser() {
		return tbUser;
	}

	public void setTbUser(TbUser tbUser) {
		this.tbUser = tbUser;
	}
}