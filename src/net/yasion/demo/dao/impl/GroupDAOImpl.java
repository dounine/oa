package net.yasion.demo.dao.impl;

import net.yasion.common.dao.impl.BaseDAOImpl;
import net.yasion.demo.dao.IGroupDAO;
import net.yasion.demo.model.Group;

import org.springframework.stereotype.Repository;

@Repository("demoGroupDAO")
public class GroupDAOImpl extends BaseDAOImpl<Group, String> implements IGroupDAO {

	@Override
	public Group insert(Group group) {
		// Transaction tx = session.beginTransaction();
		this.save(group);
		// tx.commit();
		// session.flush();
		// session.close();
		return group;
	}

	@Override
	public void delete(Group group) {
		this.deleteByEntity(group);
	}

	@Override
	public Group find(String groupId) {
		return this.get(groupId);
	}

	@Override
	protected Class<Group> getEntityClass() {
		return Group.class;
	}
}