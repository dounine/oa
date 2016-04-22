package net.yasion.common.service;

import java.util.List;

import net.yasion.common.dto.QuestionnaireQuestionDTO;
import net.yasion.common.model.TbQuestionnaireQuestion;



public interface IQuestionnaireQuestionService extends IBaseService<TbQuestionnaireQuestion, QuestionnaireQuestionDTO, String> {

	public List<TbQuestionnaireQuestion> findByCodes(String[] codes);
	
}
