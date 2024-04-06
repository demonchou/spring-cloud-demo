package cn.com.stori.account.web.config;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import cn.com.stori.account.web.common.constant.SystemName;
import cn.com.stori.account.web.common.util.PrivacyEncryptionUtil;
import cn.com.stori.account.web.common.util.ResolverResourcesUtil;
import cn.com.stori.account.web.module.service.impl.SysSequenceServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据源配置
 * @author hzzhouhongfei
 * @version $$ DatasourceConfig, 2024/3/24 6:18 PM hzzhouhongfei $$
 */
@Slf4j
@Configuration
@MapperScan(basePackages = { "cn.com.stori.account.web.module.domain.mapper"})
public class DatasourceConfig
{
	private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

	@Value("${jdbc-stori-accting-mysql.user}")
	private String username;

	@Value("${jdbc-stori-accting-mysql.encryptPassword}")
	private String encryptPassword;

	@Value("${jdbc-stori-accting-mysql.druid.driver-url}")
	private String url;

	@Value("${jdbc-stori-accting-mysql.druid.initialSize}")
	private int initialSize;

	@Value("${jdbc-stori-accting-mysql.druid.minIdle}")
	private int minIdle;

	@Value("${jdbc-stori-accting-mysql.druid.maxWait}")
	private long maxWait;

	@Value("${jdbc-stori-accting-mysql.druid.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;

	@Value("${jdbc-stori-accting-mysql.druid.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis;

	@Value("${jdbc-stori-accting-mysql.druid.validationQuery}")
	private String validationQuery;

	@Value("${jdbc-stori-accting-mysql.druid.maxEvictableIdleTimeMillis:1800000}")
	private long maxEvictableIdleTimeMillis;

	@Value("${jdbc-stori-accting-mysql.druid.maxOpenPreparedStatements:100}")
	private int maxOpenPreparedStatements;

	@Value("${jdbc-stori-accting-mysql.druid.maxPoolPreparedStatementPerConnectionSize:100}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Bean(name = "mysqlDataSource", initMethod = "init", destroyMethod = "close")
	public DruidDataSource mysqlDataSource()
	{
		DruidDataSource dataSource = new DruidDataSource();
		log.info("mysqlDataSource 数据源连接信息，url：{}，name：{}", url, username);
		dataSource.setDriverClassName(MYSQL_JDBC_DRIVER);
		dataSource.setUsername(username);

		//国密解密配置中心的密文密码并设置
		dataSource.setPassword(sm4Decrypt(encryptPassword));

		dataSource.setUrl(url);
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxWait(maxWait);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
		dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

		try
		{
			dataSource.setFilters("stat");
		}
		catch (SQLException e)
		{
			log.error("【E0】额度管理系统 mysqlDataSource druid filter 配置失败，连接url：{}，异常原因:{}", dataSource.getUrl(),
					e.getMessage(), e);
		}

		log.info("==== 【mysqlDataSource】数据源初始化完成 ====");

		return dataSource;
	}

	@Bean(name = "sqlSessionFactory")
	SqlSessionFactory sqlSessionFactory() throws Exception
	{
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		// mybatis个性化配置
		sqlSessionFactory.setConfigLocation(
				new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
		// 指定数据源
		sqlSessionFactory.setDataSource(mysqlDataSource());
		// 实体类映射地址
		sqlSessionFactory.setTypeAliasesPackage(
				"cn.com.stori.account.web.module.domain.entity");
		// mapper.xml 路径

		sqlSessionFactory
				.setMapperLocations(ResolverResourcesUtil.resolverResources("classpath*:mybatis/*.xml"));
		return sqlSessionFactory.getObject();
	}

	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception
	{
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager()
	{
		return new DataSourceTransactionManager(mysqlDataSource());
	}

	private String sm4Decrypt(String cipherText)
	{
		return PrivacyEncryptionUtil.sm4DecryptEcb(cipherText, SystemName.ACCOUNT_LIMIT.getCode());
	}

	@Bean
	public SysSequenceServiceImpl sysSequenceService(
			@Qualifier("transactionManager") DataSourceTransactionManager dataSourceTransactionManager)
	{

		SysSequenceServiceImpl sysSequenceService = new SysSequenceServiceImpl();
		sysSequenceService.setSequenceName("STORI_ACC_SEQ");
		sysSequenceService.setTransactionManager(dataSourceTransactionManager);
		log.info("STORI_ACC_SEQ 序列加载完成");
		return sysSequenceService;
	}
}
