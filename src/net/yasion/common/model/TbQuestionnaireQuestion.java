package net.yasion.common.model;

import java.util.Set;

/**
 * TbQuestionnaireQuestion entity. @author MyEclipse Persistence Tools
 */

public class TbQuestionnaireQuestion extends BaseModel<String> {

	private static final long serialVersionUID = -2220766469261402580L;
	// Fields
	private String name;
	private String code;
	private String question;
	private String type;
	private String descr;
	private Set<TbQuestionnaireOption> tbQuestionnaireOptions;
	private Set<TbQuestionnaireAnswer> tbQuestionnaireAnswers;
	private Set<TbQuestionnaireQuestionRelation> tbQuestionnaireQuestionRelations;

	// Constructors

	/** default constructor */
	public TbQuestionnaireQuestion() {
	}

	/** full constructor */
	public TbQuestionnaireQuestion(String name, String code, String question, String type, String descr) {
		this.name = name;
		this.code = code;
		this.question = question;
		this.type = type;
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

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Set<TbQuestionnaireOption> getTbQuestionnaireOptions() {
		return tbQuestionnaireOptions;
	}

	public void setTbQuestionnaireOptions(Set<TbQuestionnaireOption> tbQuestionnaireOptions) {
		this.tbQuestionnaireOptions = tbQuestionnaireOptions;
	}

	public Set<TbQuestionnaireAnswer> getTbQuestionnaireAnswers() {
		return tbQuestionnaireAnswers;
	}

	public void setTbQuestionnaireAnswers(Set<TbQuestionnaireAnswer> tbQuestionnaireAnswers) {
		this.tbQuestionnaireAnswers = tbQuestionnaireAnswers;
	}

	public Set<TbQuestionnaireQuestionRelation> getTbQuestionnaireQuestionRelations() {
		return tbQuestionnaireQuestionRelations;
	}

	public void setTbQuestionnaireQuestionRelations(Set<TbQuestionnaireQuestionRelation> tbQuestionnaireQuestionRelations) {
		this.tbQuestionnaireQuestionRelations = tbQuestionnaireQuestionRelations;
	}
}