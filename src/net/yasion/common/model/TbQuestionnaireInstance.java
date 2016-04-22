package net.yasion.common.model;

import java.util.Set;

/**
 * TbQuestionnaireInstance entity. @author MyEclipse Persistence Tools
 */

public class TbQuestionnaireInstance extends BaseModel<String> {

	private static final long serialVersionUID = 1260655970171949382L;
	// Fields
	private String fillTime;
	private String applicant;
	private String applicantMobile;
	private String ipAddress;
	private TbQuestionnaire tbQuestionnaire;
	private Set<TbQuestionnaireAnswer> tbQuestionnaireAnswers;

	// Constructors

	/** default constructor */
	public TbQuestionnaireInstance() {
	}

	/** full constructor */
	public TbQuestionnaireInstance(String fillTime, String applicant, String applicantMobile) {
		this.fillTime = fillTime;
		this.applicant = applicant;
		this.applicantMobile = applicantMobile;
	}

	// Property accessors

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

	public Set<TbQuestionnaireAnswer> getTbQuestionnaireAnswers() {
		return tbQuestionnaireAnswers;
	}

	public void setTbQuestionnaireAnswers(Set<TbQuestionnaireAnswer> tbQuestionnaireAnswers) {
		this.tbQuestionnaireAnswers = tbQuestionnaireAnswers;
	}

	public TbQuestionnaire getTbQuestionnaire() {
		return tbQuestionnaire;
	}

	public void setTbQuestionnaire(TbQuestionnaire tbQuestionnaire) {
		this.tbQuestionnaire = tbQuestionnaire;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}