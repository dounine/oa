package net.yasion.common.constant;

public class ContentTypeConstants extends ConfigurableConstants {

	// 静态初始化读入config.properties中的设置
	static {
		load("WEB-INF/config/properties/constant/contentType.properties");
	}

	protected ContentTypeConstants() {
		super();
	}

	public static void main(String[] args) {
		System.out.println(getProperties() + " " + getProperty("apk"));
	}
}