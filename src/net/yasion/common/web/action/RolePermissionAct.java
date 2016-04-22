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
import net.yasion.common.dto.PermissionDTO;
import net.yasion.common.dto.RoleDTO;
import net.yasion.common.dto.UserDTO;
import net.yasion.common.model.TbPermission;
import net.yasion.common.model.TbRole;
import net.yasion.common.service.IPermissionService;
import net.yasion.common.service.IRolePermissionService;
import net.yasion.common.service.IRoleService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RolePermissionAct extends BaseAction {

	private IRoleService roleService;

	private IPermissionService permissionService;

	private IRolePermissionService rolePermissionService;

	public IRoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IPermissionService getPermissionService() {
		return permissionService;
	}

	@Autowired
	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public IRolePermissionService getRolePermissionService() {
		return rolePermissionService;
	}

	@Autowired
	public void setRolePermissionService(IRolePermissionService rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
	}

	@RequestMapping("/rolePermission/edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new CommonReturnPageProcessor("rolePermission/edit").returnViewName();
	}

	@RequestMapping("/rolePermission/ajaxLoadPermission.do")
	public void ajaxLoadPermission(HttpServletRequest request, HttpServletResponse response, Integer page, @ModelDTOSearch PermissionDTO dto) {
		PrintWriter out = null;
		dto.setCriteriaLogicExpression("Or(name,code)");
		IResultSet<TbPermission> permissionSet = this.permissionService.lFindByDTOOnPermission(dto, page, CommonConstants.PAGESIZE);
		List<TbPermission> permissionList = permissionSet.getResultList();
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = permissionList.size(); i < len; i++) {
			TbPermission permission = permissionList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", permission.getId().toString());
			jsonResult.element("text", permission.getName() + "[" + permission.getCode() + "]");
			jsonArr.add(i, jsonResult);
		}
		result.element("result", jsonArr);
		result.element("totalPage", permissionSet.getTotalPageCount());
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

	@RequestMapping("/rolePermission/ajaxLoadRole.do")
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

	@RequestMapping("/rolePermission/searchPermissionById.do")
	public void searchPermissionById(HttpServletRequest request, HttpServletResponse response, RoleDTO dto, @ConverterArrayParam String[] ids) {
		PrintWriter out = null;
		List<TbPermission> permissionList = this.permissionService.findByIds(ids);
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = permissionList.size(); i < len; i++) {
			TbPermission permission = permissionList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", permission.getId().toString());
			jsonResult.element("text", permission.getName() + "[" + permission.getCode() + "]");
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

	@RequestMapping("/rolePermission/searchRoleById.do")
	public void searchRoleById(HttpServletRequest request, HttpServletResponse response, UserDTO dto, @ConverterArrayParam String[] ids) {
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

	@RequestMapping(value = "/rolePermission/findPermissionsId.do")
	public void findPermissionsId(HttpServletRequest request, HttpServletResponse response, String roleId) {
		PrintWriter out = null;
		TbRole role = new TbRole();
		role.setId(roleId);
		List<TbPermission> permissionList = this.rolePermissionService.findPermissionByRole(role);
		JSONArray jsonArr = new JSONArray();
		for (int i = 0; i < permissionList.size(); i++) {
			TbPermission permission = permissionList.get(i);
			jsonArr.add(i, permission.getId().toString());
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

	@RequestMapping(value = "/rolePermission/save.do")
	public void save(HttpServletRequest request, HttpServletResponse response, String roleId, @ConverterArrayParam String[] permissionIds) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbRole role = new TbRole();
			role.setId(roleId);
			this.rolePermissionService.deleteByRole(role);
			this.rolePermissionService.saveByRole(role, permissionIds);
			out.write("{\"result\":true,\"msg\":\"save success\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}
}