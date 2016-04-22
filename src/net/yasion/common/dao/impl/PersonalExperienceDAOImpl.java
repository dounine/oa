package net.yasion.common.dao.impl;

import net.yasion.common.dao.IPersonalExperienceDAO;
import net.yasion.common.model.TbPersonalExperience;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository("personalExperienceDAO")
public class PersonalExperienceDAOImpl extends BaseDAOImpl<TbPersonalExperience, String> implements IPersonalExperienceDAO {

	protected Order[] getOrders() {
		return new Order[] { Order.asc("startTime"), Order.desc("id") };
	}

	@Override
	protected Class<TbPersonalExperience> getEntityClass() {
		return TbPersonalExperience.class;
	}
}