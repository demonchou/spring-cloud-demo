package org.captain.chou.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
//开启feign
@EnableFeignClients
@SpringBootApplication
public class ServiceConsumerApplication
{

//	负载均衡策略
//	@Bean
//	public RandomRule randomRule()
//	{
//		return new RandomRule();
//	}

	public static void main(String[] args)
	{
		SpringApplication.run(ServiceConsumerApplication.class, args);
	}
}
