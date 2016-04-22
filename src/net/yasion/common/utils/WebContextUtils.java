package net.yasion.common.utils;

import org.apache.commons.lang3.StringUtils;

public class WebContextUtils {

	public static final String WEB_INF = "WEB-INF";

	/**
	 * 静态获取项目的绝对路径,并且拼接上propertyFileName指定的路径(也就是说获取项目路径下的propertyFileName目录的绝对路径)
	 * 
	 * @param propertyFileName
	 *            文件名
	 * @return 绝对路径
	 */
	public static String getWebContextRealPath(String propertyFileName) {
		String path = WebContextUtils.class.getResource("/").getPath();
		int start = path.indexOf(WEB_INF);
		String basePath = (path.substring(0, start));
		basePath = (StringUtils.endsWith(basePath, "/") || StringUtils.endsWith(basePath, "\\") ? basePath : (basePath + "/"));
		if (StringUtils.isNotBlank(propertyFileName)) {
			propertyFileName = StringUtils.trim(propertyFileName);
			propertyFileName = ((StringUtils.startsWith(propertyFileName, "/") || StringUtils.startsWith(propertyFileName, "\\")) ? StringUtils.substring(propertyFileName, 1, propertyFileName.length()) : propertyFileName);
		} else {
			propertyFileName = (null == propertyFileName ? "" : StringUtils.trim(propertyFileName));
		}
		return (basePath + propertyFileName);
	}

	/**
	 * 静态获取项目WEB-INF的绝对路径
	 * 
	 * @param propertyFileName
	 *            文件名
	 * @return WEB-INF的绝对路径
	 */
	public static String getWebContextWebInfRealPath() {
		String path = WebContextUtils.class.getResource("/").getPath();
		int start = path.indexOf(WEB_INF);
		String basePath = (path.substring(0, start));
		basePath = (StringUtils.endsWith(basePath, "/") || StringUtils.endsWith(basePath, "\\") ? basePath : (basePath + "/"));
		return (basePath + WEB_INF);
	}

	/**
	 * 判断指定的path是不是基于web context的路径
	 * 
	 * @param path
	 *            指定路径
	 * @return 是否web context路径
	 */
	public static boolean isWebContextPath(String path) {
		return (StringUtils.startsWith(path, "/") || StringUtils.startsWith(path, "\\"));
	}

	/**
	 * 判断指定的path是不是基于web-inf的路径
	 * 
	 * @param path
	 *            指定路径
	 * @return 是否web-inf路径
	 */
	public static boolean isWebInfPath(String path) {
		return (StringUtils.startsWith(path, WebContextUtils.WEB_INF) || StringUtils.startsWith(path, "/" + WebContextUtils.WEB_INF) || StringUtils.startsWith(path, "\\" + WebContextUtils.WEB_INF));
	}
}