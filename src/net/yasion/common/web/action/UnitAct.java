package net.yasion.common.web.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.yasion.common.annotation.ConverterArrayParam;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.annotation.ModelJson;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dto.RoleDTO;
import net.yasion.common.dto.UnitDTO;
import net.yasion.common.model.TbUnit;
import net.yasion.common.service.IUnitService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;
import net.yasion.common.utils.AfxBeanUtils;
import net.yasion.common.utils.HttpUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UnitAct extends BaseAction {

	private IUnitService unitService;

	public IUnitService getUnitService() {
		return unitService;
	}

	@Autowired
	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/unit/save.do")
	@ResponseBody
	public void save(@ModelJson UnitDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUnit entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbUnit> entityList = unitService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					entity = unitService.lSave(dto);
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

	@RequestMapping(method = RequestMethod.POST, value = "/unit/update.do")
	@ResponseBody
	public void update(@ModelJson UnitDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUnit entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbUnit> entityList = unitService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					entity = unitService.lUpdate(dto);
					out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
					return;
				} else {
					entity = entityList.get(0);
					if (entity.getId().equals(dto.getId())) {
						entity = unitService.lUpdate(dto);// 当前记录自身时候,一般情况
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

	@RequestMapping("/unit/mainFrame.do")
	public String mainFrame() {
		return new CommonReturnPageProcessor("unit/mainFrame").returnViewName();
	}

	@RequestMapping("/unit/leftFrame.do")
	public String leftFrame() {
		return new CommonReturnPageProcessor("unit/leftFrame").returnViewName();
	}

	@RequestMapping("/unit/topFrame.do")
	public String topFrame() {
		return new CommonReturnPageProcessor("unit/topFrame").returnViewName();
	}

	@RequestMapping("/unit/list.do")
	public String list(HttpServletRequest request, @ModelDTOSearch UnitDTO dto, ModelMap model, Integer pageNumber) {
		pageNumber = (pageNumber != null && pageNumber != 0 ? pageNumber : 1);
		if ("root".equals(dto.getParentId())) {
			dto.setParentId(null);
		}
		IResultSet<TbUnit> resultSet = unitService.lFindByDTOOnPermission(dto, pageNumber, CommonConstants.PAGESIZE);
		model.put("resultSet", resultSet);
		this.setToPageContext(dto);
		return new CommonReturnPageProcessor("unit/list").returnViewName();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/unit/delete.do")
	@ResponseBody
	public void delete(@RequestParam(value = "ids[]") String[] ids, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			int count = unitService.lRemoveByIds(ids);
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

	@RequestMapping(method = RequestMethod.POST, value = "/unit/find.do")
	@ResponseBody
	public void find(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUnit entity = unitService.findById(id);
			UnitDTO dto = new UnitDTO();
			AfxBeanUtils.copySamePropertyValue(entity, dto);
			if (null != entity.getParent()) {
				dto.setParentId(entity.getParent().getId());
			}
			dto.setOperatedUnitId((StringUtils.isBlank(entity.getModifiedUnitId()) ? entity.getCreateUnitId() : entity.getModifiedUnitId()));
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(AfxBeanUtils.getComplexFieldNames(UnitDTO.class));
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

	@RequestMapping("/unit/ajaxLoadUnit.do")
	public void ajaxLoadUnit(HttpServletRequest request, HttpServletResponse response, Integer page, @ModelDTOSearch UnitDTO dto) {
		PrintWriter out = null;
		IResultSet<TbUnit> entitySet = this.unitService.lFindByDTOOnAllUnit(dto, page, CommonConstants.PAGESIZE);
		List<TbUnit> entityList = entitySet.getResultList();
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = entityList.size(); i < len; i++) {
			TbUnit entity = entityList.get(i);
			if (null != entity) {
				JSONObject jsonResult = new JSONObject();
				jsonResult.element("id", entity.getId().toString());
				jsonResult.element("text", entity.getName());
				jsonArr.add(i, jsonResult);
			}
		}
		result.element("result", jsonArr);
		result.element("totalPage", entitySet.getTotalPageCount());
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

	@RequestMapping("/unit/ajaxLoadNotSubUnit.do")
	public void ajaxLoadNotSubUnit(HttpServletRequest request, HttpServletResponse response, Integer page, @ModelDTOSearch UnitDTO dto) {
		PrintWriter out = null;
		IResultSet<TbUnit> entitySet = this.unitService.lFindByDTOOnNotSubUnit(dto, page, CommonConstants.PAGESIZE);
		List<TbUnit> entityList = entitySet.getResultList();
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = entityList.size(); i < len; i++) {
			TbUnit entity = entityList.get(i);
			if (null != entity) {
				JSONObject jsonResult = new JSONObject();
				jsonResult.element("id", entity.getId().toString());
				jsonResult.element("text", entity.getName());
				jsonArr.add(i, jsonResult);
			}
		}
		result.element("result", jsonArr);
		result.element("totalPage", entitySet.getTotalPageCount());
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

	@RequestMapping("/unit/searchUnitById.do")
	public void searchUnitById(HttpServletRequest request, HttpServletResponse response, RoleDTO dto, @ConverterArrayParam String[] ids) {
		PrintWriter out = null;
		List<TbUnit> entityList = this.unitService.findByIds(ids);
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = entityList.size(); i < len; i++) {
			TbUnit entity = entityList.get(i);
			if (null != entity) {
				JSONObject jsonResult = new JSONObject();
				jsonResult.element("id", entity.getId().toString());
				jsonResult.element("text", entity.getName());
				jsonArr.add(i, jsonResult);
			}
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

	@RequestMapping("/unit/treeNode.do")
	public void treeNode(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			String parentId = HttpUtils.getQueryParam(request, "id");
			String parentParentId = HttpUtils.getQueryParam(request, "parentId");
			List<TbUnit> unitList = null;
			StringBuilder builder = new StringBuilder();
			UnitDTO dto = new UnitDTO();
			if (!"root".equals(parentId)) {
				dto.setParentId(parentId);
			}
			unitList = this.unitService.lFindSubUnit(dto, null, null).getResultList();
			builder.append("[");
			for (int i = 0, len = unitList.size(); i < len; i++) {
				TbUnit unit = unitList.get(i);
				if (null != unit) {
					builder.append("{");
					builder.append("id:\"" + unit.getId() + "\",name:\"" + unit.getName() + "\",isParent:" + (0 < unit.getSubUnits().size() /* || 0 < unit.getTbUsers().size() */) + ",type:\"Unit\",icon:\"" + HttpUtils.getContextPath(request) + "/static/common/"
							+ CommonConstants.COMMON_STYLE + "/img/unit/unit.png\", parentId:\"" + (StringUtils.isNotBlank(parentParentId) ? parentParentId + "," : "") + parentId + "\"");
					builder.append("}");
					builder.append(i < (len - 1) ? "," : ""/* (0 < userList.size() ? "," : "") */);
				}
			}
			builder.append("]");
			out.write(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("[{id:\"error\",name:\"查询出错\"}]");
		}
	}
}