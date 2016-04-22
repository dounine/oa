package net.yasion.common.core.cache.ehcache.facotry;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.util.ClassLoaderUtil;
import net.yasion.common.utils.WebContextUtils;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.ehcache.EhCacheMessageLogger;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.cache.ehcache.internal.util.HibernateEhcacheUtils;
import org.hibernate.cfg.Settings;
import org.jboss.logging.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * 用于Web的WebEhCacheRegionFactory。可以基于Web根目录指定diskStore地址。
 */
public class WebEhCacheRegionFactory extends EhCacheRegionFactory {

	private static final long serialVersionUID = 6050253323675989479L;

	private static final EhCacheMessageLogger LOG = Logger.getMessageLogger(EhCacheMessageLogger.class, WebEhCacheRegionFactory.class.getName());

	@Override
	protected URL loadResource(String configurationResourceName) {
		URL url = null;
		final ClassLoader standardClassloader = ClassLoaderUtil.getStandardClassLoader();
		if (standardClassloader != null) {
			url = standardClassloader.getResource(configurationResourceName);
		}
		if (url == null) {
			url = WebEhCacheRegionFactory.class.getResource(configurationResourceName);
		}
		if (url == null) {
			try {
				url = new URL(configurationResourceName);
			} catch (MalformedURLException e) {
				// ignore
			}
		}
		if (null == url) {
			String configurationResourceRealPath = WebContextUtils.getWebContextRealPath(configurationResourceName);
			try {
				Resource resource = new FileSystemResource(configurationResourceRealPath);
				url = resource.getURL();
			} catch (MalformedURLException e) {
				// ignore
			} catch (IOException e) {
				// ignore
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debugf("Creating EhCacheRegionFactory from a specified resource: %s.  Resolved to URL: %s", configurationResourceName, url);
		}
		if (url == null) {
			LOG.unableToLoadConfiguration(configurationResourceName);
		}
		return url;
	}

	@Override
	public void start(Settings settings, Properties properties) throws CacheException {
		this.settings = settings;
		if (manager != null) {
			LOG.attemptToRestartAlreadyStartedEhCacheProvider();
			return;
		}
		try {
			String configurationResourceName = null;
			if (properties != null) {
				configurationResourceName = (String) properties.get(NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME);
			}
			if (configurationResourceName == null || configurationResourceName.length() == 0) {
				final Configuration configuration = ConfigurationFactory.parseConfiguration();
				manager = new CacheManager(configuration);
			} else {
				final URL url = loadResource(configurationResourceName);
				final Configuration configuration = HibernateEhcacheUtils.loadAndCorrectConfiguration(url);
				DiskStoreConfiguration diskStoreConfiguration = configuration.getDiskStoreConfiguration();
				String path = diskStoreConfiguration.getPath();
				if (WebContextUtils.isWebContextPath(path)) {
					String diskStoreRealPath = WebContextUtils.getWebContextRealPath(path);
					diskStoreConfiguration.setPath(diskStoreRealPath);
				}
				manager = new CacheManager(configuration);
			}
			mbeanRegistrationHelper.registerMBean(manager, properties);
		} catch (net.sf.ehcache.CacheException e) {
			if (e.getMessage().startsWith("Cannot parseConfiguration CacheManager. Attempt to create a new instance of " + "CacheManager using the diskStorePath")) {
				throw new CacheException("Attempt to restart an already started EhCacheRegionFactory. " + "Use sessionFactory.close() between repeated calls to buildSessionFactory. " + "Consider using SingletonEhCacheRegionFactory. Error from ehcache was: " + e.getMessage());
			} else {
				throw new CacheException(e);
			}
		}
	}
}