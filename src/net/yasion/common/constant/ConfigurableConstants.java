package net.yasion.common.constant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 可用Properties文件配置的Constants基类. 本类既保持了Constants的static和final(静态与不可修改)特性,又拥有了可用Properties文件配置的特征, 主要是应用了Java语言中静态初始化代码的特性.
 */
public class ConfigurableConstants {

	private static final String WEB_INF = "WEB-INF";

	private static final Properties properties = new Properties();

	public static Properties getProperties() {
		return properties;
	}

	protected ConfigurableConstants() {
		super();
	}

	/**
	 * 静态读入属性文件到Properties properties变量中
	 * 
	 * @param propertyFileName
	 *            properties文件名
	 */
	protected static void load(String propertyFileName) {
		InputStream in = null;
		try {

			String path = ConfigurableConstants.class.getResource("").getPath();
			int start = path.indexOf(WEB_INF);
			String basePath = (path.substring(0, start));
			in = new FileInputStream(basePath + propertyFileName);
			if (in != null) {
				properties.load(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取指定属性的值，返回是String类型
	 * 
	 * @param key
	 *            property key.
	 * @param defaultValue
	 *            当使用property key在properties中取不到值时的默认值.
	 * @return 返回属性值，若无则是defaultValue值
	 */
	public static String getProperty(String key, String defaultValue) {
		return StringUtils.trim(properties.getProperty(key, defaultValue));
	}

	/**
	 * 获取指定属性的值，返回是String类型
	 * 
	 * @param key
	 *            property key.
	 * @return 返回属性值，若无则是null
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key, null);
	}

	/**
	 * 获取指定属性的值，返回是Number类型
	 * 
	 * @param key
	 *            property key.
	 * @param defaultValue
	 *            当使用property key在properties中取不到值时的默认的数值.
	 * @return 返回属性值，若无则是defaultValue值
	 */
	public static Number getNumberProperty(String key, Number defaultValue) {
		BigDecimal decimal = new BigDecimal(StringUtils.trim(properties.getProperty(key, defaultValue.toString())));
		return (Number) decimal;
	}

	/**
	 * 获取指定属性的值，返回是Number类型
	 * 
	 * @param key
	 *            property key.
	 * @return 返回属性值，若无则是0
	 */
	public static Number getNumberProperty(String key) {
		return getNumberProperty(key, 0);
	}

	/**
	 * 获取指定属性的值，返回是Boolean类型
	 * 
	 * @param key
	 *            property key.
	 * @param defaultValue
	 *            当使用property key在properties中取不到值时的默认的数值.
	 * @return 返回属性值，若无则是defaultValue值
	 */
	public static Boolean getBooleanProperty(String key, Boolean defaultValue) {
		Boolean bool = new Boolean(StringUtils.trim(properties.getProperty(key, defaultValue.toString())));
		return bool;
	}

	/**
	 * 获取指定属性的值，返回是Boolean类型
	 * 
	 * @param key
	 *            property key.
	 * @return 返回属性值，若无则是false
	 */
	public static Boolean getBooleanProperty(String key) {
		return getBooleanProperty(key, false);
	}
}