package net.yasion.common.constant;

import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.model.TbSuperUser;

public class CommonConstants extends ConfigurableConstants {

	// 静态初始化读入config.properties中的设置
	static {
		load("WEB-INF/config/properties/constant/common.properties");
	}

	protected CommonConstants() {
		super();
	}

	/** 超级用户(超级用户/真正超级管理员)实体类名 */
	public static final String SUPER_USER_CLASS_NAME = TbSuperUser.class.getName();
	/** 项目部署路径 */
	public static final String CONTEXT_PATH = HttpInternalObjectManager.getServletContext().getContextPath();
	/** WEB_INF路径 */
	public static final String WEB_INF_PATH = "/WEB-INF";
	/** 框架风格,读取/WEB-INF/pages/framework下面的文件夹,默认default */
	public static final String FRAMEWORK_STYLE = getProperty("frameworkStyle", "default");
	/** 公共后台风格,读取/WEB-INF/pages/common下面的文件夹,默认default */
	public static final String COMMON_STYLE = getProperty("commonStyle", "default");
	/** 自定义模块风格,读取/WEB-INF/pages/[*]下面的文件夹,默认default */
	public static final String CUSTOM_STYLE = getProperty("customStyle", "default");
	/** 前台风格,读取/WEB-INF/pages/frontend下面的文件夹,默认default */
	public static final String FRONTEND_STYLE = getProperty("frontendStyle", "default");
	/** BaseDAOImpl 实体存放的包名 */
	public static final String MODEL_PAGECKAGE_NAME = getProperty("modelPaeckageName", "net.yasion.tsdp.model");
	/** cookie中的JSESSIONID名称 */
	public static final String JSESSION_COOKIE = "JSESSIONID";
	/** url中的jsessionid名称 */
	public static final String JSESSION_URL = "jsessionid";
	/** HTTP POST请求 */
	public static final String POST = "POST";
	/** HTTP GET请求 */
	public static final String GET = "GET";
	/** 部署路径属性名称 */
	public static final String CONTEXT_PATH_ATTRIBUTE_NAME = "base";
	/** tomcat session id 共享属性名称 */
	public static final String JSESSION_ID = "jsessionId";
	/** session id 属性名称 */
	public static final String SESSION_ID = "sessionId";
	/** 登录模块用来保存user到session的标识 */
	public static final String LOGIN_USER = "login_user";
	/** 登录模块用来表示错误代码的标识 */
	public static final String LOGIN_ERR_MSG = "loginErrMsg";
	/** 分页页面记录数 */
	public static final Integer PAGESIZE = getNumberProperty("pageSize", 15).intValue();
	/** HttpInternalObjectManager维护的线程唯一request对象标记 */
	public static final String THREAD_GLOBAL_VARIABLE_REQUEST = "threadRequest";
	/** HttpInternalObjectManager维护的线程唯一response对象标记 */
	public static final String THREAD_GLOBAL_VARIABLE_RESPONSE = "threadResponse";
	/** 全局请求对象匹配URL对象标志 */
	public static final String GLOBAL_REQUEST_MAPPING_URL = "globalRequestMappingUrl";
	/** 登录排除名单地址 */
	public static final String LOGIN_EXCLUDE_URLS = getProperty("loginExcludeUrls");
	/** 权限重定向的地址 */
	public static final String PERMISSION_REDIRECT_URL = getProperty("permissionRedirectUrl");
	/** 权限排除名单地址 */
	public static final String PERMISSION_EXCLUDE_URLS = getProperty("permissionExcludeUrls");
	/** 权限默认白名单地址 */
	public static final String PERMISSION_DEF_WHITE_URLS = getProperty("permissionDefWhiteUrls");
	/** 权限默认黑名单地址 */
	public static final String PERMISSION_DEF_BLACK_URLS = getProperty("permissionDefBlackUrls");
	/** 公共的日期时间格式 */
	public static final String COMMON_DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 管理员角色编码 */
	public static final String ADMIN_ROLE_CODE = getProperty("adminRoleCode", "ADMIN,SUPERADMIN");
	/** 管理员单位ID */
	public static final String ADMIN_UNIT_ID = "SUPERADMIN";
	/** 系统单位ID */
	public static final String SYSTEM_UNIT_ID = "SYSTEM";
	/** 逻辑删除标志 */
	public static final String LOGICAL_DELETE_FLAG = "D";
	/** ModelJson注解流重置失败之后将值备份到request Attribute的属性名 */
	public static final String MODEL_JSON_BACKUP_ATTRIBUTE_NAME = "modelJsonBackupAttributeName";
	/** UEditor上传的文件保存路径 */
	public static final String UEDITOR_UPLOAD_PATH = getProperty("ueditorUploadPath");
	/** UEditor上传file元素的名称 */
	public static final String UEDITOR_FILE_FIELD_NAME = getProperty("ueditorFileFieldName");
	/** uploadFile上传file元素的名称 */
	public static final String UPLOAD_FILE_FIELD_NAME = getProperty("uploadFileFieldName");
	/** 个人空间路径,一定在/WEB-INF目录下 */
	public static final String PERSONAN_SPACE_PATH = getProperty("personalSpacePath");
}