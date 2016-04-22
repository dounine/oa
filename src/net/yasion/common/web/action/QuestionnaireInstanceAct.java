package net.yasion.common.web.action;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yasion.common.annotation.ModelJson;
import net.yasion.common.dto.QuestionnaireInstanceDTO;
import net.yasion.common.model.TbQuestionnaire;
import net.yasion.common.model.TbQuestionnaireInstance;
import net.yasion.common.service.IQuestionnaireInstanceService;
import net.yasion.common.service.IQuestionnaireService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;
import net.yasion.common.utils.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionnaireInstanceAct extends BaseAction {

	private IQuestionnaireInstanceService questionnaireInstanceService;

	private IQuestionnaireService questionnaireService;

	public IQuestionnaireService getQuestionnaireService() {
		return questionnaireService;
	}

	@Autowired
	public void setQuestionnaireService(IQuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}

	public IQuestionnaireInstanceService getQuestionnaireInstanceService() {
		return questionnaireInstanceService;
	}

	@Autowired
	public void setQuestionnaireInstanceService(IQuestionnaireInstanceService questionnaireInstanceService) {
		this.questionnaireInstanceService = questionnaireInstanceService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaireInstance/save.do")
	@ResponseBody
	public void save(@ModelJson QuestionnaireInstanceDTO dto, ModelMap model, HttpServletResponse response, HttpServletRequest request) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbQuestionnaireInstance entity = null;
			String ipAddress = HttpUtils.getIpAddr(request);
			dto.setIpAddress(ipAddress);
			QuestionnaireInstanceDTO searchDTO = new QuestionnaireInstanceDTO();
			searchDTO.setQuestionnaireId(dto.getQuestionnaireId());
			searchDTO.setIpAddress(ipAddress);
			IResultSet<TbQuestionnaireInstance> resultSet = questionnaireInstanceService.lFindByDTO(searchDTO, null, null);
			if (null == resultSet || 0 == resultSet.getTotalResultCount()) {
				Cookie cookie = HttpUtils.getCookie(request, "questionnaire" + dto.getQuestionnaireId());
				if (null == cookie || !cookie.getValue().equals(dto.getQuestionnaireId())) {
					entity = questionnaireInstanceService.lSave(dto);
					HttpUtils.addCookie(request, response, "questionnaire" + dto.getQuestionnaireId(), dto.getQuestionnaireId(), Integer.MAX_VALUE, null);
					out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
					return;
				}
			}
			out.write("{\"result\":false,\"msg\":\"Message:您已提交过问卷，请不要重复提交!\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping("/questionnaireInstance/instance.do")
	public String instance(HttpServletRequest request, HttpServletResponse response, ModelMap model, String id) {
		TbQuestionnaire questionnaire = questionnaireService.findById(id);
		model.put("questionnaire", questionnaire);
		return new CommonReturnPageProcessor("questionnaireInstance/instance").returnViewName();
	}
}