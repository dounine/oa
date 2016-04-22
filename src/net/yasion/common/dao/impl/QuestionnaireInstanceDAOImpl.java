package net.yasion.common.dao.impl;


import net.yasion.common.dao.IQuestionnaireInstanceDAO;
import net.yasion.common.model.TbQuestionnaireInstance;
import org.springframework.stereotype.Repository;


	@Repository("questionnaireInstanceDAO")
	public class QuestionnaireInstanceDAOImpl extends BaseDAOImpl<TbQuestionnaireInstance, String> implements IQuestionnaireInstanceDAO {

		@Override
		protected Class<TbQuestionnaireInstance> getEntityClass() {
			return TbQuestionnaireInstance.class;
		}
}
