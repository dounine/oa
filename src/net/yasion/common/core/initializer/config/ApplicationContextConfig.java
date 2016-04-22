package net.yasion.common.core.initializer.config;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import net.yasion.common.utils.WebContextUtils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "net.yasion", excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class, /* 避免重复加载 START */Configuration.class /* 避免重复加载 END */}) })
public class ApplicationContextConfig {

	private static Properties webConfigParams = new Properties();

	// 静态初始化读入config.properties中的设置
	static {
		String propertyFileName = WebContextUtils.getWebContextRealPath("WEB-INF/config/properties/jdbc/mysql.properties");
		InputStream in = null;
		try {
			in = new FileInputStream(propertyFileName);
			if (in != null) {
				webConfigParams.load(in);
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

	public static Properties getWebConfigParams() {
		return webConfigParams;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(webConfigParams.getProperty("datasource.driverClassName"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setJdbcUrl(webConfigParams.getProperty("datasource.url"));
		dataSource.setUser(webConfigParams.getProperty("datasource.username"));
		dataSource.setPassword(webConfigParams.getProperty("datasource.password"));
		dataSource.setMinPoolSize(NumberUtils.toInt(webConfigParams.getProperty("c3p0.minPoolSize")));
		dataSource.setMaxPoolSize(NumberUtils.toInt(webConfigParams.getProperty("c3p0.maxPoolSize")));
		dataSource.setInitialPoolSize(NumberUtils.toInt(webConfigParams.getProperty("c3p0.initialPoolSize")));
		dataSource.setMaxIdleTime(NumberUtils.toInt(webConfigParams.getProperty("c3p0.maxIdleTime")));
		dataSource.setAcquireIncrement(NumberUtils.toInt(webConfigParams.getProperty("c3p0.acquireIncrement")));
		dataSource.setIdleConnectionTestPeriod(NumberUtils.toInt(webConfigParams.getProperty("c3p0.idleConnectionTestPeriod")));
		dataSource.setMaxStatements(NumberUtils.toInt(webConfigParams.getProperty("c3p0.maxStatements")));
		dataSource.setNumHelperThreads(NumberUtils.toInt(webConfigParams.getProperty("c3p0.numHelperThreads")));
		dataSource.setCheckoutTimeout(NumberUtils.toInt(webConfigParams.getProperty("c3p0.checkoutTimeout")));
		dataSource.setAutoCommitOnClose(BooleanUtils.toBoolean(webConfigParams.getProperty("c3p0.autoCommitOnClose")));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(this.dataSource());
		sessionFactoryBean.setMappingDirectoryLocations(new org.springframework.core.io.Resource[] { new FileSystemResource(WebContextUtils.getWebContextRealPath("WEB-INF/config/hibernate/mapping")) });
		Properties prop = new Properties();
		prop.setProperty("hibernate.dialect", webConfigParams.getProperty("hibernate.dialect"));
		prop.setProperty("hibernate.show_sql", webConfigParams.getProperty("hibernate.show_sql"));
		prop.setProperty("hibernate.connection.release_mode", webConfigParams.getProperty("hibernate.connection.release_mode"));
		prop.setProperty("hibernate.jdbc.fetch_size", webConfigParams.getProperty("hibernate.jdbc.fetch_size"));
		prop.setProperty("hibernate.jdbc.batch_size", webConfigParams.getProperty("hibernate.jdbc.batch_size"));
		prop.setProperty("hibernate.generate_statistics", webConfigParams.getProperty("hibernate.generate_statistics"));
		prop.setProperty("hibernate.cache.use_second_level_cache", webConfigParams.getProperty("hibernate.cache.use_second_level_cache"));
		prop.setProperty("hibernate.cache.use_query_cache", webConfigParams.getProperty("hibernate.cache.use_query_cache"));
		prop.setProperty("hibernate.cache.region.factory_class", webConfigParams.getProperty("hibernate.cache.region.factory_class"));
		prop.setProperty("net.sf.ehcache.configurationResourceName", webConfigParams.getProperty("net.sf.ehcache.configurationResourceName"));
		prop.setProperty("hibernate.connection.autocommit", webConfigParams.getProperty("hibernate.connection.autocommit"));
		// 当hibernate.hbm2ddl.auto为非none时候，并且MySQL版本是5.0以上
		// 需要把hibernate.dialect设置为org.hibernate.dialect.MySQL5Dialect，否则会无法创建表
		prop.setProperty("hibernate.hbm2ddl.auto", webConfigParams.getProperty("hibernate.hbm2ddl.auto"));
		sessionFactoryBean.setHibernateProperties(prop);
		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager hiberTransMana = new HibernateTransactionManager();
		hiberTransMana.setSessionFactory(sessionFactory().getObject());
		return hiberTransMana;
	}
}