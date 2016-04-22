package net.yasion.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.constant.ContentTypeConstants;
import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.dto.WebFileDTO;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbWebFile;
import net.yasion.common.service.IWebFileService;
import net.yasion.common.service.impl.WebFileServiceImpl;
import net.yasion.common.support.common.bean.Pair;
import net.yasion.common.support.common.bean.UploadFile;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UrlPathHelper;

/** Http对象工具类,包括request、response、cookies等操作功能 */
public class HttpUtils {

	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";

	/** 支持的内容类型 */
	public static final HashMap<String, String> contentTypeMap = new HashMap<String, String>();

	/* 初始化 contentTypeMap 加载默认是的支持类型 */
	static {
		contentTypeMap.put("html", "text/html");
		contentTypeMap.put("xml", "text/xml");
		contentTypeMap.put("hta", "application/hta");
		contentTypeMap.put("doc", "application/msword");
		contentTypeMap.put("wps", "application/vnd.ms-works");
		contentTypeMap.put("xls", "application/vnd.ms-excel");
		contentTypeMap.put("htm", "text/html");
		contentTypeMap.put("gif", "image/gif");
		contentTypeMap.put("jpeg", "image/jpeg");
		contentTypeMap.put("jpg", "image/jpeg");
		contentTypeMap.put("mht", "message/rfc822");
		contentTypeMap.put("mhtml", "message/rfc822");
		contentTypeMap.put("pdf", "application/pdf");
		contentTypeMap.put("ppt", "application/vnd.ms-powerpoint");
		contentTypeMap.put("pps", "application/vnd.ms-powerpoint");
		contentTypeMap.put("tif", "image/tiff");
		contentTypeMap.put("tiff", "image/tiff");
		contentTypeMap.put("txt", "text/plain");
		contentTypeMap.put("zip", "application/zip");
		contentTypeMap.put("rar", "application/rar");
		contentTypeMap.put("class", "application/x-java-vm");
		contentTypeMap.put("jar", "application/x-java-archive");
		contentTypeMap.put("ser", "application/x-java-serialized");
		contentTypeMap.put("exe", "application/octet-stream");
		contentTypeMap.put("hdml", "text/x-hdml");
		contentTypeMap.put("bmp", "image/bmp");
		contentTypeMap.put("ico", "image/x-icon");
		contentTypeMap.put("wml", "text/vnd.wap.wml");
		contentTypeMap.put("wmls", "text/vnd.wap.wmlscript");
		contentTypeMap.put("wmlc", "application/vnd.wap.wmlc");
		contentTypeMap.put("wmlsc", "application/vnd.wap.wmlscript");
		contentTypeMap.put("wbmp", "image/vnd.wap.wbmp");
		contentTypeMap.put("csv", "application/msexcel");
		contentTypeMap.put("vsd", "application/vnd.visio");
		contentTypeMap.put("p7b", "application/x-pkcs7-certificates");
		contentTypeMap.put("cer", "application/x-x509-ca-cert");
		contentTypeMap.put("der", "application/x-x509-ca-cert");
	}

	public static class UtilsHttpResponse {

		private Header[] headers;
		private byte[] dataEntity;
		private Locale locale;
		private ProtocolVersion protocolVersion;
		private StatusLine statusLine;

		public UtilsHttpResponse() {
			super();
		}

		public UtilsHttpResponse(Header[] headers, byte[] dataEntity, Locale locale, ProtocolVersion protocolVersion, StatusLine statusLine) {
			super();
			this.headers = headers;
			this.dataEntity = dataEntity;
			this.locale = locale;
			this.protocolVersion = protocolVersion;
			this.statusLine = statusLine;
		}

		void setHeaders(Header[] headers) {
			this.headers = headers;
		}

		void setDataEntity(byte[] dataEntity) {
			this.dataEntity = dataEntity;
		}

		void setLocale(Locale locale) {
			this.locale = locale;
		}

		void setProtocolVersion(ProtocolVersion protocolVersion) {
			this.protocolVersion = protocolVersion;
		}

		void setStatusLine(StatusLine statusLine) {
			this.statusLine = statusLine;
		}

		public Header getHeader(String key) {
			Header returnHeader = null;
			for (int i = 0, len = headers.length; i < len; i++) {
				Header header = headers[i];
				String headerName = header.getName();
				if (headerName.equals(key)) {
					returnHeader = header;
					break;
				}
			}
			return returnHeader;
		}

		public Header[] getHeaders() {
			return headers;
		}

		public byte[] getDataEntity() {
			return dataEntity;
		}

		public Locale getLocale() {
			return locale;
		}

		public ProtocolVersion getProtocolVersion() {
			return protocolVersion;
		}

		public StatusLine getStatusLine() {
			return statusLine;
		}
	}

	/**
	 * 获取代理服务配置
	 * 
	 * @param hostname
	 *            代理服务器的主机名
	 * @param port
	 *            代理服务器的端口
	 * @param scheme
	 *            代理服务器使用的协议
	 * @return 对应的代理配置
	 */
	public static RequestConfig getProxy(String hostname, int port, String scheme) {
		// 依次是代理地址，代理端口号，协议类型
		HttpHost proxy = new HttpHost(hostname, port, scheme);
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		return config;
	}

	/**
	 * 按默认方式生成SSL的客户端可关闭的请求对象
	 * 
	 * @return 返回一个SSL的客户端可关闭的请求对象
	 */
	public static CloseableHttpClient createSSLClient() {
		return createSSLClient(null, null, null, null, null, null);
	}

	/**
	 * 生成SSL的客户端可关闭的请求对象
	 * 
	 * @param keyStoreFilePath
	 *            密码保护文件路径
	 * @param keyStorePassword
	 *            密码保护文件解密密码,默认为null,即是没有密码
	 * @return 返回一个SSL的客户端可关闭的请求对象
	 */
	public static CloseableHttpClient createSSLClient(String keyStoreFilePath, String keyStorePassword) {
		return createSSLClient(keyStoreFilePath, keyStorePassword, null, null, null, null);
	}

