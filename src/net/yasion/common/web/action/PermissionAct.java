package net.yasion.common.web.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.annotation.ModelJson;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dto.PermissionDTO;
import net.yasion.common.model.TbPermission;
import net.yasion.common.service.IPermissionService;
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
public class PermissionAct extends BaseAction {

	private IPermissionService permissionService;

	public IPermissionService getPermissionService() {
		return permissionService;
	}

	@Autowired
	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/permission/save.do")
	@ResponseBody
	public void save(@ModelJson PermissionDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbPermission entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbPermission> entityList = permissionService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					entity = permissionService.lSave(dto);
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

	@RequestMapping(method = RequestMethod.POST, value = "/permission/update.do")
	@ResponseBody
	public void update(@ModelJson PermissionDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbPermission entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbPermission> entityList = permissionService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					entity = permissionService.lUpdate(dto);
					out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
					return;
				} else {
					entity = entityList.get(0);
					if (entity.getId().equals(dto.getId())) {
						entity = permissionService.lUpdate(dto);// 当前记录自身时候,一般情况
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

	@RequestMapping("/permission/list.do")
	public String list(HttpServletRequest request, @ModelDTOSearch PermissionDTO dto, ModelMap model, Integer pageNumber) {
		pageNumber = (pageNumber != null && pageNumber != 0 ? pageNumber : 1);
		IResultSet<TbPermission> resultSet = permissionService.lFindByDTOOnPermission(dto, pageNumber, CommonConstants.PAGESIZE);
		model.put("resultSet", resultSet);
		this.setToPageContext(dto);
		return new CommonReturnPageProcessor("permission/list").returnViewName();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/permission/delete.do")
	@ResponseBody
	public void delete(@RequestParam(value = "ids[]") String[] ids, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			int count = permissionService.lRemoveByIds(ids);
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

	@RequestMapping(method = RequestMethod.POST, value = "/permission/find.do")
	@ResponseBody
	public void find(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbPermission entity = permissionService.findById(id);
			PermissionDTO dto = new PermissionDTO();
			AfxBeanUtils.copySamePropertyValue(entity, dto);
			if (StringUtils.isBlank(dto.getWhiteUrls())) {
				dto.setWhiteUrls(CommonConstants.PERMISSION_DEF_WHITE_URLS);
			}
			if (StringUtils.isBlank(dto.getBlackUrls())) {
				dto.setBlackUrls(CommonConstants.PERMISSION_DEF_BLACK_URLS);
			}
			dto.setOperatedUnitId((StringUtils.isBlank(entity.getModifiedUnitId()) ? entity.getCreateUnitId() : entity.getModifiedUnitId()));
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(AfxBeanUtils.getComplexFieldNames(PermissionDTO.class));
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

	@RequestMapping("/permission/ajaxLoadUrls.do")
	public void ajaxLoadUrls(HttpServletRequest request, HttpServletResponse response, Integer page, String key) {
		PrintWriter out = null;
		Entry<List<Object>, Integer> entry = this.permissionService.findUrls(page, key, CommonConstants.PAGESIZE);
		List<Object> urlsList = entry.getKey();
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = urlsList.size(); i < len; i++) {
			Object urls = urlsList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", urls.toString());
			jsonResult.element("text", urls.toString());
			jsonArr.add(i, jsonResult);
		}
		result.element("result", jsonArr);
		result.element("totalPage", entry.getValue());
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
}