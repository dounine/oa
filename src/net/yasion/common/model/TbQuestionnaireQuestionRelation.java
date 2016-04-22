package net.yasion.common.model;

/**
 * TbQuestionnaireQuestionRelation entity. @author MyEclipse Persistence Tools
 */

public class TbQuestionnaireQuestionRelation extends BaseModel<String> {

	private static final long serialVersionUID = -1819705117997078634L;
	// Fields
	private TbQuestionnaire tbQuestionnaire;
	private TbQuestionnaireQuestion tbQuestionnaireQuestion;

	// Constructors

	/** default constructor */
	public TbQuestionnaireQuestionRelation() {
	}

	// Property accessors
	public TbQuestionnaire getTbQuestionnaire() {
		return tbQuestionnaire;
	}

	public void setTbQuestionnaire(TbQuestionnaire tbQuestionnaire) {
		this.tbQuestionnaire = tbQuestionnaire;
	}

	public TbQuestionnaireQuestion getTbQuestionnaireQuestion() {
		return tbQuestionnaireQuestion;
	}

	public void setTbQuestionnaireQuestion(TbQuestionnaireQuestion tbQuestionnaireQuestion) {
		this.tbQuestionnaireQuestion = tbQuestionnaireQuestion;
	}
}