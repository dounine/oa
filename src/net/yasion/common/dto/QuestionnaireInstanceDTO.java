package net.yasion.common.dto;

/**
 * TbQuestionnaireInstance entity. @author MyEclipse Persistence Tools
 */

public class QuestionnaireInstanceDTO extends BaseDTO<String> {

	// Fields
	private String questionnaireId;
	private String fillTime;
	private String applicant;
	private String applicantMobile;
	private String ipAddress;
	private String[] questionnaireAnswerIds;
	private QuestionnaireAnswerDTO[] answerDTOs;

	// Constructors

	/** default constructor */
	public QuestionnaireInstanceDTO() {
	}

	/** full constructor */
	public QuestionnaireInstanceDTO(String questionnaireId, String fillTime, String applicant, String applicantMobile) {
		this.questionnaireId = questionnaireId;
		this.fillTime = fillTime;
		this.applicant = applicant;
		this.applicantMobile = applicantMobile;
	}

	// Property accessors

	public String getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getFillTime() {
		return this.fillTime;
	}

	public void setFillTime(String fillTime) {
		this.fillTime = fillTime;
	}

	public String getApplicant() {
		return this.applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getApplicantMobile() {
		return this.applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	public String[] getQuestionnaireAnswerIds() {
		return questionnaireAnswerIds;
	}

	public void setQuestionnaireAnswerIds(String[] questionnaireAnswerIds) {
		this.questionnaireAnswerIds = questionnaireAnswerIds;
	}

	public QuestionnaireAnswerDTO[] getAnswerDTOs() {
		return answerDTOs;
	}

	public void setAnswerDTOs(QuestionnaireAnswerDTO[] answerDTOs) {
		this.answerDTOs = answerDTOs;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public boolean isExcludedProperty(String propertyName, Object propertyValue) {
		return "answerDTOs".equals(propertyName) || "questionnaireId".equals(propertyName) || "questionnaireAnswerIds".equals(propertyName);
	}
}