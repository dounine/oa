package net.yasion.common.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.annotation.ModelJson;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dto.QuestionnaireOptionDTO;
import net.yasion.common.dto.QuestionnaireQuestionDTO;
import net.yasion.common.model.TbQuestionnaireOption;
import net.yasion.common.model.TbQuestionnaireQuestion;
import net.yasion.common.service.IQuestionnaireQuestionService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;
import net.yasion.common.utils.AfxBeanUtils;

import org.apache.commons.collections.CollectionUtils;
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
public class QuestionnaireQuestionAct extends BaseAction {

	private IQuestionnaireQuestionService questionnaireQuestionService;

	public IQuestionnaireQuestionService getQuestionnaireQuestionService() {
		return questionnaireQuestionService;
	}

	@Autowired
	public void setQuestionnaireQuestionService(IQuestionnaireQuestionService questionnaireQuestionService) {
		this.questionnaireQuestionService = questionnaireQuestionService;
	}

	@RequestMapping("/questionnaireQuestion/list.do")
	public String list(HttpServletRequest request, @ModelDTOSearch QuestionnaireQuestionDTO dto, ModelMap model, Integer pageNumber) {
		pageNumber = (pageNumber != null && pageNumber != 0 ? pageNumber : 1);
		IResultSet<TbQuestionnaireQuestion> resultSet = questionnaireQuestionService.lFindByDTOOnPermission(dto, pageNumber, CommonConstants.PAGESIZE);
		model.put("resultSet", resultSet);
		this.setToPageContext(dto);
		return new CommonReturnPageProcessor("questionnaireQuestion/list").returnViewName();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaireQuestion/save.do")
	@ResponseBody
	public void save(@ModelJson QuestionnaireQuestionDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbQuestionnaireQuestion entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbQuestionnaireQuestion> entityList = questionnaireQuestionService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					if ("C".equals(dto.getType()) || ArrayUtils.isNotEmpty(dto.getOptionDTOs())) {
						entity = questionnaireQuestionService.lSave(dto);
						out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
					} else {
						out.write("{\"result\":false,\"msg\":\"Message:必须添加至少一个选项!\"}");
					}
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

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaireQuestion/delete.do")
	@ResponseBody
	public void delete(@RequestParam(value = "ids[]") String[] ids, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			int count = questionnaireQuestionService.lRemoveByIds(ids);

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

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaireQuestion/update.do")
	@ResponseBody
	public void update(@ModelJson QuestionnaireQuestionDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbQuestionnaireQuestion entity = null;
			if (StringUtils.isNotBlank(dto.getCode())) {
				List<TbQuestionnaireQuestion> entityList = questionnaireQuestionService.findByCodes(new String[] { dto.getCode() });
				if (null == entityList || 0 == entityList.size()) {
					if ("C".equals(dto.getType()) || ArrayUtils.isNotEmpty(dto.getOptionDTOs())) {
						entity = questionnaireQuestionService.lUpdate(dto);
						out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
					} else {
						out.write("{\"result\":false,\"msg\":\"Message:必须添加至少一个选项!\"}");
					}
					return;
				} else {
					entity = entityList.get(0);
					if (entity.getId().equals(dto.getId())) {
						if ("C".equals(dto.getType()) || ArrayUtils.isNotEmpty(dto.getOptionDTOs())) {
							entity = questionnaireQuestionService.lUpdate(dto);// 当前记录自身时候,一般情况
							out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
						} else {
							out.write("{\"result\":false,\"msg\":\"Message:必须添加至少一个选项!\"}");
						}
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

	@RequestMapping(method = RequestMethod.POST, value = "/questionnaireQuestion/find.do")
	@ResponseBody
	public void find(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbQuestionnaireQuestion entity = questionnaireQuestionService.findById(id);
			QuestionnaireQuestionDTO dto = new QuestionnaireQuestionDTO();
			entity.copyValuesTo(dto);
			Set<TbQuestionnaireOption> tbQuestionnaireOptions = entity.getTbQuestionnaireOptions();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(AfxBeanUtils.getComplexFieldNames(TbQuestionnaireOption.class));
			if (CollectionUtils.isNotEmpty(tbQuestionnaireOptions)) {
				Iterator<TbQuestionnaireOption> tbQuestionnaireOptionIt = tbQuestionnaireOptions.iterator();
				List<QuestionnaireOptionDTO> questionnaireOptionDTOList = new ArrayList<QuestionnaireOptionDTO>();
				while (tbQuestionnaireOptionIt.hasNext()) {
					QuestionnaireOptionDTO optionDTO = new QuestionnaireOptionDTO();
					TbQuestionnaireOption tbQuestionnaireOption = tbQuestionnaireOptionIt.next();
					tbQuestionnaireOption.copyValuesTo(optionDTO);
					questionnaireOptionDTOList.add(optionDTO);
				}
				dto.setOptionDTOs(questionnaireOptionDTOList.toArray(new QuestionnaireOptionDTO[0]));
			}
			dto.setOperatedUnitId((StringUtils.isBlank(entity.getModifiedUnitId()) ? entity.getCreateUnitId() : entity.getModifiedUnitId()));
			JSONObject jsonModel = JSONObject.fromObject(dto);
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