package net.yasion.common.dto;

/**
 * TbQuestionnaireQuestionRelation entity. @author MyEclipse Persistence Tools
 */

public class QuestionnaireQuestionRelationDTO extends BaseDTO<String> {

	// Fields
	private String questionnaireId;
	private String questionnaireQuestionId;

	// Constructors

	/** default constructor */
	public QuestionnaireQuestionRelationDTO() {
	}

	/** full constructor */
	public QuestionnaireQuestionRelationDTO(String questionnaireId, String questionnaireQuestionId) {
		this.questionnaireId = questionnaireId;
		this.questionnaireQuestionId = questionnaireQuestionId;
	}

	// Property accessors

	public String getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionnaireQuestionId() {
		return this.questionnaireQuestionId;
	}

	public void setQuestionnaireQuestionId(String questionnaireQuestionId) {
		this.questionnaireQuestionId = questionnaireQuestionId;
	}
}