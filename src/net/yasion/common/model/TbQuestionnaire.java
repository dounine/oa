package net.yasion.common.model;

import java.util.Set;

/**
 * TbQuestionnaire entity. @author MyEclipse Persistence Tools
 */

public class TbQuestionnaire extends BaseModel<String> {

	private static final long serialVersionUID = -6135867129058025461L;
	// Fields
	private String name;
	private String code;
	private String descr;
	private Set<TbQuestionnaireInstance> tbQuestionnaireInstances;
	private Set<TbQuestionnaireQuestionRelation> tbQuestionnaireQuestionRelations;

	// Constructors

	/** default constructor */
	public TbQuestionnaire() {
	}

	/** full constructor */
	public TbQuestionnaire(String name, String code, String descr) {
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

	public Set<TbQuestionnaireInstance> getTbQuestionnaireInstances() {
		return tbQuestionnaireInstances;
	}

	public void setTbQuestionnaireInstances(Set<TbQuestionnaireInstance> tbQuestionnaireInstances) {
		this.tbQuestionnaireInstances = tbQuestionnaireInstances;
	}

	public Set<TbQuestionnaireQuestionRelation> getTbQuestionnaireQuestionRelations() {
		return tbQuestionnaireQuestionRelations;
	}

	public void setTbQuestionnaireQuestionRelations(Set<TbQuestionnaireQuestionRelation> tbQuestionnaireQuestionRelations) {
		this.tbQuestionnaireQuestionRelations = tbQuestionnaireQuestionRelations;
	}
}