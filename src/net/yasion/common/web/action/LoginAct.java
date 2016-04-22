package net.yasion.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.logger.aop.annotation.Logger;
import net.yasion.common.core.logger.aop.enumeration.LoggerType;
import net.yasion.common.dto.UserDTO;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IUserService;
import net.yasion.common.support.common.processor.FrameworkReturnPageProcessor;
import net.yasion.common.utils.MD5Util;
import net.yasion.common.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginAct extends BaseAction {

	private IUserService userService;

	public IUserService getUserService() {
		return this.userService;
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/index.do")
	public String index(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
		TbUser user = UserUtils.getCurrentUser();
		if (user == null) {// 未登录
			return new FrameworkReturnPageProcessor("login").returnViewName();
		} else {
			return new FrameworkReturnPageProcessor("index").returnViewName();
		}
	}

	@Logger(type = LoggerType.LOGIN)
	@RequestMapping(method = RequestMethod.POST, value = "/login.do")
	public String login(ModelMap model, HttpServletResponse response, HttpServletRequest request, UserDTO dto) {
		TbUser user = this.userService.lFindByUsername(dto.getUsername());
		String errMsg = "";
		if (user == null) {
			errMsg = "用户不存在";
		} else if ("Y".equals(user.getDisable())) {
			errMsg = "用户已经被禁用";
		} else if ("A".equals(user.getDisable())) {
			errMsg = "用户未通过审核";
		} else if (dto.getPassword() != null && MD5Util.MD5(dto.getPassword()).equals(user.getPassword())) {
			UserUtils.setCurrentUser(user);
			return new FrameworkReturnPageProcessor("index").returnViewName();
		} else {
			errMsg = "密码错误 ";
		}
		model.put(CommonConstants.LOGIN_ERR_MSG, errMsg);
		return new FrameworkReturnPageProcessor("login").returnViewName();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login.do")
	public String loginGet(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
		TbUser loginUser = UserUtils.getCurrentUser();
		if (null == loginUser) {
			return new FrameworkReturnPageProcessor("login").returnViewName();
		} else {
			return new FrameworkReturnPageProcessor("index").returnViewName();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/logout.do")
	public String logout(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
		UserUtils.removeCurrentUser();
		return new FrameworkReturnPageProcessor("login").returnViewName();
	}
}