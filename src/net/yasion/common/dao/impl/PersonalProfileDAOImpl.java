package net.yasion.common.dao.impl;

import net.yasion.common.dao.IPersonalProfileDAO;
import net.yasion.common.model.TbPersonalProfile;

import org.springframework.stereotype.Repository;

@Repository("personalProfileDAO")
public class PersonalProfileDAOImpl extends BaseDAOImpl<TbPersonalProfile, String> implements IPersonalProfileDAO {

	@Override
	protected Class<TbPersonalProfile> getEntityClass() {
		return TbPersonalProfile.class;
	}
}