package net.yasion.demo.service;

import net.yasion.common.dto.BaseDTO;
import net.yasion.common.service.IBaseService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.demo.dto.TestDTO;
import net.yasion.demo.model.Group;

public interface IGroupService extends IBaseService<Group, BaseDTO<String>, String> {

	public Group insert(Group group);

	public void delete(Group group);

	public Group find(String groupId);

	public IResultSet<TestDTO> findOfDTO(String groupId);
}