	/**
	 * 生成SSL的客户端可关闭的请求对象
	 * 
	 * @param keyStoreFilePath
	 *            密码保护文件路径
	 * @param keyStorePassword
	 *            密码保护文件解密密码,默认为null,即是没有密码
	 * @param trustStrategy
	 *            信任策略,默认只相信自己的CA和所有自签名的证书
	 * @param supportedProtocols
	 *            SSL要支持的协议
	 * @return 返回一个SSL的客户端可关闭的请求对象
	 */
	public static CloseableHttpClient createSSLClient(String keyStoreFilePath, String keyStorePassword, TrustStrategy trustStrategy, String[] supportedProtocols) {
		return createSSLClient(keyStoreFilePath, keyStorePassword, null, null, trustStrategy, supportedProtocols);
	}

	/**
	 * 生成SSL的客户端可关闭的请求对象
	 * 
	 * @param keyStoreFilePath
	 *            密码保护文件路径
	 * @param keyStorePassword
	 *            密码保护文件解密密码,默认为null,即是没有密码
	 * @param keyStoreType
	 *            密码保护文件的类型,默认为Java keystore
	 * @param keyStoreProvider
	 *            密码保护文件的提供者,默认null
	 * @param trustStrategy
	 *            信任策略,默认只相信自己的CA和所有自签名的证书
	 * @param supportedProtocols
	 *            SSL要支持的协议
	 * @return 返回一个SSL的客户端可关闭的请求对象
	 */
	public static CloseableHttpClient createSSLClient(String keyStoreFilePath, String keyStorePassword, String keyStoreType, String keyStoreProvider, TrustStrategy trustStrategy, String[] supportedProtocols) {
		CloseableHttpClient httpclient = null;
		KeyStore trustStore = null;
		char[] passwordCharArr = (StringUtils.isNotBlank(keyStorePassword) ? keyStorePassword.toCharArray() : null);
		trustStrategy = (null == trustStrategy ? new TrustSelfSignedStrategy() : trustStrategy);
		supportedProtocols = ArrayUtils.isEmpty(supportedProtocols) ? new String[] { "TLSv1" } : supportedProtocols;
		try {
			if (StringUtils.isNotBlank(keyStoreFilePath)) {
				keyStoreType = (StringUtils.isNotBlank(keyStoreType) ? keyStoreType : KeyStore.getDefaultType());
				trustStore = StringUtils.isNotBlank(keyStoreProvider) ? KeyStore.getInstance(keyStoreType, keyStoreProvider) : KeyStore.getInstance(keyStoreType);
				FileInputStream instream = new FileInputStream(new File(keyStoreFilePath));
				try {
					// 加载keyStore
					trustStore.load(instream, passwordCharArr);
				} catch (CertificateException e) {
					e.printStackTrace();
				} finally {
					try {
						instream.close();
					} catch (Exception ignore) {
					}
				}
			}
			// 相信自己的CA和所有自签名的证书
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, trustStrategy).build();
			// 只允许使用TLSv1协议
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, supportedProtocols, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpclient;
	}

	/**
	 * 默认方式调用get访问指定地址并且获取数据,然后将数据返回
	 * 
	 * @param url
	 *            访问的地址
	 * @return 返回的响应数据
	 */
	public static UtilsHttpResponse get(String url) {
		return get(url, null);
	}

	/**
	 * 使用代理方式调用get访问指定地址并且获取数据,然后将数据返回
	 * 
	 * @param url
	 *            访问的地址
	 * @param proxyConfig
	 *            代理配置,可以通过getProxy方法获取
	 * @return 返回的响应数据
	 */
	public static UtilsHttpResponse get(String url, RequestConfig proxyConfig) {
		return get(null, url, proxyConfig);
	}

