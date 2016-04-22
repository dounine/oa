package net.yasion.common.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.annotation.ModelJson;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dto.PersonalExperienceDTO;
import net.yasion.common.dto.RoleDTO;
import net.yasion.common.model.TbPersonalExperience;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IPersonalExperienceService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;
import net.yasion.common.utils.AfxBeanUtils;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonalExperienceAct extends BaseAction {

	private IPersonalExperienceService personalExperienceService;

	public IPersonalExperienceService getPersonalExperienceService() {
		return personalExperienceService;
	}

	@Autowired
	public void setPersonalExperienceService(IPersonalExperienceService personalExperienceService) {
		this.personalExperienceService = personalExperienceService;
	}

	@RequestMapping("/personalExperience/detail.do")
	public String detail(HttpServletRequest request, @ModelDTOSearch PersonalExperienceDTO dto, ModelMap model, Integer pageNumber) {
		pageNumber = (pageNumber != null && pageNumber != 0 ? pageNumber : 1);
		TbUser user = UserUtils.getCurrentUser();
		dto.setUserId(user.getId());
		IResultSet<TbPersonalExperience> resultSet = personalExperienceService.lFindByDTO(dto, pageNumber, CommonConstants.PAGESIZE);
		model.put("resultSet", resultSet);
		this.setToPageContext(dto);
		return new CommonReturnPageProcessor("personalExperience/detail").returnViewName();
	}

	@RequestMapping("/personalExperience/delete.do")
	public void delete(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			int count = personalExperienceService.lRemoveById(id);
			if (0 < count) {
				response.sendRedirect(CommonConstants.CONTEXT_PATH + "/personalExperience/detail.do");
			} else {
				out.write("{\"result\":false,\"msg\":\"delete result : 0\"}");
			}
			UserUtils.refreshCurrentUser();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/personalExperience/save.do")
	@ResponseBody
	public void save(@ModelJson PersonalExperienceDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbPersonalExperience entity = null;
			if (StringUtils.isNotBlank(dto.getDescr())) {
				entity = personalExperienceService.lSave(dto);
				out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
				return;
			} else {
				out.write("{\"result\":false,\"msg\":\"Message:经历必须填写!\"}");
			}
			UserUtils.refreshCurrentUser();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/personalExperience/find.do")
	@ResponseBody
	public void find(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbPersonalExperience entity = personalExperienceService.findById(id);
			PersonalExperienceDTO dto = new PersonalExperienceDTO();
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

	@RequestMapping(method = RequestMethod.POST, value = "/personalExperience/update.do")
	@ResponseBody
	public void update(@ModelJson PersonalExperienceDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbPersonalExperience entity = null;
			if (StringUtils.isNotBlank(dto.getDescr())) {
				entity = personalExperienceService.lUpdate(dto);
				out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
				return;
			} else {
				out.write("{\"result\":false,\"msg\":\"Message:经历必须填写!\"}");
			}
			UserUtils.refreshCurrentUser();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}
}