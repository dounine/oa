package net.yasion.common.dao.impl;

import net.yasion.common.dao.ILogDAO;
import net.yasion.common.model.TbLog;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository("logDAO")
public class LogDAOImpl extends BaseDAOImpl<TbLog, String> implements ILogDAO {

	@Override
	protected Class<TbLog> getEntityClass() {
		return TbLog.class;
	}

	@Override
	protected Order[] getOrders() {
		return new Order[] { Order.desc("logTime") };
	}
}