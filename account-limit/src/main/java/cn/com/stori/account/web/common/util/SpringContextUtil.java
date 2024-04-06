package cn.com.stori.account.web.common.util;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring上下文工具类
 * @author hzzhouhongfei
 * @version $$ SpringContextUtil, 2024/3/24 5:45 PM hzzhouhongfei $$
 */
@Slf4j
@Service("SpringContextUtil")
public class SpringContextUtil implements ApplicationContextAware
{
	/**
	 * 以静态变量保存ApplicationContext,可在任意代码中取出ApplicationContext.
	 */
	private static ApplicationContext context;

	/**
	 * 获取容器
	 *
	 */
	public static ApplicationContext getApplicationContext()
	{
		return context;
	}

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 * @param context
	 */
	@Override
	public void setApplicationContext(ApplicationContext context)
	{
		SpringContextUtil.context = context;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * @param name
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name)
	{
		try
		{
			return (T) context.getBean(name);
		}
		catch (NoSuchBeanDefinitionException e)
		{
			log.error("【E0】获取Bean实例异常，Bean名称：{}，异常原因：{}", name, e.getMessage(), e);
			return null;
		}
	}

	public static <T> T getBean(Class<T> beanClass)
	{
		try
		{
			return (T) context.getBean(beanClass);
		}
		catch (NoSuchBeanDefinitionException e)
		{
			log.error("【E0】获取Bean实例异常，Bean名称：{}，异常原因：{}", beanClass, e.getMessage(), e);
			return null;
		}
	}
}
