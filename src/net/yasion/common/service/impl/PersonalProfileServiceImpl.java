package net.yasion.common.service.impl;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IPersonalProfileDAO;
import net.yasion.common.dto.PersonalProfileDTO;
import net.yasion.common.model.TbPersonalProfile;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IPersonalProfileService;
import net.yasion.common.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personalProfileService")
@Transactional
public class PersonalProfileServiceImpl extends BaseServiceImpl<TbPersonalProfile, PersonalProfileDTO, String> implements IPersonalProfileService {

	private IPersonalProfileDAO personalProfileDAO;

	public IPersonalProfileDAO getPersonalProfileDAO() {
		return personalProfileDAO;
	}

	@Autowired
	public void setPersonalProfileDAO(IPersonalProfileDAO personalProfileDAO) {
		this.personalProfileDAO = personalProfileDAO;
	}

	@Override
	protected IBaseDAO<TbPersonalProfile, String> getDefaultDAO() {
		return this.personalProfileDAO;
	}

	@Override
	public TbPersonalProfile getCurrentUserPersonalProfile() {
		TbUser currentUser = UserUtils.getCurrentUser();
		return this.personalProfileDAO.getUniqueByProperty("tbUser.id", currentUser.getId());
	}

	@Override
	public TbPersonalProfile save(PersonalProfileDTO dto) {
		try {
			TbPersonalProfile entity = new TbPersonalProfile();
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
}