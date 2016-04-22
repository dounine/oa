package net.yasion.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.core.uurp.UurpAdaptor;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.ISuperUserDAO;
import net.yasion.common.dao.IUnitDAO;
import net.yasion.common.dao.IUserDAO;
import net.yasion.common.dao.IUserRoleDAO;
import net.yasion.common.dto.UserDTO;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IUserService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.utils.MD5Util;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<TbUser, UserDTO, String> implements IUserService {

	private IUserDAO userDAO;

	private ISuperUserDAO superUserDAO;

	private IUserRoleDAO userRoleDAO;

	private IUnitDAO unitDAO;

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	@Autowired
	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ISuperUserDAO getSuperUserDAO() {
		return superUserDAO;
	}

	@Autowired
	public void setSuperUserDAO(ISuperUserDAO superUserDAO) {
		this.superUserDAO = superUserDAO;
	}

	public IUserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	@Autowired
	public void setUserRoleDAO(IUserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public IUnitDAO getUnitDAO() {
		return unitDAO;
	}

	@Autowired
	public void setUnitDAO(IUnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	@Override
	public Integer removeById(String id) {
		TbUser deleteUser = userDAO.get(id);
		if (deleteUser instanceof TbSuperUser) {// 不能通过user删除超级管理员
			return 0;
		} else {
			userRoleDAO.deleteByUserId(id);
			return userDAO.deleteById(id);
		}
	}

	@Override
	public Integer removeByIds(String[] ids) {
		Integer count = 0;
		for (int i = 0, len = ids.length; i < len; i++) {
			count += this.removeById(ids[i]);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = true)
	public TbUser findByUsername(String userName) {
		TbUser user = null;
		if (userName != null) {
			user = userDAO.getUniqueByProperty("username", userName);
		}
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public TbUser lFindByUsername(String userName) {
		TbUser user = null;
		if (userName != null) {
			Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(null);
			user = userDAO.getUniqueByProperty("username", userName, new Criterion[] { logicalDeleteCriterion });
		}
		return user;
	}

	@Override
	public TbUser save(UserDTO userDTO) {
		TbUser entity = new TbUser();
		userDTO.copyValuesTo(entity);
		entity.setPassword(MD5Util.MD5(entity.getPassword()));
		String unitId = userDTO.getUnitId();
		if (StringUtils.isNotBlank(unitId)) {
			TbUnit unit = new TbUnit();
			unit.setId(unitId);
			entity.setTbUnit(unit);
		}
		this.setEntityCreateDefaultValue(entity, userDTO);
		return this.save(entity);
	}

	@Override
	public TbUser update(UserDTO userDTO) {
		TbUser entity = userDAO.get(userDTO.getId());
		String temppw = entity.getPassword();
		userDTO.copyValuesTo(entity);
		if (StringUtils.isNotBlank(userDTO.getPassword())) {
			entity.setPassword(MD5Util.MD5(entity.getPassword()));
		} else {
			entity.setPassword(temppw);
		}
		String unitId = userDTO.getUnitId();
		if (StringUtils.isNotBlank(unitId)) {
			TbUnit unit = new TbUnit();
			unit.setId(unitId);
			entity.setTbUnit(unit);
		} else {
			entity.setTbUnit(null);
		}
		this.setEntityModifiedDefaultValue(entity, userDTO);
		return this.update(entity);
	}

	@Override
	public IResultSet<TbUser> findByDTO(UserDTO dto, Integer pageNumber, Integer pageSize, List<Criterion> additionCriterionList) {
		additionCriterionList = (null != additionCriterionList) ? additionCriterionList : new ArrayList<Criterion>();
		additionCriterionList.add(Restrictions.or(Restrictions.not(Restrictions.eq((StringUtils.isBlank(dto.getCriterionAlias()) ? "flag" : dto.getCriterionAlias() + ".flag"), "SYS")),
				Restrictions.isNull((StringUtils.isBlank(dto.getCriterionAlias()) ? "flag" : dto.getCriterionAlias() + ".flag"))));
		if (StringUtils.isNotBlank(dto.getUnitId())) {
			UurpAdaptor uurpAdaptor = SpringBeanManager.getUurpAdaptor();
			List<TbUnit> subUnit = uurpAdaptor.queryUnitTree(dto.getUnitId());
			TbUnit nowUnit = this.unitDAO.get(dto.getUnitId());
			subUnit = (null != subUnit) ? subUnit : new ArrayList<TbUnit>();
			subUnit.add(nowUnit);
			if (1 == subUnit.size()) {
				additionCriterionList.add(Restrictions.eq("tbUnit.id", subUnit.get(0).getId()));
			} else if (2 == subUnit.size()) {
				additionCriterionList.add(Restrictions.or(Restrictions.eq("tbUnit.id", subUnit.get(0).getId()), Restrictions.eq("tbUnit.id", subUnit.get(1).getId())));
			} else {
				Criterion or = Restrictions.eq("tbUnit.id", subUnit.get(0).getId());
				for (int i = 1, len = subUnit.size(); i < len; i++) {
					Criterion or2 = Restrictions.eq("tbUnit.id", subUnit.get(i).getId());
					or = Restrictions.or(or, or2);
				}
				additionCriterionList.add(or);
			}
			return super.findByDTO(dto, pageNumber, pageSize, additionCriterionList);
		} else {
			return super.findByDTO(dto, pageNumber, pageSize, additionCriterionList);
		}
	}

	@Override
	public IResultSet<TbUser> lFindByDTOOnAllUser(UserDTO dto, Integer pageNumber, Integer pageSize) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion logicalDeleteCriterion = getLogicalDeleteCriterion(dto);
		if (null != logicalDeleteCriterion) {
			criterionList.add(logicalDeleteCriterion);
		}
		criterionList.add(Restrictions.or(Restrictions.not(Restrictions.eq((StringUtils.isBlank(dto.getCriterionAlias()) ? "flag" : dto.getCriterionAlias() + ".flag"), "SYS")),
				Restrictions.isNull((StringUtils.isBlank(dto.getCriterionAlias()) ? "flag" : dto.getCriterionAlias() + ".flag"))));
		return super.findByDTO(dto, pageNumber, pageSize, criterionList);
	}

	@Override
	protected IBaseDAO<TbUser, String> getDefaultDAO() {
		return this.userDAO;
	}
}