package net.yasion.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IPersonalExperienceDAO;
import net.yasion.common.dto.PersonalExperienceDTO;
import net.yasion.common.model.TbPersonalExperience;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IPersonalExperienceService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personalExperienceService")
@Transactional
public class PersonalExperienceServiceImpl extends BaseServiceImpl<TbPersonalExperience, PersonalExperienceDTO, String> implements IPersonalExperienceService {

	private IPersonalExperienceDAO personalExperienceDAO;

	public IPersonalExperienceDAO getPersonalExperienceDAO() {
		return personalExperienceDAO;
	}

	@Autowired
	public void setPersonalExperienceDAO(IPersonalExperienceDAO personalExperienceDAO) {
		this.personalExperienceDAO = personalExperienceDAO;
	}

	@Override
	public IResultSet<TbPersonalExperience> lFindByDTO(PersonalExperienceDTO dto, Integer pageNumber, Integer pageSize) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(dto);
		if (null != logicalDeleteCriterion) {
			criterionList.add(logicalDeleteCriterion);
		}
		if (StringUtils.isNotBlank(dto.getUserId())) {
			criterionList.add(Restrictions.eq("tbUser.id", dto.getUserId()));
		}
		return this.findByDTO(dto, pageNumber, pageSize, criterionList);
	}

	@Override
	public TbPersonalExperience save(PersonalExperienceDTO dto) {
		try {
			TbPersonalExperience entity = new TbPersonalExperience();
			TbUser currentUser = UserUtils.getCurrentUser();
			entity.setTbUser(currentUser);// 设置关联TbUser
			dto.copyValuesTo(entity);
			setEntityCreateDefaultValue(entity, dto);
			return this.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected IBaseDAO<TbPersonalExperience, String> getDefaultDAO() {
		return this.personalExperienceDAO;
	}
}