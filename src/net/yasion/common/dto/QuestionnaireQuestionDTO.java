package net.yasion.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

/**
 * TbQuestionnaireQuestion entity. @author MyEclipse Persistence Tools
 */

public class QuestionnaireQuestionDTO extends BaseDTO<String> {

	// Fields
	private String name;
	private String code;
	private String question;
	private String type;
	private String descr;
	private String[] questionnaireOptionIds;
	private String[] questionnaireAnswerIds;
	private String[] questionnaireQuestionRelationIds;
	private String[] questionnaireIds;
	private QuestionnaireOptionDTO[] optionDTOs;

	// Constructors

	/** default constructor */
	public QuestionnaireQuestionDTO() {
	}

	/** full constructor */
	public QuestionnaireQuestionDTO(String name, String code, String question, String type, String descr) {
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

	public String[] getQuestionnaireOptionIds() {
		return questionnaireOptionIds;
	}

	public void setQuestionnaireOptionIds(String[] questionnaireOptionIds) {
		this.questionnaireOptionIds = questionnaireOptionIds;
	}

	public String[] getQuestionnaireAnswerIds() {
		return questionnaireAnswerIds;
	}

	public void setQuestionnaireAnswerIds(String[] questionnaireAnswerIds) {
		this.questionnaireAnswerIds = questionnaireAnswerIds;
	}

	public String[] getQuestionnaireQuestionRelationIds() {
		return questionnaireQuestionRelationIds;
	}

	public void setQuestionnaireQuestionRelationIds(String[] questionnaireQuestionRelationIds) {
		this.questionnaireQuestionRelationIds = questionnaireQuestionRelationIds;
	}

	public String[] getQuestionnaireIds() {
		return questionnaireIds;
	}

	public void setQuestionnaireIds(String[] questionnaireIds) {
		this.questionnaireIds = questionnaireIds;
	}

	public QuestionnaireOptionDTO[] getOptionDTOs() {
		return optionDTOs;
	}

	public void setOptionDTOs(QuestionnaireOptionDTO[] optionDTOs) {
		this.optionDTOs = optionDTOs;
	}

	@Override
	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		HashMap<String, OperatorType> operateRelationMap = new HashMap<String, OperatorType>();
		operateRelationMap.put("name", OperatorType.LIKE);
		operateRelationMap.put("code", OperatorType.LIKE);
		operateRelationMap.put("question", OperatorType.LIKE);
		operateRelationMap.put("type", OperatorType.LIKE);
		operateRelationMap.put("descr", OperatorType.LIKE);
		this.setOperateRelation(operateRelationMap);
	}
}