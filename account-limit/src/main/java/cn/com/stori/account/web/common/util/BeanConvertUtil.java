package cn.com.stori.account.web.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeanUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * bean的转换工具
 * @author hzzhouhongfei
 * @version $$ BeanConvertUtil, 2024/3/25 10:19 PM hzzhouhongfei $$
 */
@Slf4j
public class BeanConvertUtil
{
	/**
	 * 把源对象中的所有属性，复制到目标对象中
	 * @param source
	 * @param target
	 */
	public static void convertBean(Object source, Object target)
	{
		BeanUtils.copyProperties(source, target);
	}

	/**
	 * 把源对象中的所有属性，复制到目标对象中
	 * @param sources
	 * @param targets
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void convertBeanList(List sources, List targets, Class targetClass)
	{
		if (sources == null)
		{
			return;
		}
		Validate.notNull(targets);
		try
		{
			for (int i = 0; i < sources.size(); i++)
			{
				Object targetObj = targetClass.newInstance();
				BeanUtils.copyProperties(sources.get(i), targetObj);
				targets.add(targetObj);
			}
		}
		catch (InstantiationException e1)
		{
			log.error(targetClass.getName() + "类没有无参构造器", e1);
		}
		catch (IllegalAccessException e2)
		{
			log.error(targetClass.getName() + "类实例化异常", e2);
		}
	}

	/**
	 * 把源对象中的所有属性，复制到目标对象中
	 * @param source
	 * @param targetClass
	 * @return
	 */
	public static <T> T convertBean(Object source, Class<T> targetClass)
	{
		T target = null;
		if (source == null)
		{
			return target;
		}
		try
		{
			target = targetClass.newInstance();
			BeanUtils.copyProperties(source, target);
		}
		catch (InstantiationException e)
		{
			log.error(targetClass.getName() + "类没有无参构造器", e);
		}
		catch (IllegalAccessException e)
		{
			log.error(targetClass.getName() + "类实例化异常", e);
		}
		return target;
	}

	public static <T> List<T> convertBeanList(List<?> sources, Class<T> targetClass)
	{
		if (sources == null)
		{
			return null;
		}
		List<T> targets = new ArrayList<>();
		try
		{
			for (int i = 0; i < sources.size(); i++)
			{
				Object targetObj = targetClass.newInstance();
				BeanUtils.copyProperties(sources.get(i), targetObj);
				targets.add((T)targetObj);
			}
		}
		catch (InstantiationException e1)
		{
			log.error(targetClass.getName() + "类没有无参构造器", e1);
		}
		catch (IllegalAccessException e2)
		{
			log.error(targetClass.getName() + "类实例化异常", e2);
		}
		return targets;
	}
}
