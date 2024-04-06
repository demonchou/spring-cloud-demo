package cn.com.stori.account.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 程序主入口
 * @author hzzhouhongfei
 * @version $$ AccountLimitSpringBootApplication, 2024/3/21 9:26 PM hzzhouhongfei $$
 */
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = { "cn.com.stori.account" })
public class AccountLimitSpringBootApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(AccountLimitSpringBootApplication.class, args);
	}

}
