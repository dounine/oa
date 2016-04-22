package net.yasion.common.web.action;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.dto.UeditorFileDTO;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUeditorFile;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.model.TbWebFile;
import net.yasion.common.service.IUEditorService;
import net.yasion.common.service.IUeditorFileService;
import net.yasion.common.service.IWebFileService;
import net.yasion.common.support.common.bean.UploadFile;
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

import sun.misc.BASE64Decoder;

@Controller
public class UEditorAct extends BaseAction {

	private IUEditorService ueditorService;

	private IWebFileService webFileService;

	private IUeditorFileService ueditorFileService;

	public IUEditorService getUeditorService() {
		return ueditorService;
	}

	@Autowired
	public void setUeditorService(IUEditorService ueditorService) {
		this.ueditorService = ueditorService;
	}

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

	@RequestMapping(method = RequestMethod.POST, value = "/ueditor/upload.do")
	@ResponseBody
	public void upload(ModelMap model, HttpServletResponse response, MultipartHttpServletRequest request) {
		PrintWriter out = null;
		String type = HttpUtils.getQueryParam(request, "type");
		type = (StringUtils.isBlank(type) ? "file" : type);
		try {
			DateFormat df = new SimpleDateFormat(CommonConstants.COMMON_DATA_TIME_FORMAT);
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUser currentUser = UserUtils.getCurrentUser();
			String realPath = HttpInternalObjectManager.getServletContext().getRealPath("/" + CommonConstants.UEDITOR_UPLOAD_PATH) + "/" + (null == currentUser ? "temp" : currentUser.getId()) + "/" + type + "/";
			MultipartFile file = request.getFile(CommonConstants.UEDITOR_FILE_FIELD_NAME);
			UploadFile uploadFile = HttpUtils.upload(request, CommonConstants.UEDITOR_FILE_FIELD_NAME, realPath);
			if (null != uploadFile) {
				String url = null;
				try {
					UeditorFileDTO ueditorFileDTO = new UeditorFileDTO();
					ueditorFileDTO.setUeditorType(type);
					ueditorFileDTO.setFileName(uploadFile.getRealFileName());
					ueditorFileDTO.setOriginalName(uploadFile.getOrigFileName());
					ueditorFileDTO.setFileType(uploadFile.getFileType());
					ueditorFileDTO.setSize(BigInteger.valueOf(uploadFile.getFileLength()));
					String contextRealPath = HttpInternalObjectManager.getServletContext().getRealPath("/");
					String fileAbsolutePath = uploadFile.getFile().getAbsolutePath();
					String relativePath = fileAbsolutePath.replace(contextRealPath, "");
					relativePath = relativePath.replaceAll("\\\\", "/");
					relativePath = (relativePath.startsWith("/") ? relativePath : "/" + relativePath);
					ueditorFileDTO.setFilePath(relativePath);
					ueditorFileDTO.setFileId(uploadFile.getFileId());
					ueditorFileDTO.setUploadTime(df.format(new Date()));
					if (null != currentUser) {
						TbUnit unit = currentUser.getTbUnit();
						ueditorFileDTO.setOperatedUnitId(currentUser instanceof TbSuperUser ? CommonConstants.ADMIN_UNIT_ID : ((null != unit) ? unit.getId() : ""));
					}
					TbUeditorFile ueFileEntity = ueditorFileService.lSave(ueditorFileDTO);
					url = (null != ueFileEntity ? ("/ueditorFile/visit.do?id=" + ueFileEntity.getId() + "&showName=" + ueFileEntity.getOriginalName())
							: (StringUtils.isNotBlank(uploadFile.getFileId()) ? ("/webFile/visit.do?id=" + uploadFile.getFileId() + "&showName=" + uploadFile.getOrigFileName()) : ""));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.isBlank(url)) {
					url = ("/" + (CommonConstants.UEDITOR_UPLOAD_PATH.endsWith("/") ? CommonConstants.UEDITOR_UPLOAD_PATH : CommonConstants.UEDITOR_UPLOAD_PATH + "/") + (null == currentUser ? "temp" : currentUser.getId()) + "/" + type + "/" + uploadFile.getRealFileName());
				}
				out.write("{\"success\":true,\"url\":\"" + (url) + "\",\"fileType\":\"" + uploadFile.getFileType() + "\",\"status\":\"SUCCESS\",\"state\":\"SUCCESS\",\"original\":\"" + file.getOriginalFilename() + "\",\"fileName\":\"" + uploadFile.getRealFileName() + "\"}");
			} else {
				out.write("{\"success\":false,\"status\":\"ERROR\",\"state\":\"\u4E0A\u4F20\u5931\u8D25\",\"msg\":\"\u4E0A\u4F20\u5931\u8D25\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"url\":\"\",\"fileType\":\"\",\"state\":\"\u6587\u4EF6\u4E0A\u4F20\u5931\u8D25\",\"status\":\"ERROR\",\"msg\":\"\u6587\u4EF6\u4E0A\u4F20\u5931\u8D25\",\"original\":\"\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ueditor/uploadScraw.do")
	@ResponseBody
	public void uploadScraw(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
		PrintWriter out = null;
		String type = HttpUtils.getQueryParam(request, "type");
		type = (StringUtils.isBlank(type) ? "file" : type);
		try {
			DateFormat df = new SimpleDateFormat(CommonConstants.COMMON_DATA_TIME_FORMAT);
			UploadFile uploadFile = null;
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			String param = request.getParameter("action");
			TbUser currentUser = UserUtils.getCurrentUser();
			String realPath = HttpInternalObjectManager.getServletContext().getRealPath("/" + CommonConstants.UEDITOR_UPLOAD_PATH) + "/" + (null == currentUser ? "temp" : currentUser.getId()) + "/" + type + "/";
			MultipartHttpServletRequest multipartRequest = null;
			if (StringUtils.isNotBlank(param) && "tmpImg".equals(param)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				uploadFile = HttpUtils.upload(multipartRequest, CommonConstants.UEDITOR_FILE_FIELD_NAME, realPath);
			} else {
				String base64Data = request.getParameter("content");
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] fileData = decoder.decodeBuffer(base64Data);
				uploadFile = HttpUtils.upload(fileData, realPath, "test.png");
			}
			if (null != uploadFile) {
				String url = null;
				try {
					UeditorFileDTO ueditorFileDTO = new UeditorFileDTO();
					ueditorFileDTO.setUeditorType(type);
					ueditorFileDTO.setFileName(uploadFile.getRealFileName());
					ueditorFileDTO.setOriginalName(uploadFile.getOrigFileName());
					ueditorFileDTO.setFileType(uploadFile.getFileType());
					ueditorFileDTO.setSize(BigInteger.valueOf(uploadFile.getFileLength()));
					String contextRealPath = HttpInternalObjectManager.getServletContext().getRealPath("/");
					String fileAbsolutePath = uploadFile.getFile().getAbsolutePath();
					String relativePath = fileAbsolutePath.replace(contextRealPath, "");
					relativePath = relativePath.replaceAll("\\\\", "/");
					relativePath = (relativePath.startsWith("/") ? relativePath : "/" + relativePath);
					ueditorFileDTO.setFilePath(relativePath);
					ueditorFileDTO.setFileId(uploadFile.getFileId());
					ueditorFileDTO.setUploadTime(df.format(new Date()));
					if (null != currentUser) {
						TbUnit unit = currentUser.getTbUnit();
						ueditorFileDTO.setOperatedUnitId(currentUser instanceof TbSuperUser ? CommonConstants.ADMIN_UNIT_ID : ((null != unit) ? unit.getId() : ""));
					}
					TbUeditorFile ueFileEntity = ueditorFileService.lSave(ueditorFileDTO);
					url = (null != ueFileEntity ? ("/ueditorFile/visit.do?id=" + ueFileEntity.getId() + "&showName=" + ueFileEntity.getOriginalName())
							: (StringUtils.isNotBlank(uploadFile.getFileId()) ? ("/webFile/visit.do?id=" + uploadFile.getFileId() + "&showName=" + uploadFile.getOrigFileName()) : ""));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.isBlank(url)) {
					url = ("/" + (CommonConstants.UEDITOR_UPLOAD_PATH.endsWith("/") ? CommonConstants.UEDITOR_UPLOAD_PATH : CommonConstants.UEDITOR_UPLOAD_PATH + "/") + (null == currentUser ? "temp" : currentUser.getId()) + "/" + type + "/" + uploadFile.getRealFileName());
				}
				if (StringUtils.isNotBlank(param) && "tmpImg".equals(param)) {
					response.setContentType("text/html;charset=UTF-8");
					out = response.getWriter();
					out.print("<script>parent.ue_callback('" + (url) + "','SUCCESS')</script>");
				} else {
					response.setContentType("text/json;charset=UTF-8");
					out = response.getWriter();
					out.write("{'url':'" + (url) + "',state:'SUCCESS'}");
				}
			} else {
				response.setContentType("text/json;charset=UTF-8");
				out = response.getWriter();
				out.write("{\"success\":false,\"status\":\"ERROR\",\"state\":\"\u4E0A\u4F20\u5931\u8D25\",\"msg\":\"\u4E0A\u4F20\u5931\u8D25\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"url\":\"\",\"fileType\":\"\",\"state\":\"\u6587\u4EF6\u4E0A\u4F20\u5931\u8D25\",\"status\":\"ERROR\",\"msg\":\"\u6587\u4EF6\u4E0A\u4F20\u5931\u8D25\",\"original\":\"\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ueditor/imageManager.do")
	@ResponseBody
	public void imageManager(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUser currentUser = UserUtils.getCurrentUser();
			UeditorFileDTO dto = new UeditorFileDTO();
			if (null != currentUser) {
				dto.setCreateUserId(currentUser.getId());
			}
			dto.setUeditorType("image");
			List<TbUeditorFile> imageFileList = ueditorFileService.lFindByDTO(dto, null, null).getResultList();
			String imgStr = "";
			for (int i = 0, len = imageFileList.size(); i < len; i++) {
				TbUeditorFile ueFileEntity = imageFileList.get(i);
				TbWebFile webFile = webFileService.findByUUIDName(ueFileEntity.getFileName());
				String url = (null != ueFileEntity ? ("/ueditorFile/visit.do?id=" + ueFileEntity.getId() + "&showName=" + ueFileEntity.getOriginalName()) : (null != webFile ? ("/webFile/visit.do?id=" + webFile.getId() + "&showName=" + webFile.getOriginalFileName()) : ""));
				imgStr += (url + "ue_separate_ue");
			}
			if (imgStr != "") {
				imgStr = imgStr.substring(0, imgStr.lastIndexOf("ue_separate_ue")).replace(File.separator, "/").trim();
			}
			out.print(imgStr);
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"url\":\"\",\"fileType\":\"\",\"state\":\"\u8BBF\u95EE\u51FA\u9519\",\"status\":\"ERROR\",\"msg\":\"\u8BBF\u95EE\u51FA\u9519\":\"\"}");
		}
	}
}