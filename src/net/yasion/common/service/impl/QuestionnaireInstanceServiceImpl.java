package net.yasion.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IQuestionnaireInstanceDAO;
import net.yasion.common.dto.QuestionnaireAnswerDTO;
import net.yasion.common.dto.QuestionnaireInstanceDTO;
import net.yasion.common.model.TbQuestionnaire;
import net.yasion.common.model.TbQuestionnaireAnswer;
import net.yasion.common.model.TbQuestionnaireInstance;
import net.yasion.common.model.TbQuestionnaireOption;
import net.yasion.common.model.TbQuestionnaireQuestion;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IQuestionnaireInstanceService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("questionnaireInstanceService")
@Transactional
public class QuestionnaireInstanceServiceImpl extends BaseServiceImpl<TbQuestionnaireInstance, QuestionnaireInstanceDTO, String> implements IQuestionnaireInstanceService {

	private IQuestionnaireInstanceDAO questionnaireInstanceDAO;

	public IQuestionnaireInstanceDAO getQuestionnaireInstanceDAO() {
		return questionnaireInstanceDAO;
	}

	@Autowired
	public void setQuestionnaireInstanceDAO(IQuestionnaireInstanceDAO questionnaireInstanceDAO) {
		this.questionnaireInstanceDAO = questionnaireInstanceDAO;
	}

	@Override
	public TbQuestionnaireInstance save(QuestionnaireInstanceDTO dto) {
		TbQuestionnaireInstance entity = new TbQuestionnaireInstance();
		dto.copyValuesTo(entity);
		TbUnit currentUnit = UserUtils.getCurrentUnit();
		String unitId = dto.getOperatedUnitId();
		unitId = (StringUtils.isNotBlank(unitId) ? unitId : (null != currentUnit ? currentUnit.getId() : null));
		if (StringUtils.isNotBlank(dto.getQuestionnaireId())) {
			TbQuestionnaire tbQuestionnaire = new TbQuestionnaire();
			tbQuestionnaire.setId(dto.getQuestionnaireId());
			entity.setTbQuestionnaire(tbQuestionnaire);
		}
		Set<TbQuestionnaireAnswer> tbQuestionnaireAnswers = new LinkedHashSet<TbQuestionnaireAnswer>();
		QuestionnaireAnswerDTO[] answerDTOs = dto.getAnswerDTOs();
		for (int i = 0, len = answerDTOs.length; i < len; i++) {
			QuestionnaireAnswerDTO questionnaireAnswerDTO = answerDTOs[i];
			TbQuestionnaireAnswer questionnaireAnswer = new TbQuestionnaireAnswer();
			questionnaireAnswerDTO.copyValuesTo(questionnaireAnswer);
			if (StringUtils.isNotBlank(questionnaireAnswerDTO.getQuestionnaireOptionId())) {
				TbQuestionnaireOption tbQuestionnaireOption = new TbQuestionnaireOption();
				tbQuestionnaireOption.setId(questionnaireAnswerDTO.getQuestionnaireOptionId());
				questionnaireAnswer.setTbQuestionnaireOption(tbQuestionnaireOption);
			}
			if (StringUtils.isNotBlank(questionnaireAnswerDTO.getQuestionnaireQuestionId())) {
				TbQuestionnaireQuestion tbQuestionnaireQuestion = new TbQuestionnaireQuestion();
				tbQuestionnaireQuestion.setId(questionnaireAnswerDTO.getQuestionnaireQuestionId());
				questionnaireAnswer.setTbQuestionnaireQuestion(tbQuestionnaireQuestion);
			}
			questionnaireAnswer.setTbQuestionnaireInstance(entity);
			TbUser currentUser = UserUtils.getCurrentUser();
			if (null != currentUser) {
				questionnaireAnswer.setCreateUserId(currentUser.getId());
			}
			questionnaireAnswer.setCreateUnitId(unitId);
			questionnaireAnswer.setCreateDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
			tbQuestionnaireAnswers.add(questionnaireAnswer);
		}
		entity.setTbQuestionnaireAnswers(tbQuestionnaireAnswers);
		this.setEntityCreateDefaultValue(entity, dto);
		return this.save(entity);
	}

	@Override
	public IResultSet<TbQuestionnaireInstance> lFindByDTO(QuestionnaireInstanceDTO dto, Integer pageNumber, Integer pageSize) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(dto);
		if (null != logicalDeleteCriterion) {
			criterionList.add(logicalDeleteCriterion);
		}
		if (StringUtils.isNotBlank(dto.getQuestionnaireId())) {
			criterionList.add(Restrictions.eq((StringUtils.isBlank(dto.getCriterionAlias()) ? "tbQuestionnaire.id" : dto.getCriterionAlias() + ".tbQuestionnaire.id"), dto.getQuestionnaireId()));
		}
		return this.findByDTO(dto, pageNumber, pageSize, criterionList);
	}

	@Override
	protected IBaseDAO<TbQuestionnaireInstance, String> getDefaultDAO() {
		return this.questionnaireInstanceDAO;
	}
}