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
import net.yasion.common.dto.SuperUserDTO;
import net.yasion.common.dto.UserDTO;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.ISuperUserService;
import net.yasion.common.service.IUnitService;
import net.yasion.common.service.IUserService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.processor.CommonReturnPageProcessor;
import net.yasion.common.support.common.processor.FrameworkReturnPageProcessor;
import net.yasion.common.utils.AfxBeanUtils;
import net.yasion.common.utils.MD5Util;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserAct extends BaseAction {

	private ISuperUserService superUserService;

	private IUserService userService;

	private IUnitService unitService;

	public ISuperUserService getSuperUserService() {
		return superUserService;
	}

	@Autowired
	public void setSuperUserService(ISuperUserService superUserService) {
		this.superUserService = superUserService;
	}

	public IUserService getUserService() {
		return this.userService;
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IUnitService getUnitService() {
		return unitService;
	}

	@Autowired
	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/user/save.do")
	@ResponseBody
	public void save(@ModelJson UserDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			if (StringUtils.isBlank(dto.getUsername())) {
				out.write("{\"result\":false,\"msg\":\"Message:用户名不能为空\"}");
				return;
			} else {
				TbUser temp = this.userService.lFindByUsername(dto.getUsername());
				if (temp != null) {
					out.write("{\"result\":false,\"msg\":\"Message:用户名已经存在\"}");
					return;
				}
			}
			if (StringUtils.isBlank(dto.getPassword())) {
				out.write("{\"result\":false,\"msg\":\"Message:密码不能为空\"}");
				return;
			}
			if (StringUtils.isBlank(dto.getUnitId())) {
				out.write("{\"result\":false,\"msg\":\"Message:请选择一个所属单位\"}");
				return;
			}
			if (StringUtils.isBlank(dto.getName())) {
				dto.setName(dto.getUsername());
			}
			TbUser userEntity = userService.lSave(dto);
			out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + userEntity.getId() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/user/update.do")
	@ResponseBody
	public void update(@ModelJson UserDTO dto, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			if (StringUtils.isBlank(dto.getName())) {
				dto.setName(dto.getUsername());
			}
			if (StringUtils.isBlank(dto.getUsername())) {
				out.write("{\"result\":false,\"msg\":\"Message:用户名不能为空\"}");
				return;
			}
			if (StringUtils.isBlank(dto.getUnitId())) {
				out.write("{\"result\":false,\"msg\":\"Message:请选择一个所属单位\"}");
				return;
			}
			TbUser temp = this.userService.lFindByUsername(dto.getUsername());
			if (temp != null && !temp.getId().equals(dto.getId())) {
				out.write("{\"result\":false,\"msg\":\"Message:用户名已经存在\"}");
				return;
			}
			TbUser userEntity = userService.lUpdate(dto);
			out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + userEntity.getId() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping("/user/mainFrame.do")
	public String mainFrame() {
		return new CommonReturnPageProcessor("user/mainFrame").returnViewName();
	}

	@RequestMapping("/user/leftFrame.do")
	public String leftFrame() {
		return new CommonReturnPageProcessor("user/leftFrame").returnViewName();
	}

	@RequestMapping("/user/topFrame.do")
	public String topFrame() {
		return new CommonReturnPageProcessor("user/topFrame").returnViewName();
	}

	@RequestMapping("/user/list.do")
	public String list(HttpServletRequest request, @ModelDTOSearch UserDTO dto, ModelMap model, Integer pageNumber) {
		pageNumber = (pageNumber != null && pageNumber != 0 ? pageNumber : 1);
		if ("root".equals(dto.getUnitId())) {
			dto.setUnitId(null);
		}
		IResultSet<TbUser> resultSet = userService.lFindByDTOOnPermission(dto, pageNumber, CommonConstants.PAGESIZE);
		model.put("resultSet", resultSet);
		this.setToPageContext(dto);
		return new CommonReturnPageProcessor("user/list").returnViewName();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/user/delete.do")
	@ResponseBody
	public void delete(@RequestParam(value = "ids[]") String[] ids, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			int count = userService.lRemoveByIds(ids);
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

	@RequestMapping(method = RequestMethod.POST, value = "/user/find.do")
	@ResponseBody
	public void find(String id, ModelMap model, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			TbUser entity = userService.findById(id);
			UserDTO dto = new UserDTO();
			entity.copyValuesTo(dto);
			dto.setPassword(null);
			if (null != entity.getTbUnit()) {
				dto.setUnitId(entity.getTbUnit().getId());
			}
			dto.setOperatedUnitId((StringUtils.isBlank(entity.getModifiedUnitId()) ? entity.getCreateUnitId() : entity.getModifiedUnitId()));
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(AfxBeanUtils.getComplexFieldNames(UserDTO.class));
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

	@RequestMapping("/user/info.do")
	public String info(HttpServletRequest request, ModelMap model) {
		TbUser loginUser = UserUtils.getCurrentUser();
		TbUser nowUser = null;
		if (loginUser instanceof TbSuperUser) {
			nowUser = this.superUserService.findById(loginUser.getId());
		} else {
			nowUser = this.userService.findById(loginUser.getId());
		}
		if (null != nowUser) {
			if (!(nowUser instanceof TbSuperUser)) {
				TbUnit unit = nowUser.getTbUnit();
				StringBuilder builder = new StringBuilder();
				if (null != unit) {
					String unitId = unit.getId();
					builder.append(unitId);
				}
				model.put("unitId", builder.toString());
			}
			model.put("operatedUnitId", (StringUtils.isBlank(loginUser.getModifiedUnitId()) ? loginUser.getCreateUnitId() : loginUser.getModifiedUnitId()));
			model.put("user", nowUser);
		}
		return new FrameworkReturnPageProcessor("info").returnViewName();
	}

	@RequestMapping("/user/infoUpdate.do")
	public void infoUpdate(@ModelJson UserDTO dto, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		TbUser loginUser = UserUtils.getCurrentUser();
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			if (StringUtils.isBlank(dto.getName())) {
				dto.setName(dto.getUsername());
			}
			TbUser userEntity = null;
			if (loginUser instanceof TbSuperUser) {
				userEntity = superUserService.findById(dto.getId());
			} else {
				userEntity = userService.findById(dto.getId());
			}
			if (StringUtils.isBlank(dto.getUsername())) {
				out.write("{\"result\":false,\"msg\":\"Message:用户名不能为空\"}");
				return;
			}
			if (StringUtils.isBlank(dto.getUnitId()) && !(loginUser instanceof TbSuperUser)) {
				out.write("{\"result\":false,\"msg\":\"Message:请选择一个所属单位\"}");
				return;
			}
			TbUser temp = null;
			if (loginUser instanceof TbSuperUser) {
				temp = superUserService.lFindByUsername(dto.getUsername());
			} else {
				temp = userService.lFindByUsername(dto.getUsername());
			}
			if (null != temp && !temp.getId().equals(dto.getId())) {
				out.write("{\"result\":false,\"msg\":\"Message:用户名已经存在\"}");
				return;
			}
			if (StringUtils.isNotBlank(dto.getPassword()) && StringUtils.isNotBlank(dto.getConfirmPassword()) && StringUtils.isNotBlank(dto.getOldPassword())) {
				if (userEntity.getPassword().equals(MD5Util.MD5(dto.getOldPassword()))) {
					if (dto.getConfirmPassword().equals(dto.getPassword())) {
						dto.getExcludeCopyValueFieldNames().add("disable");// 不保存这个字段
						if (loginUser instanceof TbSuperUser) {
							SuperUserDTO superDTO = new SuperUserDTO();
							AfxBeanUtils.copySamePropertyValue(dto, superDTO);
							userEntity = superUserService.lUpdate(superDTO);
						} else {
							userEntity = userService.lUpdate(dto);
						}
					} else {
						out.write("{\"result\":false,\"msg\":\"Message:两次输入的密码不相同\"}");
						return;
					}
				} else {
					out.write("{\"result\":false,\"msg\":\"Message:旧密码输入错误\"}");
					return;
				}
			} else if (StringUtils.isBlank(dto.getPassword()) && StringUtils.isBlank(dto.getConfirmPassword()) && StringUtils.isBlank(dto.getOldPassword())) {
				dto.getExcludeCopyValueFieldNames().add("disable");// 不保存这个字段
				if (loginUser instanceof TbSuperUser) {
					SuperUserDTO superDTO = new SuperUserDTO();
					AfxBeanUtils.copySamePropertyValue(dto, superDTO);
					userEntity = superUserService.lUpdate(superDTO);
				} else {
					userEntity = userService.lUpdate(dto);
				}
			} else {
				out.write("{\"result\":false,\"msg\":\"Message:请输入旧密码、新密码、确认密码!\"}");
				return;
			}
			UserUtils.refreshCurrentUser();// 刷新session对象的用户信息
			out.write("{\"result\":true,\"msg\":\"save success\",\"id\":\"" + userEntity.getId() + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":false,\"msg\":\"Message:" + e.toString() + "\\r\\n\\r\\nCauseBy:" + e.getCause() + "\"}");
		}
	}

	@RequestMapping("/user/ajaxLoadUser.do")
	public void ajaxLoadUser(HttpServletRequest request, HttpServletResponse response, Integer page, @ModelDTOSearch UserDTO dto) {
		PrintWriter out = null;
		dto.setCriteriaLogicExpression("Or(name,username)");
		IResultSet<TbUser> userSet = this.userService.lFindByDTOOnAllUser(dto, page, CommonConstants.PAGESIZE);
		List<TbUser> userList = userSet.getResultList();
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = userList.size(); i < len; i++) {
			TbUser user = userList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", user.getId().toString());
			jsonResult.element("text", user.getName() + "[" + user.getUsername() + "]");
			jsonArr.add(i, jsonResult);
		}
		result.element("result", jsonArr);
		result.element("totalPage", userSet.getTotalPageCount());
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

	@RequestMapping("/user/searchUserById.do")
	public void searchUserById(HttpServletRequest request, HttpServletResponse response, UserDTO dto, @ConverterArrayParam String[] ids) {
		PrintWriter out = null;
		List<TbUser> userList = this.userService.findByIds(ids);
		JSONObject result = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0, len = userList.size(); i < len; i++) {
			TbUser user = userList.get(i);
			JSONObject jsonResult = new JSONObject();
			jsonResult.element("id", user.getId().toString());
			jsonResult.element("text", user.getName() + "[" + user.getUsername() + "]");
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

	@RequestMapping("/user/register.do")
	public String register(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		TbUser currentUser = UserUtils.getCurrentUser();
		if (currentUser == null) {// 禁止登录用户使用注册界面
			// 设置无缓存
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
		} else {
			return new FrameworkReturnPageProcessor("index").returnViewName();
		}
		return new CommonReturnPageProcessor("user/register").returnViewName();
	}

	@RequestMapping("/user/registerSubmit.do")
	public String registerSubmit(UserDTO user, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getPassword())) {
			model.put("error", "数据异常,请勿非法操作!");
			return new CommonReturnPageProcessor("user/register").returnViewName();
		} else {
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(user.getUsername());
			IResultSet<TbUser> userSet = this.userService.findByDTO(userDTO, null, null);
			if (null == userSet || 0 == userSet.getTotalResultCount()) {
				user.setDisable("A");// 未审核
				this.userService.save(user);
			} else {
				model.put("error", "数据异常,请勿非法操作!");
				return new CommonReturnPageProcessor("user/register").returnViewName();
			}
		}
		return new CommonReturnPageProcessor("user/registerSuccess").returnViewName();
	}

	@RequestMapping("/user/testUserName.do")
	public void testUserName(String username, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		IResultSet<TbUser> userSet = this.userService.findByDTO(userDTO, null, null);
		PrintWriter out = null;
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			if (null != userSet && 0 < userSet.getTotalResultCount()) {
				out.write("false");
			} else {
				out.write("true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("false");
		}
	}
}