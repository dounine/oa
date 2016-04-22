package net.yasion.common.service.impl;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IPermissionDAO;
import net.yasion.common.dao.IRolePermissionDAO;
import net.yasion.common.dto.PermissionDTO;
import net.yasion.common.model.TbPermission;
import net.yasion.common.service.IPermissionService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("permissionService")
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<TbPermission, PermissionDTO, String> implements IPermissionService {

	private IPermissionDAO permissionDAO;

	private IRolePermissionDAO rolePermissionDAO;

	public IPermissionDAO getPermissionDAO() {
		return permissionDAO;
	}

	@Autowired
	public void setPermissionDAO(IPermissionDAO permissionDAO) {
		this.permissionDAO = permissionDAO;
	}

	public IRolePermissionDAO getRolePermissionDAO() {
		return rolePermissionDAO;
	}

	@Autowired
	public void setRolePermissionDAO(IRolePermissionDAO rolePermissionDAO) {
		this.rolePermissionDAO = rolePermissionDAO;
	}

	@Override
	public Integer removeById(String id) {
		rolePermissionDAO.deleteByPermissionId(id);
		return permissionDAO.deleteById(id);
	}

	@Override
	public Integer removeByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			rolePermissionDAO.deleteByPermissionId(ids[i]);
		}
		return permissionDAO.deleteByIds(ids);
	}

	@Override
	public List<TbPermission> findByCodes(String[] codes) {
		Criterion criterion = null;
		for (int i = 0, len = codes.length; i < len; i++) {
			Criterion thisCriterion = Restrictions.eq("code", codes[i]);
			if (null != criterion) {
				criterion = Restrictions.or(criterion, thisCriterion);
			} else {
				criterion = thisCriterion;
			}
		}
		IResultSet<TbPermission> entityList = permissionDAO.listByCriteria(new Criterion[] { criterion }, new Order[] { Order.desc("id") }, null);
		return entityList.getResultList();
	}

	@Override
	public Entry<List<Object>, Integer> findUrls(Integer pageIndex, String key, Integer pageSize) {
		pageIndex = (0 == pageIndex ? 1 : pageIndex);
		pageSize = (0 == pageSize ? 1 : pageSize);
		Map<?, ?> requestMappingMap = (Map<?, ?>) HttpInternalObjectManager.getServletContext().getAttribute(CommonConstants.GLOBAL_REQUEST_MAPPING_URL);
		ArrayList<Object> keyList = new ArrayList<Object>(requestMappingMap.keySet());
		if (StringUtils.isNotBlank(key)) {
			ArrayList<Object> removeKeyList = new ArrayList<Object>();
			for (int i = 0, len = keyList.size(); i < len; i++) {
				Object object = keyList.get(i);
				if (!object.toString().contains(key)) {
					removeKeyList.add(object);
				}
			}
			keyList.removeAll(removeKeyList);
		}
		int startPos = pageSize * (pageIndex - 1);
		int endPos = startPos + pageSize;
		endPos = (endPos > keyList.size() ? keyList.size() : endPos);
		List<Object> subList = keyList.subList(startPos, endPos);
		int litlePageCount = (keyList.size() % pageSize);
		int mainPageCount = (keyList.size() / pageSize);
		int totalCount = (litlePageCount == 0 ? mainPageCount : mainPageCount + 1);
		Entry<List<Object>, Integer> entry = new SimpleEntry<List<Object>, Integer>(subList, totalCount);
		return entry;
	}

	@Override
	protected IBaseDAO<TbPermission, String> getDefaultDAO() {
		return this.permissionDAO;
	}
}