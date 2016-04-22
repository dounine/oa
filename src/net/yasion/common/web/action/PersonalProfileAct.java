package net.yasion.common.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.annotation.ModelJson;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.dto.PermissionDTO;
import net.yasion.common.dto.PersonalProfileDTO;
import net.yasion.common.model.TbPersonalProfile;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IPersonalProfileService;
import net.yasion.common.support.common.bean.UploadFile;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;
import net.yasion.common.utils.HttpUtils;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class PersonalProfileAct extends BaseAction {

	private IPersonalProfileService personalProfileService;

	public IPersonalProfileService getPersonalProfileService() {
		return personalProfileService;
	}

	@Autowired
	public void setPersonalProfileService(IPersonalProfileService personalProfileService) {
		this.personalProfileService = personalProfileService;
	}

	@RequestMapping("/personalProfile/detail.do")
	public String detail(HttpServletRequest request, @ModelDTOSearch PermissionDTO dto, ModelMap model, Integer pageNumber) {
		TbPersonalProfile currentUserPersonalProfile = this.personalProfileService.getCurrentUserPersonalProfile();
		model.put("currentUserPersonalProfile", currentUserPersonalProfile);
		model.put("jqueryVersion", "1.7.2");// 指定JQuery版本
		return new CommonReturnPageProcessor("personalProfile/detail").returnViewName();
	}

	@RequestMapping("/personalProfile/submit.do")
	public void submit(@ModelJson PersonalProfileDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbPersonalProfile entity = null;
			TbUser currentUser = UserUtils.getCurrentUser();
			dto.setUserId(currentUser.getId());
			if (StringUtils.isBlank(dto.getId())) {
				entity = this.personalProfileService.lSave(dto);
				out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
			} else {
				entity = this.personalProfileService.lUpdate(dto);
				out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + entity.getId() + "\"}");
			}
			UserUtils.refreshCurrentUser();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/personalProfile/uploadPhoto.do")
	@ResponseBody
	public void uploadPhoto(ModelMap model, HttpServletResponse response, MultipartHttpServletRequest request) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUser currentUser = UserUtils.getCurrentUser();
			String webInfPath = CommonConstants.WEB_INF_PATH;
			webInfPath = (webInfPath.startsWith("/") ? webInfPath : ("/" + webInfPath));
			String personalSpacePath = CommonConstants.PERSONAN_SPACE_PATH;
			personalSpacePath = (personalSpacePath.startsWith("/") ? personalSpacePath : ("/" + personalSpacePath));
			String realPath = HttpInternalObjectManager.getServletContext().getRealPath(webInfPath + personalSpacePath) + "/" + currentUser.getId() + "/photo";
			MultipartFile file = request.getFile("photoUpload");
			UploadFile uploadFile = HttpUtils.upload(request, "photoUpload", realPath);
			if (null != uploadFile) {
				out.write("{\"success\":true,\"url\":\"" + ("/webFile/visit.do?id=" + uploadFile.getFileId() + "&showName=" + uploadFile.getOrigFileName()) + "\",\"fileType\":\"" + uploadFile.getFileType() + "\",\"status\":\"SUCCESS\",\"original\":\""
						+ file.getOriginalFilename() + "\",\"fileName\":\"" + uploadFile.getRealFileName() + "\",\"fileId\":\"" + uploadFile.getFileId() + "\"}");
			} else {
				out.write("{\"success\":false,\"status\":\"ERROR\",\"msg\":\"\u4E0A\u4F20\u5931\u8D25\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"success\":false,\"status\":\"ERROR\",msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}
}