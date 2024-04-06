package cn.com.stori.account.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;

/**
 * 调度任务线程池配置
 * @author hzzhouhongfei
 * @version $$ SchedulingConfig, 2024/3/25 8:11 PM hzzhouhongfei $$
 */
@Slf4j
@Configuration
public class SchedulingConfig {

	@Bean(destroyMethod = "shutdown")
	public ThreadPoolTaskScheduler taskScheduler()
	{
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(10);
		scheduler.setThreadNamePrefix("额度管理调度任务-");
		log.info("== 指定任务执行的线程池初始化完成 ===");
		return scheduler;
	}
}
