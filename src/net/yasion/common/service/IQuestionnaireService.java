package net.yasion.common.service;

import java.util.List;

import net.yasion.common.dto.QuestionnaireDTO;
import net.yasion.common.dto.QuestionnaireStatisticsDTO;
import net.yasion.common.model.TbQuestionnaire;

public interface IQuestionnaireService extends IBaseService<TbQuestionnaire, QuestionnaireDTO, String> {

	public List<TbQuestionnaire> findByCodes(String[] codes);

	public List<QuestionnaireStatisticsDTO> statisticsByQuestionnaire(String questionnaireId);
}
