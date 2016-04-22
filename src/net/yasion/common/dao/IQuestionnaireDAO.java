package net.yasion.common.dao;

import java.util.List;

import net.yasion.common.dto.QuestionnaireStatisticsDTO;
import net.yasion.common.model.TbQuestionnaire;

public interface IQuestionnaireDAO extends IBaseDAO<TbQuestionnaire, String> {

	public List<QuestionnaireStatisticsDTO> listStatisticsByQuestionnaire(String questionnaireId);

}