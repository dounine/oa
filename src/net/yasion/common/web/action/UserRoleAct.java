package net.yasion.common.web.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.yasion.common.annotation.ConverterArrayParam;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dto.RoleDTO;
import net.yasion.common.dto.UserDTO;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IRoleService;
import net.yasion.common.service.IUserRoleService;
import net.yasion.common.service.IUserService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserRoleAct extends BaseAction {

	private IUserService userService;

	private IRoleService roleService;

	private IUserRoleService userRoleService;

	public IUserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IUserRoleService getUserRoleService() {
		return userRoleService;
	}

	@Autowired
	public void setUserRoleService(IUserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	@RequestMapping("/userRole/edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new CommonReturnPageProcessor("userRole/edit").returnViewName();
	}

	@RequestMapping("/userRole/ajaxLoadRole.do")
	public void ajaxLoadRole(HttpServletRequest request, HttpServletResponse response, Integer page, @ModelDTOSearch RoleDTO dto) {
		PrintWriter out = null;
		dto.setCriteriaLogicExpression("Or(name,code)");
		IResultSet<TbRole> roleSet = this.roleService.lFindByDTOOnPermission(dto, page, CommonConstants.PAGESIZE);
		List<TbRole> roleList = roleSet.getResultList();
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = roleList.size(); i < len; i++) {
			TbRole role = roleList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", role.getId().toString());
			jsonResult.element("text", role.getName() + "[" + role.getCode() + "]");
			jsonArr.add(i, jsonResult);
		}
		result.element("result", jsonArr);
		result.element("totalPage", roleSet.getTotalPageCount());
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.write(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":[],\"totalPage\":0}");
		}
	}

	@RequestMapping("/userRole/ajaxLoadUser.do")
	public void ajaxLoadUser(HttpServletRequest request, HttpServletResponse response, Integer page, @ModelDTOSearch UserDTO dto) {
		PrintWriter out = null;
		dto.setCriteriaLogicExpression("Or(name,username)");
		IResultSet<TbUser> userSet = this.userService.lFindByDTOOnAllUser(dto, page, CommonConstants.PAGESIZE);
		List<TbUser> userList = userSet.getResultList();
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = userList.size(); i < len; i++) {
			TbUser user = userList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", user.getId().toString());
			jsonResult.element("text", user.getName() + "[" + user.getUsername() + "]");
			jsonArr.add(i, jsonResult);
		}
		result.element("result", jsonArr);
		result.element("totalPage", userSet.getTotalPageCount());
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.write(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":[],\"totalPage\":0}");
		}
	}

	@RequestMapping("/userRole/searchRoleById.do")
	public void searchRoleById(HttpServletRequest request, HttpServletResponse response, RoleDTO dto, @ConverterArrayParam String[] ids) {
		PrintWriter out = null;
		List<TbRole> roleList = this.roleService.findByIds(ids);
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = roleList.size(); i < len; i++) {
			TbRole role = roleList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", role.getId().toString());
			jsonResult.element("text", role.getName() + "[" + role.getCode() + "]");
			jsonArr.add(i, jsonResult);
		}
		result.element("result", jsonArr);
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.write(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":[]");
		}
	}

	@RequestMapping("/userRole/searchUserById.do")
	public void searchUserById(HttpServletRequest request, HttpServletResponse response, UserDTO dto, @ConverterArrayParam String[] ids) {
		PrintWriter out = null;
		List<TbUser> userList = this.userService.findByIds(ids);
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = userList.size(); i < len; i++) {
			TbUser user = userList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", user.getId().toString());
			jsonResult.element("text", user.getName() + "[" + user.getUsername() + "]");
			jsonArr.add(i, jsonResult);
		}
		result.element("result", jsonArr);
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.write(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":[]");
		}
	}

	@RequestMapping(value = "/userRole/findRolesId.do")
	public void findRolesId(HttpServletRequest request, HttpServletResponse response, String userId) {
		PrintWriter out = null;
		TbUser user = new TbUser();
		user.setId(userId);
		List<TbRole> roleList = this.userRoleService.findRolesByUser(user);
		JSONArray jsonArr = new JSONArray();
		for (int i = 0; i < roleList.size(); i++) {
			TbRole role = roleList.get(i);
			jsonArr.add(i, role.getId().toString());
		}
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.write(jsonArr.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("");
		}
	}

	@RequestMapping(value = "/userRole/save.do")
	public void save(HttpServletRequest request, HttpServletResponse response, String userId, @ConverterArrayParam String[] roleIds) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUser user = new TbUser();
			user.setId(userId);
			this.userRoleService.deleteByUser(user);
			this.userRoleService.saveByUser(user, roleIds);
			out.write("{\"result\":true,\"msg\":\"save success\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}
}