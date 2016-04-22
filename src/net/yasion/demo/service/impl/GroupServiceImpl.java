package net.yasion.demo.service.impl;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dto.BaseDTO;
import net.yasion.common.service.impl.BaseServiceImpl;
import net.yasion.common.support.common.dao.callback.ParameterSetterCallback;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.demo.dao.IGroupDAO;
import net.yasion.demo.dto.TestDTO;
import net.yasion.demo.dto.TestDTO2;
import net.yasion.demo.model.Group;
import net.yasion.demo.service.IGroupService;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("demoGroupService")
@Transactional
public class GroupServiceImpl extends BaseServiceImpl<Group, BaseDTO<String>, String> implements IGroupService {

	@Autowired
	private IGroupDAO demoGroupDAO;

	public IGroupDAO getDemoGroupDAO() {
		return demoGroupDAO;
	}

	@Autowired
	public void setDemoGroupDAO(IGroupDAO demoGroupDAO) {
		this.demoGroupDAO = demoGroupDAO;
	}

	@Override
	@Transactional(readOnly = false)
	public Group insert(Group group) {
		return demoGroupDAO.insert(group);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Group group) {
		demoGroupDAO.delete(group);
	}

	@Override
	@Transactional(readOnly = true)
	public Group find(String groupId) {
		return demoGroupDAO.find(groupId);
	}

	@Override
	@Transactional(readOnly = true)
	public IResultSet<TestDTO> findOfDTO(String groupId) {
		IResultSet<TestDTO> listByCriteria2 = demoGroupDAO.listByCriteria(null, null, null, TestDTO.class);
		// IResultSet<Group> listByCriteria3 = demoGroupDAO.listByCriteria(null, null);
		// IResultSet<TestDTO> listByCriteria = demoGroupDAO.listBySQL("select * from t_group", TestDTO.class, new ParameterSetterCallback() {
		// @Override
		// public void onSetter(Query query) {
		// }
		// });
		IResultSet<TestDTO2> listByCriteria4 = demoGroupDAO.listByHQL("from " + Group.class.getName(), TestDTO2.class, new ParameterSetterCallback() {
			@Override
			public void onSetter(Query query) {
			}
		});
		IResultSet<TestDTO2> listByCriteria3 = demoGroupDAO.listBySQL("select * from t_group", TestDTO2.class, new ParameterSetterCallback() {
			@Override
			public void onSetter(Query query) {
			}
		});
		System.out.println(listByCriteria4);
		System.out.println(listByCriteria3);
		System.out.println(listByCriteria2);
		// System.out.println(listByCriteria3);
		// System.out.println(listByCriteria2);
		// System.out.println(listByCriteria);
		return listByCriteria2;
	}

	@Override
	protected IBaseDAO<Group, String> getDefaultDAO() {
		return this.demoGroupDAO;
	}
}