package cn.com.stori.account.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.com.stori.account.web.common.util.PrivacyEncryptionUtil;
import cn.com.stori.account.web.common.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统用到的一些初始化bean
 * @author hzzhouhongfei
 * @version $$ InitBeanConfig, 2024/3/24 7:13 PM hzzhouhongfei $$
 */
@Configuration
@Slf4j
public class InitBeanConfig
{
	@Bean(name = "privacyEncryptionUtil", initMethod = "init")
	public PrivacyEncryptionUtil privacyEncryptionUtil()
	{
		log.info("额度管理系统PrivacyEncryptionUtil初始化");
		return new PrivacyEncryptionUtil();
	}

	@Bean
	public SpringContextUtil getSpringContextHolder()
	{
		return new SpringContextUtil();
	}
}
