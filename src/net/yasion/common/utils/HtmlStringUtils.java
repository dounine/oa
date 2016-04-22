package net.yasion.common.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;

/** Html字符串工具类,处理相关Html、url、转义字符等操作功能 */
public class HtmlStringUtils {

	/** 禁止实例化 */
	protected HtmlStringUtils() {
	}

	/**
	 * 处理url
	 * 
	 * url为null返回null，url为空串或以http://或https://开头，则加上http://，其他情况返回原参数。
	 * 
	 * @param url
	 *            要处理的URL
	 * @return 处理后的结果
	 */
	public static String handelUrl(String url) {
		if (url == null) {
			return null;
		}
		url = url.trim();
		if (url.equals("") || url.startsWith("http://") || url.startsWith("https://")) {
			return url;
		} else {
			return "http://" + url.trim();
		}
	}

	/**
	 * 分割并且去除空格
	 * 
	 * @param str
	 *            待分割字符串
	 * @param sep
	 *            分割符
	 * @param sep2
	 *            第二个分隔符
	 * @return 如果str为空，则返回null。
	 */
	public static String[] splitAndTrim(String str, String sep, String sep2) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		if (!StringUtils.isBlank(sep2)) {
			str = StringUtils.replace(str, sep2, sep);
		}
		String[] arr = StringUtils.split(str, sep);
		// trim
		for (int i = 0, len = arr.length; i < len; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}

	/**
	 * 文本转html
	 * 
	 * @param txt
	 *            处理的文本
	 * @return 处理后的结果
	 */
	public static String txt2Html(String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
		char c;
		boolean doub = false;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			if (c == ' ') {
				if (doub) {
					sb.append(' ');
					doub = false;
				} else {
					sb.append("&nbsp;");
					doub = true;
				}
			} else {
				doub = false;
				switch (c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				default:
					sb.append(c);
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 剪切文本。如果进行了剪切，则在文本后加上"..."
	 * 
	 * @param str
	 *            剪切对象。
	 * @param len
	 *            编码小于256的作为一个字符，大于256的作为两个字符。
	 * @return 处理后的结果
	 */
	public static String textCut(String str, int len, String append) {
		if (str == null) {
			return null;
		}
		int slen = str.length();
		if (slen <= len) {
			return str;
		}
		// 最大计数（如果全是英文）
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (str.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!StringUtils.isBlank(append)) {
				if (str.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return str.substring(0, i) + append;
			} else {
				return str.substring(0, i);
			}
		} else {
			return str;
		}
	}

	/**
	 * 将html转换成字符串,并指定最大长度,超过则加入append省略符
	 * 
	 * @param html
	 *            html代码
	 * @param len
	 *            最大长度
	 * @param append
	 *            省略符
	 * @return 处理后的字符串
	 */
	public static String htmlCut(String html, int len, String append) {
		String text = html2Text(html, len * 2);
		return textCut(text, len, append);
	}

	/**
	 * html转换成显示的文本,并且指定文本最大长度,超过则不显示
	 * 
	 * @param html
	 *            html代码
	 * @param len
	 *            最大长度
	 * @return 处理后的结果
	 */
	public static String html2Text(String html, int len) {
		try {
			Lexer lexer = new Lexer(html);
			Node node;
			StringBuilder sb = new StringBuilder(html.length());
			while ((node = lexer.nextNode()) != null) {
				if (node instanceof TextNode) {
					sb.append(node.toHtml());
				}
				if (sb.length() > len) {
					break;
				}
			}
			return sb.toString();
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。
	 * 
	 * @param str
	 *            目标字符串
	 * @param search
	 *            搜索的字符串
	 * @return 是否包含
	 */
	public static boolean contains(String str, String search) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(search)) {
			return false;
		}
		String reg = StringUtils.replace(search, "*", ".*");
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}

	/**
	 * 是否包含转移字符串
	 * 
	 * @param str
	 *            判断的目标字符串
	 * @return 布尔值，是否包含
	 */
	public static boolean containsEscapeString(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		if (str.contains("'") || str.contains("\"") || str.contains("\r") || str.contains("\n") || str.contains("\t") || str.contains("\b") || str.contains("\f")) {
			return true;
		}
		return false;
	}

	/**
	 * 替换转义字符
	 * 
	 * @param str
	 *            要替换的字符串
	 * @return 替换后的字符串
	 */
	public static String replaceEscapeString(String str) {
		if (containsEscapeString(str)) {
			return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\\t").replace("\b", "\\b").replace("\f", "\\f");
		} else {
			return str;
		}
	}

	public static void main(String args[]) {
		System.out.println(replaceEscapeString("&nbsp;\r" + "</p>"));
	}
}