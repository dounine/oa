package net.yasion.common.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IQuestionnaireQuestionDAO;
import net.yasion.common.dto.QuestionnaireQuestionDTO;
import net.yasion.common.model.TbQuestionnaireOption;
import net.yasion.common.model.TbQuestionnaireQuestion;
import net.yasion.common.model.TbQuestionnaireQuestionRelation;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IQuestionnaireQuestionService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.utils.UserUtils;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("questionnaireQuestionService")
@Transactional
public class QuestionnaireQuestionServiceImpl extends BaseServiceImpl<TbQuestionnaireQuestion, QuestionnaireQuestionDTO, String> implements IQuestionnaireQuestionService {

	private IQuestionnaireQuestionDAO questionnaireQuestionDAO;

	public IQuestionnaireQuestionDAO getQuestionnaireQuestionDAO() {
		return questionnaireQuestionDAO;
	}

	@Autowired
	public void setQuestionnaireQuestionDAO(IQuestionnaireQuestionDAO questionnaireQuestionDAO) {
		this.questionnaireQuestionDAO = questionnaireQuestionDAO;
	}

	@Override
	public TbQuestionnaireQuestion save(QuestionnaireQuestionDTO dto) {
		TbQuestionnaireQuestion entity = new TbQuestionnaireQuestion();
		dto.copyValuesTo(entity);
		Set<TbQuestionnaireOption> tbQuestionnaireOptions = new LinkedHashSet<TbQuestionnaireOption>();
		for (int i = 0; i < dto.getOptionDTOs().length; i++) {
			TbQuestionnaireOption questionnaireOption = new TbQuestionnaireOption();
			questionnaireOption.setContent(dto.getOptionDTOs()[i].getContent());
			questionnaireOption.setDescr(dto.getOptionDTOs()[i].getDescr());
			questionnaireOption.copyValuesTo(dto.getOptionDTOs()[i]);
			tbQuestionnaireOptions.add(questionnaireOption);
		}
		entity.setTbQuestionnaireOptions(tbQuestionnaireOptions);
		this.setEntityCreateDefaultValue(entity, dto);
		return this.save(entity);
	}

	@Override
	public TbQuestionnaireQuestion update(QuestionnaireQuestionDTO dto) {
		TbQuestionnaireQuestion entity = questionnaireQuestionDAO.get(dto.getId());
		dto.copyValuesTo(entity);
		Set<TbQuestionnaireOption> tbQuestionnaireOptions = entity.getTbQuestionnaireOptions();
		tbQuestionnaireOptions.clear();
		for (int i = 0; i < dto.getOptionDTOs().length; i++) {
			TbQuestionnaireOption questionnaireOption = new TbQuestionnaireOption();
			questionnaireOption.setContent(dto.getOptionDTOs()[i].getContent());
			questionnaireOption.setDescr(dto.getOptionDTOs()[i].getDescr());
			questionnaireOption.copyValuesTo(dto.getOptionDTOs()[i]);
			tbQuestionnaireOptions.add(questionnaireOption);
		}
		this.setEntityModifiedDefaultValue(entity, dto);
		return this.update(entity);
	}

	@Override
	public List<TbQuestionnaireQuestion> findByCodes(String[] codes) {
		Criterion criterion = null;
		for (int i = 0, len = codes.length; i < len; i++) {
			Criterion thisCriterion = Restrictions.eq("code", codes[i]);
			if (null != criterion) {
				criterion = Restrictions.or(criterion, thisCriterion);
			} else {
				criterion = thisCriterion;
			}
		}
		IResultSet<TbQuestionnaireQuestion> entityList = questionnaireQuestionDAO.listByCriteria(new Criterion[] { criterion }, new Order[] { Order.desc("id") }, null);
		return entityList.getResultList();
	}

	@Override
	public Integer lRemoveById(String id) {
		TbQuestionnaireQuestion entity = this.getDefaultDAO().get(id);
		setEntityLogicalDeleteValue(entity);
		TbUser currentUser = UserUtils.getCurrentUser();
		String unitId = null;
		String userId = null;
		if (null != currentUser) {
			if (currentUser instanceof TbSuperUser) {
				unitId = CommonConstants.ADMIN_UNIT_ID;
			} else {
				TbUnit unit = currentUser.getTbUnit();
				if (null != unit) {
					unitId = unit.getId();
				} else {
					unitId = "UNKNOW";
				}
			}
			userId = currentUser.getId();
		}
		Set<TbQuestionnaireQuestionRelation> tbQuestionnaireQuestionRelations = entity.getTbQuestionnaireQuestionRelations();
		Iterator<TbQuestionnaireQuestionRelation> tbQuestionnaireQuestionRelationIt = tbQuestionnaireQuestionRelations.iterator();
		while (tbQuestionnaireQuestionRelationIt.hasNext()) {
			TbQuestionnaireQuestionRelation tbQuestionnaireQuestionRelation = tbQuestionnaireQuestionRelationIt.next();
			tbQuestionnaireQuestionRelation.setFlag(CommonConstants.LOGICAL_DELETE_FLAG);
			tbQuestionnaireQuestionRelation.setModifiedDate(dateFormat.format(new Date()));
			tbQuestionnaireQuestionRelation.setModifiedUnitId(unitId);
			tbQuestionnaireQuestionRelation.setModifiedUserId(userId);
		}
		this.update(entity);
		return 1;
	}

	@Override
	protected IBaseDAO<TbQuestionnaireQuestion, String> getDefaultDAO() {
		return this.questionnaireQuestionDAO;
	}
}