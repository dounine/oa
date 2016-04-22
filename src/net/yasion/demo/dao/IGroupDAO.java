package net.yasion.demo.dao;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.demo.model.Group;

public interface IGroupDAO extends IBaseDAO<Group, String> {
	public Group insert(Group group);

	public void delete(Group group);

	public Group find(String groupId);
}