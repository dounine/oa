package net.yasion.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.yasion.common.support.common.service.enumeration.OperatorType;

/**
 * TbQuestionnaireOption entity. @author MyEclipse Persistence Tools
 */

public class QuestionnaireOptionDTO extends BaseDTO<String> {

	// Fields
	private String name;
	private String code;
	private String content;
	private String questionnaireQuestionId;
	private String descr;
	private String[] questionnaireAnswerIds;

	// Constructors

	/** default constructor */
	public QuestionnaireOptionDTO() {
	}

	/** full constructor */
	public QuestionnaireOptionDTO(String name, String code, String content, String questionnaireQuestionId, String descr) {
		this.name = name;
		this.code = code;
		this.content = content;
		this.questionnaireQuestionId = questionnaireQuestionId;
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

	public String getQuestionnaireQuestionId() {
		return this.questionnaireQuestionId;
	}

	public void setQuestionnaireQuestionId(String questionnaireQuestionId) {
		this.questionnaireQuestionId = questionnaireQuestionId;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String[] getQuestionnaireAnswerIds() {
		return questionnaireAnswerIds;
	}

	public void setQuestionnaireAnswerIds(String[] questionnaireAnswerIds) {
		this.questionnaireAnswerIds = questionnaireAnswerIds;
	}
	
	@Override
	public void generateDefOperateRelation(String requestURL, Map<String, String[]> paramsMap) {
		HashMap<String, OperatorType> operateRelationMap = new HashMap<String, OperatorType>();
		operateRelationMap.put("name", OperatorType.LIKE);
		operateRelationMap.put("code", OperatorType.LIKE);
		operateRelationMap.put("content", OperatorType.LIKE);
		operateRelationMap.put("questionnaireQuestionId", OperatorType.EQ);
		operateRelationMap.put("desc", OperatorType.LIKE);
		this.setOperateRelation(operateRelationMap);
	}
	
}