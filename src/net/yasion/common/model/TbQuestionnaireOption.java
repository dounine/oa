package net.yasion.common.model;

import java.util.Set;

/**
 * TbQuestionnaireOption entity. @author MyEclipse Persistence Tools
 */

public class TbQuestionnaireOption extends BaseModel<String> {

	private static final long serialVersionUID = 2159980236006177297L;
	// Fields
	private String name;
	private String code;
	private String content;
	private String descr;
	private TbQuestionnaireQuestion tbQuestionnaireQuestion;
	private Set<TbQuestionnaireAnswer> tbQuestionnaireAnswers;

	// Constructors

	/** default constructor */
	public TbQuestionnaireOption() {
	}

	/** full constructor */
	public TbQuestionnaireOption(String name, String code, String content, String descr) {
		this.name = name;
		this.code = code;
		this.content = content;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Set<TbQuestionnaireAnswer> getTbQuestionnaireAnswers() {
		return tbQuestionnaireAnswers;
	}

	public void setTbQuestionnaireAnswers(Set<TbQuestionnaireAnswer> tbQuestionnaireAnswers) {
		this.tbQuestionnaireAnswers = tbQuestionnaireAnswers;
	}

	public TbQuestionnaireQuestion getTbQuestionnaireQuestion() {
		return tbQuestionnaireQuestion;
	}

	public void setTbQuestionnaireQuestion(TbQuestionnaireQuestion tbQuestionnaireQuestion) {
		this.tbQuestionnaireQuestion = tbQuestionnaireQuestion;
	}
}