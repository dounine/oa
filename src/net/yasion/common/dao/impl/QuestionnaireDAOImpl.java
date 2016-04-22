package net.yasion.common.dao.impl;

import java.util.List;

import net.yasion.common.dao.IQuestionnaireDAO;
import net.yasion.common.dto.QuestionnaireStatisticsDTO;
import net.yasion.common.model.TbQuestionnaire;
import net.yasion.common.support.common.dao.callback.ParameterSetterCallback;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("questionnaireDAO")
public class QuestionnaireDAOImpl extends BaseDAOImpl<TbQuestionnaire, String> implements IQuestionnaireDAO {

	@Override
	public List<QuestionnaireStatisticsDTO> listStatisticsByQuestionnaire(final String questionnaireId) {
		String sql = "SELECT q.name AS questionnaire_question_name, rate.questionnaire_question_id AS questionnaire_question_id, rate.questionnaire_option_id AS questionnaire_option_id, o.content AS questionnaire_option_content, rate.option_count AS option_count, rate.ocsc AS ocsc "
				+ "FROM (SELECT optct.questionnaire_question_id, optct.questionnaire_option_id, optct.option_count, (optct.option_count / optsc.opt_sumc) AS ocsc "
				+ "FROM (SELECT a.questionnaire_question_id, a.questionnaire_option_id, COUNT(a.questionnaire_option_id) AS option_count "
				+ "FROM tb_questionnaire_answer a INNER JOIN tb_questionnaire_instance i ON a.questionnaire_instance_id = i.id WHERE i.questionnaire_id = :questionnaireId0 AND a.questionnaire_option_id IS NOT NULL "
				+ "GROUP BY a.questionnaire_question_id, a.questionnaire_option_id) optct LEFT JOIN( "
				+ "SELECT a.questionnaire_question_id, COUNT(a.questionnaire_option_id) AS opt_sumc FROM tb_questionnaire_answer a INNER JOIN tb_questionnaire_instance i ON a.questionnaire_instance_id = i.id "
				+ "WHERE i.questionnaire_id = :questionnaireId1 GROUP BY a.questionnaire_question_id) optsc ON optct.questionnaire_question_id = optsc.questionnaire_question_id) rate "
				+ "INNER JOIN tb_questionnaire_option o ON rate.questionnaire_option_id = o.id INNER JOIN tb_questionnaire_question q ON rate.questionnaire_question_id = q.id WHERE q.flag <> 'D' OR q.flag IS NULL";

		IResultSet<QuestionnaireStatisticsDTO> listBySQL = this.listBySQL(sql, QuestionnaireStatisticsDTO.class, new ParameterSetterCallback() {
			@Override
			public void onSetter(Query query) {
				query.setParameter("questionnaireId0", questionnaireId);
				query.setParameter("questionnaireId1", questionnaireId);
			}
		});
		return listBySQL.getResultList();
	}

	@Override
	protected Class<TbQuestionnaire> getEntityClass() {
		return TbQuestionnaire.class;
	}
}