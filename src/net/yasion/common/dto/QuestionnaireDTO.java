package net.yasion.common.dto;

/**
 * TbQuestionnaire entity. @author MyEclipse Persistence Tools
 */

public class QuestionnaireDTO extends BaseDTO<String> {

	// Fields
	private String name;
	private String code;
	private String descr;
	private String[] questionnaireInstanceIds;
	private String[] questionnaireQuestionRelationIds;
	private String[] questionnaireQuestionIds;

	// Constructors

	/** default constructor */
	public QuestionnaireDTO() {
	}

	/** full constructor */
	public QuestionnaireDTO(String name, String code, String descr) {
		this.name = name;
		this.code = code;
		this.descr = descr;
	}

	// Property accessors

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String[] getQuestionnaireInstanceIds() {
		return questionnaireInstanceIds;
	}

	public void setQuestionnaireInstanceIds(String[] questionnaireInstanceIds) {
		this.questionnaireInstanceIds = questionnaireInstanceIds;
	}

	public String[] getQuestionnaireQuestionRelationIds() {
		return questionnaireQuestionRelationIds;
	}

	public void setQuestionnaireQuestionRelationIds(String[] questionnaireQuestionRelationIds) {
		this.questionnaireQuestionRelationIds = questionnaireQuestionRelationIds;
	}

	public String[] getQuestionnaireQuestionIds() {
		return questionnaireQuestionIds;
	}

	public void setQuestionnaireQuestionIds(String[] questionnaireQuestionIds) {
		this.questionnaireQuestionIds = questionnaireQuestionIds;
	}
}