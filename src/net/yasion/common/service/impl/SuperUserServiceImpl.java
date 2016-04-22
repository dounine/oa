package net.yasion.common.service.impl;

import java.util.Date;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.ISuperUserDAO;
import net.yasion.common.dao.IUserDAO;
import net.yasion.common.dto.SuperUserDTO;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.ISuperUserService;
import net.yasion.common.utils.MD5Util;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("superUserService")
@Transactional
public class SuperUserServiceImpl extends BaseServiceImpl<TbSuperUser, SuperUserDTO, String> implements ISuperUserService {

	private ISuperUserDAO superUserDAO;

	private IUserDAO userDAO;

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

	@Override
	public TbSuperUser save(SuperUserDTO superUserDTO) {
		TbSuperUser entity = new TbSuperUser();
		superUserDTO.copyValuesTo(entity);
		entity.setPassword(MD5Util.MD5(entity.getPassword()));
		TbUser currentUser = UserUtils.getCurrentUser();
		if (null != currentUser) {
			entity.setCreateUserId(CommonConstants.ADMIN_UNIT_ID);
		}
		entity.setCreateUnitId(CommonConstants.SYSTEM_UNIT_ID);
		entity.setCreateDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
		entity.setDisable("N");
		entity.setFlag("SYS");
		superUserDAO.save(entity);
		return entity;
	}

	@Override
	public TbSuperUser update(SuperUserDTO superUserDTO) {
		TbSuperUser entity = superUserDAO.get(superUserDTO.getId());
		String temppw = entity.getPassword();
		superUserDTO.copyValuesTo(entity);
		if (StringUtils.isNotBlank(superUserDTO.getPassword())) {
			entity.setPassword(MD5Util.MD5(entity.getPassword()));
		} else {
			entity.setPassword(temppw);
		}
		TbUser currentUser = UserUtils.getCurrentUser();
		if (null != currentUser) {
			entity.setModifiedUserId(CommonConstants.ADMIN_UNIT_ID);
		}
		entity.setModifiedUnitId(CommonConstants.SYSTEM_UNIT_ID);
		entity.setModifiedDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
		entity.setDisable("N");
		entity.setFlag("SYS");
		superUserDAO.update(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public TbUser findByUsername(String userName) {
		TbUser user = null;
		if (userName != null) {
			user = userDAO.getUniqueByProperty("username", userName);
			if (null == user) {
				user = superUserDAO.getUniqueByProperty("username", userName);
			}
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
			if (null == user) {
				user = superUserDAO.getUniqueByProperty("username", userName, new Criterion[] { logicalDeleteCriterion });
			}
		}
		return user;
	}

	@Override
	protected IBaseDAO<TbSuperUser, String> getDefaultDAO() {
		return this.superUserDAO;
	}
}