package net.yasion.common.service.impl;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IQuestionnaireDAO;
import net.yasion.common.dto.QuestionnaireDTO;
import net.yasion.common.dto.QuestionnaireStatisticsDTO;
import net.yasion.common.model.TbQuestionnaire;
import net.yasion.common.model.TbQuestionnaireQuestion;
import net.yasion.common.model.TbQuestionnaireQuestionRelation;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IQuestionnaireService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("questionnaireService")
@Transactional
public class QuestionnaireServiceImpl extends BaseServiceImpl<TbQuestionnaire, QuestionnaireDTO, String> implements IQuestionnaireService {

	private IQuestionnaireDAO questionnaireDAO;

	public IQuestionnaireDAO getQuestionnaireDAO() {
		return questionnaireDAO;
	}

	@Autowired
	public void setQuestionnaireDAO(IQuestionnaireDAO questionnaireDAO) {
		this.questionnaireDAO = questionnaireDAO;
	}

	@Override
	public List<TbQuestionnaire> findByCodes(String[] codes) {
		Criterion criterion = null;
		for (int i = 0, len = codes.length; i < len; i++) {
			Criterion thisCriterion = Restrictions.eq("code", codes[i]);
			if (null != criterion) {
				criterion = Restrictions.or(criterion, thisCriterion);
			} else {
				criterion = thisCriterion;
			}
		}
		IResultSet<TbQuestionnaire> entityList = questionnaireDAO.listByCriteria(new Criterion[] { criterion }, new Order[] { Order.desc("id") }, null);
		return entityList.getResultList();
	}

	@Override
	public TbQuestionnaire save(QuestionnaireDTO dto) {
		TbQuestionnaire entity = new TbQuestionnaire();
		dto.copyValuesTo(entity);
		TbUser currentUser = UserUtils.getCurrentUser();
		Set<TbQuestionnaireQuestionRelation> tbQuestionnaireQuestionRelations = new LinkedHashSet<TbQuestionnaireQuestionRelation>();
		for (int i = 0; i < dto.getQuestionnaireQuestionIds().length; i++) {
			TbQuestionnaireQuestion question = new TbQuestionnaireQuestion();
			question.setId(dto.getQuestionnaireQuestionIds()[i]);
			TbQuestionnaireQuestionRelation relation = new TbQuestionnaireQuestionRelation();
			relation.setTbQuestionnaireQuestion(question);
			relation.setTbQuestionnaire(entity);
			relation.setCreateUnitId(dto.getOperatedUnitId());
			relation.setCreateDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
			if (null != currentUser) {
				relation.setCreateUserId(currentUser.getId());
			}
			tbQuestionnaireQuestionRelations.add(relation);
		}
		entity.setTbQuestionnaireQuestionRelations(tbQuestionnaireQuestionRelations);
		this.setEntityCreateDefaultValue(entity, dto);
		return this.save(entity);
	}

	@Override
	public TbQuestionnaire update(QuestionnaireDTO dto) {
		TbQuestionnaire entity = questionnaireDAO.get(dto.getId());
		dto.copyValuesTo(entity);
		TbUser currentUser = UserUtils.getCurrentUser();
		Set<TbQuestionnaireQuestionRelation> tbQuestionnaireQuestionRelations = entity.getTbQuestionnaireQuestionRelations();
		tbQuestionnaireQuestionRelations.clear();
		for (int i = 0; i < dto.getQuestionnaireQuestionIds().length; i++) {
			TbQuestionnaireQuestion question = new TbQuestionnaireQuestion();
			question.setId(dto.getQuestionnaireQuestionIds()[i]);
			TbQuestionnaireQuestionRelation relation = new TbQuestionnaireQuestionRelation();
			relation.setTbQuestionnaireQuestion(question);
			relation.setTbQuestionnaire(entity);
			relation.setCreateUnitId(dto.getOperatedUnitId());
			relation.setCreateDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
			if (null != currentUser) {
				relation.setCreateUserId(currentUser.getId());
			}
			tbQuestionnaireQuestionRelations.add(relation);
		}
		this.setEntityModifiedDefaultValue(entity, dto);
		return this.update(entity);
	}

	@Override
	public List<QuestionnaireStatisticsDTO> statisticsByQuestionnaire(String questionnaireId) {
		return questionnaireDAO.listStatisticsByQuestionnaire(questionnaireId);
	}

	@Override
	protected IBaseDAO<TbQuestionnaire, String> getDefaultDAO() {
		return this.questionnaireDAO;
	}
}