package net.yasion.common.model;

/**
 * TbQuestionnaireAnswer entity. @author MyEclipse Persistence Tools
 */

public class TbQuestionnaireAnswer extends BaseModel<String> {

	private static final long serialVersionUID = 3495766707522976541L;
	// Fields
	private String content;
	private TbQuestionnaireInstance tbQuestionnaireInstance;
	private TbQuestionnaireQuestion tbQuestionnaireQuestion;
	private TbQuestionnaireOption tbQuestionnaireOption;

	// Constructors

	/** default constructor */
	public TbQuestionnaireAnswer() {
	}

	/** full constructor */
	public TbQuestionnaireAnswer(String content) {
		this.content = content;
	}

	// Property accessors

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TbQuestionnaireInstance getTbQuestionnaireInstance() {
		return tbQuestionnaireInstance;
	}

	public void setTbQuestionnaireInstance(TbQuestionnaireInstance tbQuestionnaireInstance) {
		this.tbQuestionnaireInstance = tbQuestionnaireInstance;
	}

	public TbQuestionnaireQuestion getTbQuestionnaireQuestion() {
		return tbQuestionnaireQuestion;
	}

	public void setTbQuestionnaireQuestion(TbQuestionnaireQuestion tbQuestionnaireQuestion) {
		this.tbQuestionnaireQuestion = tbQuestionnaireQuestion;
	}

	public TbQuestionnaireOption getTbQuestionnaireOption() {
		return tbQuestionnaireOption;
	}

	public void setTbQuestionnaireOption(TbQuestionnaireOption tbQuestionnaireOption) {
		this.tbQuestionnaireOption = tbQuestionnaireOption;
	}
}