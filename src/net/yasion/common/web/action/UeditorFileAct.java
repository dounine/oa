package net.yasion.common.web.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.dto.UeditorFileDTO;
import net.yasion.common.model.TbUeditorFile;
import net.yasion.common.model.TbUser;
import net.yasion.common.model.TbWebFile;
import net.yasion.common.service.IUeditorFileService;
import net.yasion.common.service.IUserService;
import net.yasion.common.service.IWebFileService;
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
public class UeditorFileAct extends BaseAction {

	private IWebFileService webFileService;

	private IUeditorFileService ueditorFileService;

	private IUserService userService;

	public IWebFileService getWebFileService() {
		return webFileService;
	}

	@Autowired
	public void setWebFileService(IWebFileService webFileService) {
		this.webFileService = webFileService;
	}

	public IUeditorFileService getUeditorFileService() {
		return ueditorFileService;
	}

	@Autowired
	public void setUeditorFileService(IUeditorFileService ueditorFileService) {
		this.ueditorFileService = ueditorFileService;
	}

	public IUserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/ueditorFile/download.do", "/ueditorFile/visit.do" })
	@ResponseBody
	public void access(String id, String showName, HttpServletResponse response) {
		TbUeditorFile uefile = ueditorFileService.findById(id);
		if (null != uefile && !CommonConstants.LOGICAL_DELETE_FLAG.equals(uefile.getFlag())) {
			if (StringUtils.isNotBlank(uefile.getFilePath())) {
				String filename = StringUtils.isBlank(showName) ? uefile.getOriginalName() : showName;
				String filePath = uefile.getFilePath();
				TbWebFile webFile = uefile.getTbWebFile();
				if (null != webFile) {
					filePath = webFile.getFilePath();
				}
				filePath = (filePath.startsWith("/") ? filePath : "/" + filePath);
				String realPath = HttpInternalObjectManager.getServletContext().getRealPath(filePath);
				HttpUtils.download(response, realPath, filename);
			}
		}
	}

	@RequestMapping("/ueditorFile/list.do")
	public String list(HttpServletRequest request, @ModelDTOSearch UeditorFileDTO dto, ModelMap model, Integer pageNumber) {
		pageNumber = (pageNumber != null && pageNumber != 0 ? pageNumber : 1);
		IResultSet<TbUeditorFile> resultSet = ueditorFileService.lFindByDTOOnPermission(dto, pageNumber, CommonConstants.PAGESIZE);
		Map<String, String> nameMap = new HashMap<String, String>();
		for (int i = 0, len = resultSet.getResultList().size(); i < len; i++) {
			TbUser user = userService.findById(resultSet.getResultList().get(i).getCreateUserId());
			nameMap.put(user.getId(), user.getName());
		}
		model.put("nameMap", nameMap);
		model.put("resultSet", resultSet);
		this.setToPageContext(dto);
		return new CommonReturnPageProcessor("ueditorFile/list").returnViewName();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ueditorFile/delete.do")
	@ResponseBody
	public void delete(@RequestParam(value = "ids[]") String[] ids, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			int count = ueditorFileService.lRemoveByIds(ids);
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

	@RequestMapping(method = RequestMethod.POST, value = "/ueditorFile/find.do")
	@ResponseBody
	public void find(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUeditorFile entity = ueditorFileService.findById(id);
			TbUser user = userService.findById(entity.getCreateUserId());
			UeditorFileDTO dto = new UeditorFileDTO();
			AfxBeanUtils.copySamePropertyValue(entity, dto);
			dto.setOperatedUnitId((StringUtils.isBlank(entity.getModifiedUnitId()) ? entity.getCreateUnitId() : entity.getModifiedUnitId()));
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(AfxBeanUtils.getComplexFieldNames(UeditorFileDTO.class));
			JSONObject jsonModel = JSONObject.fromObject(dto, jsonConfig);
			jsonModel.element("createUserId", (null != user ? user.getName() : null));
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