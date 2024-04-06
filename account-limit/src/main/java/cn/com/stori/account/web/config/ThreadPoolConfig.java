package cn.com.stori.account.web.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * 自定义线程池
 * @author hzzhouhongfei
 * @version $$ ThreadPoolConfig, 2024/3/25 11:01 AM hzzhouhongfei $$
 */
@Configuration
public class ThreadPoolConfig
{
	@Bean("customThreadPool")
	public Executor customThreadPool()
	{
		ThreadFactory threadFactory = new CustomizableThreadFactory("自定义线程池-");
		ThreadPoolExecutor threadPoolExecutor =
				new ThreadPoolExecutor(4, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), threadFactory);
		return threadPoolExecutor;
	}
}
