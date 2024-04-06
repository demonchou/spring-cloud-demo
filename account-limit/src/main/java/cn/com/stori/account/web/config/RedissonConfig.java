package cn.com.stori.account.web.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Redisson配置
 * @author hzzhouhongfei
 * @version $$ RedissonConfig, 2024/3/25 11:54 PM hzzhouhongfei $$
 */
@Slf4j
@Configuration
public class RedissonConfig
{

	@Value("${redis-config-address}")
	private String redisAddress;
	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient()
	{
		Config config = new Config();

		log.info("== Redisson开始配置===");

		// 单节点配置。此处没有设置密码，如果需要设置密码，需要解密从配置中心拉取回来的加密后的密码
		config.useSingleServer().setAddress(redisAddress);
		return Redisson.create(config);
	}

}