	/**
	 * 使用代理方式调用get访问指定地址并且返回的响应对象/请求客户端对象,这些响应对象和请求对象需要自己关闭
	 * 
	 * @param httpClient
	 *            指定的httpClient,可以是SSL的Client,提供灵活性
	 * @param url
	 *            访问的地址
	 * @param proxyConfig
	 *            代理配置,可以通过getProxy方法获取
	 * @return 返回的响应对象/请求客户端对象,这些响应对象和请求对象需要自己关闭
	 */
	public static Pair<CloseableHttpResponse, CloseableHttpClient> sourceGet(CloseableHttpClient httpClient, String url, RequestConfig proxyConfig) {
		CloseableHttpResponse httpResp = null;
		CloseableHttpClient client = null;
		if (!StringUtils.startsWithIgnoreCase(url, HTTP) && !StringUtils.startsWithIgnoreCase(url, HTTPS)) {
			url = HTTP + url;
		}
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			// HttpClient
			client = (null == httpClient ? httpClientBuilder.build() : httpClient);
			// 网页根目录下
			HttpGet httpGet = new HttpGet(url);
			// 依次是目标请求地址，端口号,协议类型
			HttpHost target = new HttpHost(url, 80, "http");
			if (null != proxyConfig) {
				httpGet.setConfig(proxyConfig);
				httpResp = client.execute(target, httpGet);
			} else {
				httpResp = client.execute(httpGet);
			}
			return new Pair<CloseableHttpResponse, CloseableHttpClient>(httpResp, client);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用代理方式调用get访问指定地址并且获取数据,然后将数据返回
	 * 
	 * @param httpClient
	 *            指定的httpClient,可以是SSL的Client,提供灵活性
	 * @param url
	 *            访问的地址
	 * @param proxyConfig
	 *            代理配置,可以通过getProxy方法获取
	 * @return 返回的响应数据
	 */
	public static UtilsHttpResponse get(CloseableHttpClient httpClient, String url, RequestConfig proxyConfig) {
		CloseableHttpResponse httpResp = null;
		CloseableHttpClient client = null;
		try {
			Pair<CloseableHttpResponse, CloseableHttpClient> sourceRespPair = sourceGet(httpClient, url, proxyConfig);
			httpResp = sourceRespPair.getFirst();
			client = sourceRespPair.getSecond();
			UtilsHttpResponse utilsResponse = new UtilsHttpResponse(httpResp.getAllHeaders(), EntityUtils.toByteArray(httpResp.getEntity()), httpResp.getLocale(), httpResp.getProtocolVersion(), httpResp.getStatusLine());
			return utilsResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != httpResp) {
				try {
					httpResp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != client) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 默认方式调用post访问指定地址并且获取数据,然后将数据返回
	 * 
	 * @param url
	 *            访问的地址
	 * @return 返回的数据
	 */
	public static UtilsHttpResponse post(String url) {
		return post(url, null, null);
	}

	/**
	 * 默认方式调用post访问指定地址并且获取数据,然后将数据返回
	 * 
	 * @param postValMap
	 *            post过去的参数
	 * @param url
	 *            访问的地址
	 * @return 返回的响应数据
	 */
	public static UtilsHttpResponse post(String url, Map<String, String[]> postValMap) {
		return post(url, postValMap, null);
	}

	/**
	 * 代理方式调用post访问指定地址并且获取数据,然后将数据返回
	 * 
	 * @param url
	 *            访问的地址
	 * @param postValMap
	 *            post过去的参数
	 * @param proxyConfig
	 *            代理配置,可以通过getProxy方法获取
	 * @return 返回的响应数据
	 */
	public static UtilsHttpResponse post(String url, Map<String, String[]> postValMap, RequestConfig proxyConfig) {
		return post(null, url, postValMap, proxyConfig);
	}

	/**
	 * 代理方式调用post访问指定地址返回的响应对象/请求客户端对象,这些响应对象和请求对象需要自己关闭
	 * 
	 * @param httpClient
	 *            指定的httpClient,可以是SSL的Client,提供灵活性
	 * @param postValMap
	 *            post过去的参数
	 * @param url
	 *            访问的地址
	 * @param proxyConfig
	 *            代理配置,可以通过getProxy方法获取
	 * @return 返回的响应对象/请求客户端对象,这些响应对象和请求对象需要自己关闭
	 */
	public static Pair<CloseableHttpResponse, CloseableHttpClient> sourcePost(CloseableHttpClient httpClient, String url, Map<String, String[]> postValMap, RequestConfig proxyConfig) {
		CloseableHttpResponse httpResp = null;
		CloseableHttpClient client = null;
		if (!StringUtils.startsWithIgnoreCase(url, HTTP) && !StringUtils.startsWithIgnoreCase(url, HTTPS)) {
			url = HTTP + url;
		}
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			// HttpClient
			client = (null == httpClient ? httpClientBuilder.build() : httpClient);
			// 网页根目录下
			HttpPost httPost = new HttpPost(url);
			// 依次是目标请求地址，端口号,协议类型
			HttpHost target = new HttpHost(url, 80, "http");
			// 遍历参数
			if (null != postValMap) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				Iterator<String> postValIt = postValMap.keySet().iterator();
				while (postValIt.hasNext()) {
					String paramName = postValIt.next();
					String[] vals = postValMap.get(paramName);
					if (ArrayUtils.isNotEmpty(vals)) {
						for (int i = 0, len = vals.length; i < len; i++) {
							paramList.add(new BasicNameValuePair(paramName, vals[i]));
						}
					}
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
				httPost.setEntity(entity);
			}
			if (null != proxyConfig) {
				httPost.setConfig(proxyConfig);
				httpResp = client.execute(target, httPost);
			} else {
				httpResp = client.execute(httPost);
			}
			return new Pair<CloseableHttpResponse, CloseableHttpClient>(httpResp, client);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 代理方式调用post访问指定地址并且获取数据,然后将数据返回
	 * 
	 * @param httpClient
	 *            指定的httpClient,可以是SSL的Client,提供灵活性
	 * @param postValMap
	 *            post过去的参数
	 * @param url
	 *            访问的地址
	 * @param proxyConfig
	 *            代理配置,可以通过getProxy方法获取
	 * @return 返回的响应数据
	 */
	public static UtilsHttpResponse post(CloseableHttpClient httpClient, String url, Map<String, String[]> postValMap, RequestConfig proxyConfig) {
		CloseableHttpResponse httpResp = null;
		CloseableHttpClient client = null;
		try {
			Pair<CloseableHttpResponse, CloseableHttpClient> sourceRespPair = sourcePost(httpClient, url, postValMap, proxyConfig);
			httpResp = sourceRespPair.getFirst();
			client = sourceRespPair.getSecond();
			UtilsHttpResponse utilsResponse = new UtilsHttpResponse(httpResp.getAllHeaders(), EntityUtils.toByteArray(httpResp.getEntity()), httpResp.getLocale(), httpResp.getProtocolVersion(), httpResp.getStatusLine());
			return utilsResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != httpResp) {
				try {
					httpResp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != client) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getExtendNameByContentType(String contentType) {
		String extendName = "";
		Iterator<Entry<String, String>> contentTypeIt = contentTypeMap.entrySet().iterator();
		while (contentTypeIt.hasNext()) {
			Entry<String, String> contentTypeEntry = contentTypeIt.next();
			String contentTypeT = contentTypeEntry.getValue();
			String extendNameT = contentTypeEntry.getKey();
			if (contentTypeT.equals(contentType)) {
				extendName = contentTypeMap.get(extendNameT);
				break;
			}
		}
		return extendName;
	}

	/**
	 * 根据指定的url对其执行下载操作
	 * 
	 * @param url
	 *            指定的地址
	 * @param postValMap
	 *            post过去的参数
	 * @param downloadFileSavePath
	 *            下载保存的文件路径,可以是整个文件的绝对路径,可以是目录;如果是目录的时候,baseFileName必须指定
	 * @param baseFileName
	 *            文件名,只包含文件名的,不包含扩展名的文件名
	 * @return 是否下载执行成功
	 */
	public static boolean toDownload(String url, Map<String, String[]> postValMap, String downloadFileSavePath, String baseFileName) {
		return toDownload(url, postValMap, downloadFileSavePath, baseFileName, false);
	}

	/**
	 * 根据指定的url对其执行下载操作
	 * 
	 * @param url
	 *            指定的地址
	 * @param downloadFileSavePath
	 *            下载保存的文件路径,可以是整个文件的绝对路径,可以是目录;如果是目录的时候,baseFileName必须指定
	 * @param baseFileName
	 *            文件名,只包含文件名的,不包含扩展名的文件名
	 * @return 是否下载执行成功
	 */
	public static boolean toDownload(String url, String downloadFileSavePath, String baseFileName) {
		return toDownload(url, null, downloadFileSavePath, baseFileName, true);
	}

	/**
	 * 根据指定的url对其执行下载操作
	 * 
	 * @param url
	 *            指定的地址
	 * @param postValMap
	 *            post过去的参数
	 * @param downloadFileSavePath
	 *            下载保存的文件路径,可以是整个文件的绝对路径,可以是目录;如果是目录的时候,baseFileName必须指定
	 * @param baseFileName
	 *            文件名,只包含文件名的,不包含扩展名的文件名
	 * @param getMethod
	 *            是否使用get方式请求
	 * @return 是否下载执行成功
	 */
	public static boolean toDownload(String url, Map<String, String[]> postValMap, String downloadFileSavePath, String baseFileName, boolean getMethod) {
		return toDownload(null, url, postValMap, null, downloadFileSavePath, baseFileName, getMethod);
	}

	/**
	 * 根据指定的url对其执行下载操作
	 * 
	 * @param httpClient
	 *            指定的httpClient,可以是SSL的Client,提供灵活性
	 * @param url
	 *            指定的地址
	 * @param postValMap
	 *            post过去的参数
	 * @param proxyConfig
	 *            代理配置,可以通过getProxy方法获取
	 * @param downloadFileSavePath
	 *            下载保存的文件路径,可以是整个文件的绝对路径,可以是目录;如果是目录的时候,baseFileName必须指定
	 * @param baseFileName
	 *            文件名,只包含文件名的,不包含扩展名的文件名
	 * @param getMethod
	 *            是否使用get方式请求
	 * @return 是否下载执行成功
	 */
	public static boolean toDownload(CloseableHttpClient httpClient, String url, Map<String, String[]> postValMap, RequestConfig proxyConfig, String downloadFileSavePath, String baseFileName, boolean getMethod) {
		CloseableHttpResponse httpResp = null;
		CloseableHttpClient client = null;
		BufferedOutputStream outputBuffer = null;
		BufferedInputStream contentBuffer = null;
		try {
			Pair<CloseableHttpResponse, CloseableHttpClient> sourceResp = sourceToDownload(httpClient, url, postValMap, proxyConfig, downloadFileSavePath, baseFileName, getMethod);
			if (null != sourceResp) {
				httpResp = sourceResp.getFirst();
				client = sourceResp.getSecond();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (null != httpResp) {
				try {
					httpResp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != client) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != outputBuffer) {
				try {
					outputBuffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != contentBuffer) {
				try {
					contentBuffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据指定的url对其执行下载操作
	 * 
	 * @param httpClient
	 *            指定的httpClient,可以是SSL的Client,提供灵活性
	 * @param url
	 *            指定的地址
	 * @param postValMap
	 *            post过去的参数
	 * @param proxyConfig
	 *            代理配置,可以通过getProxy方法获取
	 * @param downloadFileSavePath
	 *            下载保存的文件路径,可以是整个文件的绝对路径,可以是目录;如果是目录的时候,baseFileName必须指定
	 * @param baseFileName
	 *            文件名,只包含文件名的,不包含扩展名的文件名
	 * @param getMethod
	 *            是否使用get方式请求
	 * @return 是否下载执行成功
	 */
	public static Pair<CloseableHttpResponse, CloseableHttpClient> sourceToDownload(CloseableHttpClient httpClient, String url, Map<String, String[]> postValMap, RequestConfig proxyConfig, String downloadFileSavePath, String baseFileName, boolean getMethod) {
		CloseableHttpResponse httpResp = null;
		CloseableHttpClient client = null;
		BufferedOutputStream outputBuffer = null;
		BufferedInputStream contentBuffer = null;
		try {
			Pair<CloseableHttpResponse, CloseableHttpClient> sourceResp = null;
			if (getMethod) {
				sourceResp = sourceGet(httpClient, url, proxyConfig);
			} else {
				sourceResp = sourcePost(httpClient, url, postValMap, proxyConfig);
			}
			httpResp = sourceResp.getFirst();
			client = sourceResp.getSecond();
			HttpEntity entity = httpResp.getEntity();
			if (null != entity) {
				contentBuffer = new BufferedInputStream(entity.getContent());
				byte[] data = new byte[3 * 1024 * 1024];
				String extendName = "";
				Header contentTypeHeader = entity.getContentType();
				if (null != contentTypeHeader) {
					String contentType = contentTypeHeader.getValue();
					extendName = getExtendNameByContentType(contentType);
				}
				int readCount = 0;
				String baseName = FilenameUtils.getBaseName(downloadFileSavePath);
				boolean isFile = StringUtils.isNotBlank(baseName);
				File file = new File(downloadFileSavePath);
				if (isFile) {
					if (!file.exists()) {
						file.createNewFile();
					}
				} else {
					if (!file.exists()) {
						file.mkdirs();
					}
				}
				String realPath = (StringUtils.isNotBlank(baseName) ? downloadFileSavePath : (downloadFileSavePath + baseFileName + "." + extendName));
				File realPathFile = new File(realPath);
				if (!realPathFile.exists()) {
					file.createNewFile();
				}
				outputBuffer = new BufferedOutputStream(new FileOutputStream(realPathFile));
				while (-1 != (readCount = contentBuffer.read(data))) {
					outputBuffer.write(data, 0, readCount);
				}
				outputBuffer.flush();
				return new Pair<CloseableHttpResponse, CloseableHttpClient>(httpResp, client);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != outputBuffer) {
				try {
					outputBuffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != contentBuffer) {
				try {
					contentBuffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 触发文件下载，浏览器下载文件
	 * 
	 * @param response
	 *            请求响应对象
	 * @param filePath
	 *            要下载文件的路径(绝对路径)
	 * @param showName
	 *            下载时候显示的文件,要带上扩展名
	 * @return 是否成功触发下载
	 */
	public static boolean download(HttpServletResponse response, String filePath, String showName) {
		String contentType = ""; // response Content
		String extendName = "*";
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			if (StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				String fileName = file.getName();
				int index = fileName.lastIndexOf(".");// 获取扩展名
				if (index > 0) {
					extendName = fileName.substring(index + 1);
					extendName = extendName.toLowerCase().trim();
				}
				bis = new BufferedInputStream(new FileInputStream(file));
				if (null != bis) {
					int bytesA = bis.available();
					byte[] data = new byte[1024];
					// MIME类型
					contentType = ContentTypeConstants.getProperty(extendName);
					if (null == contentType) {
						contentType = contentTypeMap.get(extendName);
						contentType = (null == contentType ? "" : contentType);// application/*.*
					}
					response.setContentType(contentType);
					response.setContentLength(bytesA);
					bos = new BufferedOutputStream(response.getOutputStream());
					String titleName = new String((0 < showName.length() ? showName : ("download" + extendName)).getBytes("UTF-8"), "ISO8859-1");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + titleName + "\"");
					int bytesRead = 0;
					while (-1 != (bytesRead = bis.read(data))) {
						bos.write(data, 0, bytesRead);
					}
					bos.flush();
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Edity by bin.20140416 触发文件下载，浏览器下载文件
	 * 
	 * @param response
	 *            请求响应对象
	 * @param bytes
	 *            对应文件的二进制数据
	 * @param showName
	 *            下载时候显示的文件,要带上扩展名
	 * @return 是否成功触发下载
	 */
	public static boolean download(HttpServletResponse response, byte[] bytes, String showName) {
		String contentType = ""; // response Content
		String extendName = "*";
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			if (bytes != null) {
				int index = showName.lastIndexOf(".");// 获取扩展名
				if (index > 0) {
					extendName = showName.substring(index + 1);
					extendName = extendName.toLowerCase().trim();
				}
				bis = new BufferedInputStream(new ByteArrayInputStream(bytes));
				if (null != bis) {
					int bytesA = bis.available();
					byte[] data = new byte[1024];
					// MIME类型
					contentType = ContentTypeConstants.getProperty(extendName);
					if (null == contentType) {
						contentType = contentTypeMap.get(extendName);
						contentType = (null == contentType ? "" : contentType);// application/*.*
					}
					response.setContentType(contentType);
					response.setContentLength(bytesA);
					bos = new BufferedOutputStream(response.getOutputStream());
					String titleName = new String((0 < showName.length() ? showName : ("download" + extendName)).getBytes("UTF-8"), "ISO8859-1");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + titleName + "\"");
					int bytesRead = 0;
					while (-1 != (bytesRead = bis.read(data))) {
						bos.write(data, 0, bytesRead);
					}
					bos.flush();
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上传文件处理
	 * 
	 * @param request
	 *            请求对象，必须为MultipartHttpServletRequest实例，否则上传失败
	 * @param fileFieldName
	 *            对应表单的File元素的名称
	 * @param fileSavePath
	 *            文件保存的路径 ，必须为绝对路径，不需要带上文件名
	 * @return 返回UploadFile对象，对象包含基本上传文件信息
	 */
	public static UploadFile upload(HttpServletRequest request, String fileFieldName, String fileSavePath) {
		UploadFile uploadFile = null;
		MultipartHttpServletRequest multipartRequest = null;
		if (request instanceof MultipartHttpServletRequest) {
			multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile(fileFieldName);
			if (null != file && StringUtils.isNotBlank(fileSavePath)) {
				IWebFileService webFileService = SpringBeanManager.getBean(IWebFileService.class, WebFileServiceImpl.class);
				String originalFilename = file.getOriginalFilename();
				int lPointPos = originalFilename.lastIndexOf(".");
				String extendName = (lPointPos > 0 ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "");
				String fileName = UUID.randomUUID().toString().replace("-", "") + extendName;
				BufferedOutputStream bos = null;
				ByteArrayOutputStream baos = null;
				BufferedInputStream bis = null;
				try {
					File realFolder = new File(fileSavePath);
					if (!realFolder.exists()) {
						realFolder.mkdirs();
					}
					// 写出文件
					byte[] data = new byte[3 * 1024 * 1024];// 3M
					baos = new ByteArrayOutputStream();
					bis = new BufferedInputStream(file.getInputStream());
					File realFile = new File(realFolder, fileName);
					bos = new BufferedOutputStream(new FileOutputStream(realFile));
					int count = 0;
					while (-1 != (count = bis.read(data))) {
						baos.write(data, 0, count);// 用来计算md5
						bos.write(data, 0, count);
					}
					baos.flush();
					bos.flush();
					bos.close();
					String fileMd5 = null;
					// 写文件完成
					boolean md5Eq = false;
					fileMd5 = MD5Util.MD5(baos.toByteArray());
					if (null != webFileService) {
						// 验证md5，如果一样不保存文件
						TbWebFile md5WebFile = webFileService.findByMd5(fileMd5);
						if (null != md5WebFile) {// md5找到一样
							if (realFile.length() == md5WebFile.getSize().longValue()) {// 矫正不是因为文件过大造成误差
								md5Eq = true;
								uploadFile = new UploadFile(md5WebFile.getId(), originalFilename, fileName, md5WebFile.getSize().longValue(), extendName, realFile.getAbsolutePath(), realFile);
								try {
									if (realFile.exists()) {
										realFile.delete();// 删除多余的文件
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					if (!md5Eq) {
						TbWebFile webFile = null;
						try {
							if (null != webFileService) {
								String contextRealPath = HttpInternalObjectManager.getServletContext().getRealPath("/");
								String fileAbsolutePath = realFile.getAbsolutePath();
								String relativePath = fileAbsolutePath.replace(contextRealPath, "");
								relativePath = relativePath.replaceAll("\\\\", "/");
								relativePath = (relativePath.startsWith("/") ? relativePath : "/" + relativePath);
								WebFileDTO webFileDTO = new WebFileDTO(fileName, originalFilename, extendName, fileMd5, BigInteger.valueOf(realFile.length()), relativePath, request.getRequestURI(), HttpUtils.getIpAddr(request));
								TbUnit currentUnit = UserUtils.getCurrentUnit();
								webFileDTO.setOperatedUnitId(null != currentUnit ? currentUnit.getId() : null);
								webFile = webFileService.lSave(webFileDTO);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						uploadFile = new UploadFile((null != webFile ? webFile.getId() : null), originalFilename, fileName, realFile.length(), extendName, realFile.getAbsolutePath(), realFile);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (null != bis) {
							bis.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (null != bos) {
							bos.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (null != baos) {
							baos.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return uploadFile;
	}

	/**
	 * 上传文件处理
	 * 
	 * @param fileData
	 *            指定的数据
	 * @param fileSavePath
	 *            文件保存的路径 ，必须为绝对路径，不需要带上文件名
	 * @param originalFilename
	 *            原始文件名
	 * @return 返回UploadFile对象，对象包含基本上传文件信息
	 */
	public static UploadFile upload(byte[] fileData, String fileSavePath, String originalFilename) {
		UploadFile uploadFile = null;
		if (ArrayUtils.isNotEmpty(fileData) && StringUtils.isNotBlank(fileSavePath)) {
			IWebFileService webFileService = SpringBeanManager.getBean(IWebFileService.class, WebFileServiceImpl.class);
			int lPointPos = originalFilename.lastIndexOf(".");
			String extendName = (lPointPos > 0 ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "");
			String fileName = UUID.randomUUID().toString().replace("-", "") + extendName;
			BufferedOutputStream bos = null;
			ByteArrayOutputStream baos = null;
			try {
				File realFolder = new File(fileSavePath);
				if (!realFolder.exists()) {
					realFolder.mkdirs();
				}
				// 写出文件
				baos = new ByteArrayOutputStream();
				File realFile = new File(realFolder, fileName);
				bos = new BufferedOutputStream(new FileOutputStream(realFile));
				baos.write(fileData);// 用来计算md5
				bos.write(fileData);
				baos.flush();
				bos.flush();
				bos.close();
				String fileMd5 = null;
				// 写文件完成
				boolean md5Eq = false;
				fileMd5 = MD5Util.MD5(baos.toByteArray());
				if (null != webFileService) {
					// 验证md5，如果一样不保存文件
					TbWebFile md5WebFile = webFileService.findByMd5(fileMd5);
					if (null != md5WebFile) {// md5找到一样
						if (realFile.length() == md5WebFile.getSize().longValue()) {// 矫正不是因为文件过大造成误差
							md5Eq = true;
							uploadFile = new UploadFile(md5WebFile.getId(), originalFilename, fileName, md5WebFile.getSize().longValue(), extendName, realFile.getAbsolutePath(), realFile);
							try {
								if (realFile.exists()) {
									realFile.delete();// 删除多余的文件
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				if (!md5Eq) {
					TbWebFile webFile = null;
					try {
						if (null != webFileService) {
							String contextRealPath = HttpInternalObjectManager.getServletContext().getRealPath("/");
							String fileAbsolutePath = realFile.getAbsolutePath();
							String relativePath = fileAbsolutePath.replace(contextRealPath, "");
							relativePath = relativePath.replaceAll("\\\\", "/");
							relativePath = (relativePath.startsWith("/") ? relativePath : "/" + relativePath);
							HttpServletRequest currentRequest = HttpInternalObjectManager.getCurrentRequest();
							WebFileDTO webFileDTO = new WebFileDTO(fileName, originalFilename, extendName, fileMd5, BigInteger.valueOf(realFile.length()), relativePath, currentRequest.getRequestURI(), HttpUtils.getIpAddr(currentRequest));
							TbUnit currentUnit = UserUtils.getCurrentUnit();
							webFileDTO.setOperatedUnitId(null != currentUnit ? currentUnit.getId() : null);
							webFile = webFileService.lSave(webFileDTO);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					uploadFile = new UploadFile((null != webFile ? webFile.getId() : null), originalFilename, fileName, realFile.length(), extendName, realFile.getAbsolutePath(), realFile);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != bos) {
						bos.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (null != baos) {
						baos.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return uploadFile;
	}

	/**
	 * 获取对应参数的值，并使用URLDecoder以UTF-8格式转码。如果请求是以post方法提交的， 那么将通过HttpServletRequest::getParameter获取。
	 * 
	 * @param request
	 *            请求对象
	 * @param name
	 *            参数名称
	 * @return 参数值(多个则获取最后一个)
	 */
	public static String getQueryParam(HttpServletRequest request, String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		Map<String, String[]> queryParams = getQueryParams(request);
		String[] values = (null != queryParams ? queryParams.get(name) : null);
		if (values != null && values.length > 0) {
			return values[values.length - 1];
		} else {
			return null;
		}
	}

	/**
	 * 获取所有参数的键值队，并使用URLDecoder以UTF-8格式转码。如果请求是以post方法提交的， 那么将通过HttpServletRequest::getParameter获取
	 * 
	 * @param request
	 *            请求对象
	 * @return 一个map,[参数名/值数组]
	 */
	public static Map<String, String[]> getQueryParams(HttpServletRequest request) {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		if (CommonConstants.POST.equalsIgnoreCase(request.getMethod()) && (!request.getHeader("accept").contains("json"))) {
			paramMap = request.getParameterMap();
		} else {
			String queryStr = request.getQueryString();
			if (StringUtils.isBlank(queryStr)) {
				return new HashMap<String, String[]>();
			}
			try {
				queryStr = URLDecoder.decode(queryStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			paramMap = parseQueryString(queryStr);
		}
		return paramMap;
	}

	/**
	 * 
	 * Parses a query string passed from the client to the server and builds a <code>HashTable</code> object with key-value pairs. The query string should be in the form of a string packaged by the GET or POST method, that is, it should have key-value pairs in the form
	 * <i>key=value</i>, with each pair separated from the next by a &amp; character.
	 * 
	 * <p>
	 * A key can appear more than once in the query string with different values. However, the key appears only once in the hashtable, with its value being an array of strings containing the multiple values sent by the query string.
	 * 
	 * <p>
	 * The keys and values in the hashtable are stored in their decoded form, so any + characters are converted to spaces, and characters sent in hexadecimal notation (like <i>%xx</i>) are converted to ASCII characters.
	 * 
	 * @param queryStr
	 *            a string containing the query to be parsed
	 * 
	 * @return a <code>HashTable</code> object built from the parsed key-value pairs
	 * 
	 * @exception IllegalArgumentException
	 *                if the query string is invalid
	 * 
	 */
	public static Map<String, String[]> parseQueryString(String queryStr) {
		String valArray[] = null;
		if (queryStr == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String[]> ht = new HashMap<String, String[]>();
		StringTokenizer st = new StringTokenizer(queryStr, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				continue;
			}
			String key = pair.substring(0, pos);
			String val = pair.substring(pos + 1, pair.length());
			if (ht.containsKey(key)) {
				String oldVals[] = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++) {
					valArray[i] = oldVals[i];
				}
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}

	/**
	 * 获取指定前缀的参数的键值队
	 * 
	 * @param request
	 *            请求对象
	 * @param prefix
	 *            前缀
	 * @return 指定的键值队[参数名/参数值(多个时候","隔开)]
	 */
	public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix) {
		return getRequestMap(request, prefix, false);
	}

	/**
	 * 获取指定的参数的键值队
	 * 
	 * @param request
	 *            请求对象
	 * @return 指定的键值队[参数名/参数值(多个时候","隔开)]
	 */
	public static Map<String, String> getRequestMap(HttpServletRequest request) {
		return getRequestMap(request, "");
	}

	/**
	 * 获取指定前缀的参数的键值队,并在键值队中保留前缀名
	 * 
	 * @param request
	 *            请求对象
	 * @param prefix
	 *            前缀
	 * @return 指定的键值队[参数名/参数值(多个时候","隔开)]
	 */
	public static Map<String, String> getRequestMapWithPrefix(HttpServletRequest request, String prefix) {
		return getRequestMap(request, prefix, true);
	}

	/**
	 * 获取指定前缀的参数的键值队
	 * 
	 * @param request
	 *            请求对象
	 * @param prefix
	 *            前缀
	 * @param nameWithPrefix
	 *            键值队中是否保存前缀名
	 * @return 指定的键值队[参数名/参数值(多个时候","隔开)]
	 */
	protected static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		String name, key, value;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			if (name.startsWith(prefix)) {
				key = nameWithPrefix ? name : name.substring(prefix.length());
				value = StringUtils.join(request.getParameterValues(name), ',');
				map.put(key, value);
			}
		}
		return map;
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 *            请求对象
	 * @return 获取访问IP
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * 获得当的访问路径
	 * 
	 * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
	 * 
	 * @param request
	 *            请求对象
	 * @return 访问路径
	 */
	public static String getLocation(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		StringBuffer buff = request.getRequestURL();
		String uri = request.getRequestURI();
		String origUri = helper.getOriginatingRequestUri(request);
		buff.replace(buff.length() - uri.length(), buff.length(), origUri);
		String queryString = helper.getOriginatingQueryString(request);
		if (queryString != null) {
			buff.append("?").append(queryString);
		}
		return buff.toString();
	}

	/**
	 * 获得请求的session id，但是HttpServletRequest#getRequestedSessionId()方法有一些问题。 当存在部署路径的时候，会获取到根路径下的jsessionid。
	 * 
	 * @see HttpServletRequest#getRequestedSessionId()
	 * 
	 * @param request
	 *            请求对象
	 * @return session Id
	 */
	public static String getRequestedSessionId(HttpServletRequest request) {
		String sid = request.getRequestedSessionId();
		String ctx = request.getContextPath();
		// 如果session id是从url中获取，或者部署路径为空，那么是在正确的。
		if (request.isRequestedSessionIdFromURL() || StringUtils.isBlank(ctx)) {
			return sid;
		} else {
			// 手动从cookie获取
			Cookie cookie = getCookie(request, CommonConstants.JSESSION_COOKIE);
			if (cookie != null) {
				return cookie.getValue();
			} else {
				return null;
			}
		}

	}

	/**
	 * 发送文本。使用UTF-8编码。
	 * 
	 * @param response
	 *            响应对象
	 * @param text
	 *            发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 * 
	 * @param response
	 *            响应对象
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 * 
	 * @param response
	 *            响应对象
	 * @param text
	 *            发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * 
	 * @param response
	 *            响应对象
	 * @param contentType
	 *            内容类型
	 * @param text
	 *            发送的字符串
	 */
	public static void render(HttpServletResponse response, String contentType, String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得cookie
	 * 
	 * @param request
	 *            请求对象
	 * @param name
	 *            cookie名称
	 * @return 如果存在则返回cookies, 否则为空。
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * 根据部署路径，将cookie保存在根目录。
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param name
	 *            cookies名称
	 * @param value
	 *            cookies值
	 * @param expiry
	 *            超时间时间
	 * @param domain
	 *            域名
	 * @return 返回生成的cookies
	 */
	public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain) {
		Cookie cookie = new Cookie(name, value);
		if (expiry != null) {
			cookie.setMaxAge(expiry);
		}
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		response.addCookie(cookie);
		return cookie;
	}

	/**
	 * 取消cookie
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param name
	 *            cookies名称
	 * @param domain
	 *            域名
	 */
	public static void cancleCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * 复制http内置对象的Attribute(暂时只支持HttpSession/HttpServletRequest/HttpServletResponse类型内置对象的复制)
	 * 
	 * @param srcHttpInternalObject
	 *            源http内置对象
	 * @param desHttpInternalObject
	 *            目标http内置对象
	 * */
	public static void copyHttpInternalObjectAttribute(Object srcHttpInternalObject, Object desHttpInternalObject) {
		try {
			if (null != srcHttpInternalObject && null != desHttpInternalObject) {
				if ((srcHttpInternalObject instanceof HttpSession || srcHttpInternalObject instanceof HttpServletRequest || srcHttpInternalObject instanceof HttpServletResponse)
						&& (desHttpInternalObject instanceof HttpSession || desHttpInternalObject instanceof HttpServletRequest || desHttpInternalObject instanceof HttpServletResponse)) {
					Enumeration<?> attributeNames = (Enumeration<?>) AfxBeanUtils.invokeMethod(srcHttpInternalObject, "getAttributeNames", new Object[0], new Class<?>[] {});
					while (attributeNames.hasMoreElements()) {
						Object name = attributeNames.nextElement();
						Object val = AfxBeanUtils.invokeMethod(srcHttpInternalObject, "getAttribute", new Object[] { name }, new Class<?>[] { String.class });
						AfxBeanUtils.invokeMethod(desHttpInternalObject, "setAttribute", new Object[] { name, val }, new Class<?>[] { String.class, Object.class });
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		// final RequestConfig proxy = getProxy("127.0.0.1", 8087, "http");
		// Map<String, String[]> paramMap = new HashMap<String, String[]>();
		// Pair<CloseableHttpResponse, CloseableHttpClient> respSI = sourceGet(null, "http://www.xjfair.com/index.php?m=admin&c=index&a=login&pc_hash=qO4prC", proxy);
		// System.out.println(new String((EntityUtils.toByteArray(respSI.getFirst().getEntity())), "UTF-8"));
		// respSI.getFirst().close();
		// paramMap.put("username", new String[] { "admin" });
		// paramMap.put("password", new String[] { "admin123" });
		// BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		// String inp = "";
		// String code = "";
		// Pair<CloseableHttpResponse, CloseableHttpClient> respSII = sourceToDownload(respSI.getSecond(), "http://www.xjfair.com/api.php?op=checkcode&code_len=4&font_size=20&width=130&height=50&font_color=&background=", null, proxy, "C://img.jpg", "", true);
		// System.out.println("輸入驗證碼:/r/n");
		// while (null != (inp = buffer.readLine())) {
		// if ("quit".equals(inp)) {
		// break;
		// } else {
		// code = inp;
		// }
		// }
		// respSII.getFirst().close();
		// paramMap.put("code", new String[] { code });
		// Pair<CloseableHttpResponse, CloseableHttpClient> respS = sourcePost(respSI.getSecond(), "http://www.xjfair.com/index.php?m=admin&c=index&a=login&dosubmit=1", paramMap, proxy);
		// CloseableHttpResponse resp = respS.getFirst();
		// try {
		// System.out.println(((resp.getEntity().getContentType())));
		// System.out.println(((resp.getLocale())));
		// System.out.println(((resp.getProtocolVersion())));
		// System.out.println(((resp.getStatusLine())));
		// System.out.println(new String((EntityUtils.toByteArray(resp.getEntity())), "UTF-8"));
		// final CloseableHttpClient second = respSI.getSecond();
		// Thread t1 = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// CloseableHttpResponse resp = null;
		// CloseableHttpClient hc = null;
		// try {
		// while (true) {
		// // try {
		// // Thread.sleep(500);
		// // } catch (InterruptedException e1) {
		// // e1.printStackTrace();
		// // }
		// Pair<CloseableHttpResponse, CloseableHttpClient> respS = null;
		// try {
		// respS = sourceGet(second, "http://www.xjfair.com/index.php?m=admin&c=index&a=public_current_pos&menuid=905", proxy);
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// if (null != respS) {
		// resp = respS.getFirst();
		// hc = respS.getSecond();
		// System.out.println(("t1:" + (resp.getEntity().getContentType())));
		// System.out.println(("t1:" + (resp.getLocale())));
		// System.out.println(("t1:" + (resp.getProtocolVersion())));
		// System.out.println(("t1:" + (resp.getStatusLine())));
		// try {
		// System.out.println("t1:" + new String((EntityUtils.toByteArray(resp.getEntity())), "UTF-8"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (null != resp) {
		// try {
		// resp.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// if (200 != resp.getStatusLine().getStatusCode()) {
		// System.out.println(resp.getStatusLine().getStatusCode());
		// }
		// } else {
		// System.out.println("出错!");
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (null != resp) {
		// try {
		// resp.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// if (null != hc) {
		// try {
		// hc.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }
		// });
		// Thread t2 = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// CloseableHttpResponse resp = null;
		// CloseableHttpClient hc = null;
		// try {
		// while (true) {
		// // try {
		// // Thread.sleep(500);
		// // } catch (InterruptedException e1) {
		// // e1.printStackTrace();
		// // }
		// Pair<CloseableHttpResponse, CloseableHttpClient> respS = null;
		// try {
		// respS = sourceGet(second, "http://www.xjfair.com/index.php?m=admin&c=index&a=public_current_pos&menuid=905", null);
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// if (null != respS) {
		// resp = respS.getFirst();
		// hc = respS.getSecond();
		// System.out.println(("t2:" + (resp.getEntity().getContentType())));
		// System.out.println(("t2:" + (resp.getLocale())));
		// System.out.println(("t2:" + (resp.getProtocolVersion())));
		// System.out.println(("t2:" + (resp.getStatusLine())));
		// try {
		// System.out.println("t2:" + new String((EntityUtils.toByteArray(resp.getEntity())), "UTF-8"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (null != resp) {
		// try {
		// resp.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// if (200 != resp.getStatusLine().getStatusCode()) {
		// System.out.println(resp.getStatusLine().getStatusCode());
		// }
		// } else {
		// System.out.println("出错!");
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (null != resp) {
		// try {
		// resp.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// if (null != hc) {
		// try {
		// hc.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }
		// });
		// t1.start();
		// t2.start();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
}
