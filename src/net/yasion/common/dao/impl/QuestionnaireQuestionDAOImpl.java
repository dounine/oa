package net.yasion.common.dao.impl;

import net.yasion.common.dao.IQuestionnaireQuestionDAO;
import net.yasion.common.model.TbQuestionnaireQuestion;


import org.springframework.stereotype.Repository;


@Repository("questionnaireQuestionDAO")
public class QuestionnaireQuestionDAOImpl extends BaseDAOImpl<TbQuestionnaireQuestion, String> implements IQuestionnaireQuestionDAO {

	@Override
	protected Class<TbQuestionnaireQuestion> getEntityClass() {
		return TbQuestionnaireQuestion.class;
	}
}
