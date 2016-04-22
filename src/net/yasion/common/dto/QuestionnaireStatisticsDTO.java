package net.yasion.common.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class QuestionnaireStatisticsDTO extends TransformBaseDTO {

	public String questionnaireQuestionName;
	public String questionnaireQuestionId;
	public String questionnaireOptionId;
	public String questionnaireOptionContent;
	public BigInteger optionCount;
	public BigDecimal ocsc;

	public String getQuestionnaireQuestionName() {
		return questionnaireQuestionName;
	}

	public void setQuestionnaireQuestionName(String questionnaireQuestionName) {
		this.questionnaireQuestionName = questionnaireQuestionName;
	}

	public String getQuestionnaireQuestionId() {
		return questionnaireQuestionId;
	}

	public void setQuestionnaireQuestionId(String questionnaireQuestionId) {
		this.questionnaireQuestionId = questionnaireQuestionId;
	}

	public String getQuestionnaireOptionId() {
		return questionnaireOptionId;
	}

	public void setQuestionnaireOptionId(String questionnaireOptionId) {
		this.questionnaireOptionId = questionnaireOptionId;
	}

	public String getQuestionnaireOptionContent() {
		return questionnaireOptionContent;
	}

	public void setQuestionnaireOptionContent(String questionnaireOptionContent) {
		this.questionnaireOptionContent = questionnaireOptionContent;
	}

	public BigInteger getOptionCount() {
		return optionCount;
	}

	public void setOptionCount(BigInteger optionCount) {
		this.optionCount = optionCount;
	}

	public BigDecimal getOcsc() {
		return ocsc;
	}

	public void setOcsc(BigDecimal ocsc) {
		this.ocsc = ocsc;
	}
}