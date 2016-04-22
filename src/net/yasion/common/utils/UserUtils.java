package net.yasion.common.utils;

import javax.servlet.http.HttpSession;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IUserService;
import net.yasion.common.service.impl.UserServiceImpl;
import net.yasion.common.web.tag.PermissionELFunction;

import org.apache.commons.lang3.StringUtils;

public class UserUtils {

	public static TbUser getSessionUser(HttpSession session) {
		TbUser user = null;
		if (null != session) {
			try {
				user = (TbUser) session.getAttribute(CommonConstants.LOGIN_USER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public static void setSessionUser(HttpSession session, TbUser user) {
		if (null != session) {
			try {
				session.setAttribute(CommonConstants.LOGIN_USER, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void removeSessionUser(HttpSession session) {
		if (null != session) {
			try {
				session.removeAttribute(CommonConstants.LOGIN_USER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void refreshSessiontUser(HttpSession session) {
		if (null != session) {
			TbUser user = getSessionUser(session);
			if (null != user) {
				IUserService userService = SpringBeanManager.getBean(IUserService.class, UserServiceImpl.class);
				if (null != userService) {
					HibernateUtils.clearCache();
					user = userService.findById(user.getId());
					setSessionUser(session, user);
				}
			}
		}
	}

	public static TbUser getCurrentUser() {
		HttpSession session = HttpInternalObjectManager.getCurrentSession();
		return getSessionUser(session);
	}

	public static void setCurrentUser(TbUser user) {
		HttpSession session = HttpInternalObjectManager.getCurrentSession();
		setSessionUser(session, user);
	}

	public static void removeCurrentUser() {
		HttpSession session = HttpInternalObjectManager.getCurrentSession();
		removeSessionUser(session);
	}

	public static void refreshCurrentUser() {
		HttpSession session = HttpInternalObjectManager.getCurrentSession();
		refreshSessiontUser(session);
	}

	public static TbUnit getCurrentUnit() {
		TbUser currentUser = getCurrentUser();
		if (null != currentUser) {
			if (currentUser instanceof TbSuperUser) {
				TbUnit unit = new TbUnit();
				unit.setId(CommonConstants.ADMIN_UNIT_ID);
				unit.setName(CommonConstants.ADMIN_UNIT_ID);
				unit.setCode(CommonConstants.ADMIN_UNIT_ID);
				return unit;
			} else {
				return currentUser.getTbUnit();
			}
		}
		return null;
	}

	public static Boolean isRealSuperUser(TbUser user) {
		return (user instanceof TbSuperUser);
	}

	public static Boolean isCurrentRealSuperUser() {
		TbUser currentUser = getCurrentUser();
		return isRealSuperUser(currentUser);
	}

	public static Boolean isSuperUser(TbUser user) {
		return (isRealSuperUser(user) || PermissionELFunction.isRoles(user, StringUtils.split(CommonConstants.ADMIN_ROLE_CODE, ",")));
	}

	public static Boolean isCurrentSuperUser() {
		TbUser currentUser = getCurrentUser();
		return isSuperUser(currentUser);
	}
}