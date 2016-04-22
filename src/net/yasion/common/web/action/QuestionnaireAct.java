package net.yasion.common.web.action;

import java.io.PrintWriter;
import java.util.Iterator;
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
import net.yasion.common.dto.QuestionnaireDTO;
import net.yasion.common.dto.QuestionnaireQuestionDTO;
import net.yasion.common.model.TbQuestionnaire;
import net.yasion.common.model.TbQuestionnaireQuestion;
import net.yasion.common.model.TbQuestionnaireQuestionRelation;
import net.yasion.common.service.IQuestionnaireQuestionService;
import net.yasion.common.service.IQuestionnaireService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;
import net.yasion.common.utils.AfxBeanUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionnaireAct extends BaseAction {

	private IQuestionnaireService questionnaireService;

	private IQuestionnaireQuestionService questionnaireQuestionService;

	public IQuestionnaireService getQuestionnaireService() {
		return questionnaireService;
	}

	@Autowired
	public void setQuestionnaireService(IQuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}

	public IQuestionnaireQuestionService getQuestionnaireQuestionService() {
		return questionnaireQuestionService;
	}

	@Autowired
	public void setQuestionnaireQuestionService(IQuestionnaireQuestionService questionnaireQuestionService) {
		this.questionnaireQuestionService = questionnaireQuestionService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaire/save.do")
	@ResponseBody
	public void save(@ModelJson QuestionnaireDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbQuestionnaire entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				if (ArrayUtils.isNotEmpty(dto.getQuestionnaireQuestionIds())) {
					List<TbQuestionnaire> entityList = questionnaireService.findByCodes(new String[] { dto.getCode() });
					if (null == entityList || 0 == entityList.size()) {
						entity = questionnaireService.lSave(dto);
						out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
						return;
					} else {
						out.write("{\"result\":false,\"msg\":\"Message:编码重复,请重新填写!\"}");
					}
				} else {
					out.write("{\"result\":false,\"msg\":\"Message:必须选择问题!\"}");
				}
			} else {
				out.write("{\"result\":false,\"msg\":\"Message:编码必须填写!\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaire/update.do")
	@ResponseBody
	public void update(@ModelJson QuestionnaireDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbQuestionnaire entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbQuestionnaire> entityList = questionnaireService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					entity = questionnaireService.lUpdate(dto);
					out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
					return;
				} else {
					entity = entityList.get(0);
					if (entity.getId().equals(dto.getId())) {
						entity = questionnaireService.lUpdate(dto);// 当前记录自身时候,一般情况
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

	@RequestMapping("/questionnaire/list.do")
	public String list(HttpServletRequest request, @ModelDTOSearch QuestionnaireDTO dto, ModelMap model, Integer pageNumber) {
		pageNumber = (pageNumber != null && pageNumber != 0 ? pageNumber : 1);
		IResultSet<TbQuestionnaire> resultSet = questionnaireService.lFindByDTOOnPermission(dto, pageNumber, CommonConstants.PAGESIZE);
		model.put("resultSet", resultSet);
		this.setToPageContext(dto);
		return new CommonReturnPageProcessor("questionnaire/list").returnViewName();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaire/delete.do")
	@ResponseBody
	public void delete(@RequestParam(value = "ids[]") String[] ids, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			int count = questionnaireService.lRemoveByIds(ids);
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

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaire/find.do")
	@ResponseBody
	public void find(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbQuestionnaire entity = questionnaireService.findById(id);
			QuestionnaireDTO dto = new QuestionnaireDTO();
			AfxBeanUtils.copySamePropertyValue(entity, dto);
			String[] relationIds = new String[entity.getTbQuestionnaireQuestionRelations().size()];
			Iterator<TbQuestionnaireQuestionRelation> it = entity.getTbQuestionnaireQuestionRelations().iterator();
			for (int i = 0; i < relationIds.length; i++) {
				relationIds[i] = it.next().getTbQuestionnaireQuestion().getId();
			}
			dto.setQuestionnaireQuestionIds(relationIds);
			dto.setOperatedUnitId((StringUtils.isBlank(entity.getModifiedUnitId()) ? entity.getCreateUnitId() : entity.getModifiedUnitId()));
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(AfxBeanUtils.getComplexFieldNames(QuestionnaireDTO.class));
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

	@RequestMapping("/questionnaire/ajaxLoadQuestionnaireQuestion.do")
	public void ajaxLoadQuestionnaireQuestion(HttpServletRequest request, HttpServletResponse response, Integer page, @ModelDTOSearch QuestionnaireQuestionDTO dto) {
		PrintWriter out = null;
		dto.setCriteriaLogicExpression("Or(name,code)");
		IResultSet<TbQuestionnaireQuestion> questionSet = this.questionnaireQuestionService.lFindByDTOOnPermission(dto, page, CommonConstants.PAGESIZE);
		List<TbQuestionnaireQuestion> questionList = questionSet.getResultList();

		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = questionList.size(); i < len; i++) {
			TbQuestionnaireQuestion question = questionList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", question.getId().toString());
			jsonResult.element("text", question.getName() + "[" + question.getCode() + "]");
			jsonArr.add(i, jsonResult);
		}
		result.element("result", jsonArr);
		result.element("totalPage", questionSet.getTotalPageCount());
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

	@RequestMapping("/questionnaire/searchQuestionnaireQuestionById.do")
	public void searchQuestionnaireQuestionById(HttpServletRequest request, HttpServletResponse response, QuestionnaireQuestionDTO dto, @ConverterArrayParam String[] ids) {
		PrintWriter out = null;
		List<TbQuestionnaireQuestion> questionList = this.questionnaireQuestionService.findByIds(ids);
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = questionList.size(); i < len; i++) {
			TbQuestionnaireQuestion question = questionList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", question.getId().toString());
			jsonResult.element("text", question.getName() + "[" + question.getCode() + "]");
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

	@RequestMapping("/questionnaire/questionnaireQuestionDescr.do")
	public void questionnaireQuestionDescr(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String questionId = request.getParameter("questionId");
		if (StringUtils.isNotBlank(questionId)) {
			TbQuestionnaireQuestion question = this.questionnaireQuestionService.findById(questionId);
			QuestionnaireQuestionDTO dto = new QuestionnaireQuestionDTO();
			AfxBeanUtils.copySamePropertyValue(question, dto);
			JSONObject entity = JSONObject.fromObject(dto);
			try {
				response.setContentType("text/json;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				JSONObject result = new JSONObject();
				result.element("result", true);
				result.element("entity", entity);
				out = response.getWriter();
				out.write(result.toString());
			} catch (Exception e) {
				e.printStackTrace();
				out.write("{\"result\":false,\"msg\":\"获取失败:" + e.toString() + "\"}");
			}
		}
	}
}