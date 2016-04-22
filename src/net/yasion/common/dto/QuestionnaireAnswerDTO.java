package net.yasion.common.dto;

/**
 * TbQuestionnaireAnswer entity. @author MyEclipse Persistence Tools
 */

public class QuestionnaireAnswerDTO extends BaseDTO<String> {

	// Fields
	private String questionnaireInstanceId;
	private String questionnaireQuestionId;
	private String questionnaireOptionId;
	private String content;

	// Constructors

	/** default constructor */
	public QuestionnaireAnswerDTO() {
	}

	/** full constructor */
	public QuestionnaireAnswerDTO(String questionnaireInstanceId, String questionnaireQuestionId, String questionnaireOptionId, String content) {
		this.questionnaireInstanceId = questionnaireInstanceId;
		this.questionnaireQuestionId = questionnaireQuestionId;
		this.questionnaireOptionId = questionnaireOptionId;
		this.content = content;
	}

	// Property accessors

	public String getQuestionnaireInstanceId() {
		return this.questionnaireInstanceId;
	}

	public void setQuestionnaireInstanceId(String questionnaireInstanceId) {
		this.questionnaireInstanceId = questionnaireInstanceId;
	}

	public String getQuestionnaireQuestionId() {
		return this.questionnaireQuestionId;
	}

	public void setQuestionnaireQuestionId(String questionnaireQuestionId) {
		this.questionnaireQuestionId = questionnaireQuestionId;
	}

	public String getQuestionnaireOptionId() {
		return this.questionnaireOptionId;
	}

	public void setQuestionnaireOptionId(String questionnaireOptionId) {
		this.questionnaireOptionId = questionnaireOptionId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}