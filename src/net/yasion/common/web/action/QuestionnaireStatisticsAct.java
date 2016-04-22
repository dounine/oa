package net.yasion.common.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.yasion.common.annotation.ConverterArrayParam;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dto.QuestionnaireDTO;
import net.yasion.common.dto.QuestionnaireStatisticsDTO;
import net.yasion.common.model.TbQuestionnaire;
import net.yasion.common.service.IQuestionnaireService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionnaireStatisticsAct extends BaseAction {

	private IQuestionnaireService questionnaireService;

	public IQuestionnaireService getQuestionnaireService() {
		return questionnaireService;
	}

	@Autowired
	public void setQuestionnaireService(IQuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}

	@RequestMapping("/questionnaireStatistics/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return new CommonReturnPageProcessor("questionnaireStatistics/list").returnViewName();
	}

	@RequestMapping("/questionnaireStatistics/ajaxLoadQuestionnaire.do")
	public void ajaxLoadWorksType(HttpServletRequest request, HttpServletResponse response, int page) {
		PrintWriter out = null;
		QuestionnaireDTO questionnaireDTO = new QuestionnaireDTO();
		IResultSet<TbQuestionnaire> entitySet = this.questionnaireService.lFindByDTOOnPermission(questionnaireDTO, page, CommonConstants.PAGESIZE);
		List<TbQuestionnaire> entityList = entitySet.getResultList();
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = entityList.size(); i < len; i++) {
			TbQuestionnaire entity = entityList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", entity.getId().toString());
			jsonResult.element("text", entity.getName());
			jsonArr.add(i, jsonResult);
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

	@RequestMapping("/questionnaireStatistics/searchQuestionnaireById.do")
	public void searchWorksTypeById(HttpServletRequest request, HttpServletResponse response, QuestionnaireDTO dto, @ConverterArrayParam String[] ids) {
		PrintWriter out = null;
		List<TbQuestionnaire> tbQuestionnairesList = this.questionnaireService.findByIds(ids);
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = tbQuestionnairesList.size(); i < len; i++) {
			TbQuestionnaire tbQuestionnaire = tbQuestionnairesList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", tbQuestionnaire.getId().toString());
			jsonResult.element("text", tbQuestionnaire.getName());
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

	@RequestMapping("/questionnaireStatistics/chart.do")
	public String chart(ModelMap modelMap, String questionnaireId, @ModelDTOSearch QuestionnaireDTO dto) {
		if (StringUtils.isNotBlank(questionnaireId)) {
			TbQuestionnaire tbQuestionnaire = questionnaireService.findById(questionnaireId);
			if (null != tbQuestionnaire) {
				String questionnaireName = tbQuestionnaire.getName();
				Map<String, List<QuestionnaireStatisticsDTO>> dataWrapMap = new LinkedHashMap<String, List<QuestionnaireStatisticsDTO>>();
				Map<String, String> resultMap = new LinkedHashMap<String, String>();
				List<QuestionnaireStatisticsDTO> statisticsList = questionnaireService.statisticsByQuestionnaire(questionnaireId);
				for (int i = 0; i < statisticsList.size(); i++) {
					QuestionnaireStatisticsDTO statisticsDTO = statisticsList.get(i);
					String questionId = statisticsDTO.getQuestionnaireQuestionId();
					List<QuestionnaireStatisticsDTO> questionnaireStatisticsDTOList = dataWrapMap.get(questionId);
					if (null == questionnaireStatisticsDTOList) {
						questionnaireStatisticsDTOList = new ArrayList<QuestionnaireStatisticsDTO>();
						questionnaireStatisticsDTOList.add(statisticsDTO);
						dataWrapMap.put(questionId, questionnaireStatisticsDTOList);
					} else {
						questionnaireStatisticsDTOList.add(statisticsDTO);
					}
				}
				Iterator<String> resultKeyIt = dataWrapMap.keySet().iterator();
				while (resultKeyIt.hasNext()) {
					StringBuilder contentBuilder = new StringBuilder(" data-contents='");
					StringBuilder contentPercentageBuilder = new StringBuilder(" data-contentPercentages='");
					StringBuilder ocscBuilder = new StringBuilder(" data-ocscs='");
					StringBuilder nameBuilder = new StringBuilder(" data-names='");
					String key = resultKeyIt.next();
					List<QuestionnaireStatisticsDTO> questionnaireStatisticsDTOList = dataWrapMap.get(key);
					for (int i = 0, len = questionnaireStatisticsDTOList.size(); i < len; i++) {
						QuestionnaireStatisticsDTO questionnaireStatisticsDTO = questionnaireStatisticsDTOList.get(i);
						contentBuilder.append(questionnaireStatisticsDTO.getQuestionnaireOptionContent() + (i < len - 1 ? "," : ""));
						BigDecimal percentagesVal = questionnaireStatisticsDTO.getOcsc().multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
						ocscBuilder.append(percentagesVal + (i < len - 1 ? "," : ""));
						nameBuilder.append(questionnaireStatisticsDTO.getQuestionnaireQuestionName() + (i < len - 1 ? "," : ""));
						contentPercentageBuilder.append(questionnaireStatisticsDTO.getQuestionnaireOptionContent() + "(" + percentagesVal + "%)" + (i < len - 1 ? "," : ""));
					}
					contentBuilder.append("' ");
					ocscBuilder.append("' ");
					nameBuilder.append("' ");
					contentPercentageBuilder.append("' ");
					resultMap.put(key, contentBuilder.toString() + contentPercentageBuilder.toString() + ocscBuilder.toString() + nameBuilder.toString());
				}
				modelMap.put("questionnaireId", questionnaireId);
				modelMap.put("questionnaireName", questionnaireName);
				modelMap.put("resultMap", resultMap);
			}
		}
		return new CommonReturnPageProcessor("questionnaireStatistics/list").returnViewName();
	}
}