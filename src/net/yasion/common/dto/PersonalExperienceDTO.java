package net.yasion.common.dto;

public class PersonalExperienceDTO extends BaseDTO<String> {

	private String startTime;
	private String endTime;
	private String descr;
	private String userId;

	public PersonalExperienceDTO() {
		super();
	}

	public PersonalExperienceDTO(String userId, String startTime, String endTime, String descr) {
		super();
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.descr = descr;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
}