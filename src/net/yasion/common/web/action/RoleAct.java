package net.yasion.common.web.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.annotation.ModelJson;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dto.RoleDTO;
import net.yasion.common.model.TbRole;
import net.yasion.common.service.IRoleService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;
import net.yasion.common.utils.AfxBeanUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleAct extends BaseAction {

	private IRoleService roleService;

	public IRoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/role/save.do")
	@ResponseBody
	public void save(@ModelJson RoleDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbRole entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbRole> entityList = roleService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					entity = roleService.lSave(dto);
					out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
					return;
				} else {
					out.write("{\"result\":false,\"msg\":\"Message:编码重复,请重新填写!\"}");
				}
			} else {
				out.write("{\"result\":false,\"msg\":\"Message:编码必须填写!\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/role/update.do")
	@ResponseBody
	public void update(@ModelJson RoleDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbRole entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbRole> entityList = roleService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					entity = roleService.lUpdate(dto);
					out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
					return;
				} else {
					entity = entityList.get(0);
					if (entity.getId().equals(dto.getId())) {
						entity = roleService.lUpdate(dto);// 当前记录自身时候,一般情况
						out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
						return;
					} else {
						out.write("{\"result\":false,\"msg\":\"Message:编码重复,请重新填写!\"}");
					}
				}
			} else {
				out.write("{\"result\":false,\"msg\":\"Message:编码必须填写!\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping("/role/list.do")
	public String list(HttpServletRequest request, @ModelDTOSearch RoleDTO dto, ModelMap model, Integer pageNumber) {
		pageNumber = (pageNumber != null && pageNumber != 0 ? pageNumber : 1);
		IResultSet<TbRole> resultSet = roleService.lFindByDTOOnPermission(dto, pageNumber, CommonConstants.PAGESIZE);
		model.put("resultSet", resultSet);
		this.setToPageContext(dto);
		return new CommonReturnPageProcessor("role/list").returnViewName();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/role/delete.do")
	@ResponseBody
	public void delete(@RequestParam(value = "ids[]") String[] ids, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			int count = roleService.lRemoveByIds(ids);
			if (0 < count) {
				out.write("{\"result\":true,\"msg\":\"delete success\",\"count\":\"" + count + "\"}");
			} else {
				out.write("{\"result\":false,\"msg\":\"delete result : 0\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/role/find.do")
	@ResponseBody
	public void find(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbRole entity = roleService.findById(id);
			RoleDTO dto = new RoleDTO();
			AfxBeanUtils.copySamePropertyValue(entity, dto);
			dto.setOperatedUnitId((StringUtils.isBlank(entity.getModifiedUnitId()) ? entity.getCreateUnitId() : entity.getModifiedUnitId()));
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(AfxBeanUtils.getComplexFieldNames(RoleDTO.class));
			JSONObject jsonModel = JSONObject.fromObject(dto, jsonConfig);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("result", true);
			jsonResult.element("entity", jsonModel);
			out.write(jsonResult.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}
}