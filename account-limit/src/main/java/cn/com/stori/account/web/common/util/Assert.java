package cn.com.stori.account.web.common.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import cn.com.stori.account.web.common.errorcode.ErrorCodeEnum;
import cn.com.stori.account.web.common.exception.BusinessException;

/**
 * 自定义断言。引入自定义业务异常（BusinessException）
 * @author hzzhouhongfei
 * @version $$ Assert, 2024/3/25 6:07 PM hzzhouhongfei $$
 */
public class Assert extends org.springframework.util.Assert
{
	public static void isTrue(boolean expression, ErrorCodeEnum errorCode, String message)
	{
		if (!expression)
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void isTrue(boolean expression, ErrorCodeEnum errorCode)
	{
		isTrue(expression, errorCode, errorCode.getDesc());
	}

	public static void isNull(Object object, ErrorCodeEnum errorCode, String message)
	{
		if (object != null)
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void isNull(Object object, ErrorCodeEnum errorCode)
	{
		isNull(object, errorCode, errorCode.getDesc());
	}

	public static void notNull(Object object, ErrorCodeEnum errorCode, String message)
	{
		if (object == null)
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void notNull(Object object, ErrorCodeEnum errorCode)
	{
		notNull(object, errorCode, errorCode.getDesc());
	}

	public static void hasLength(String text, ErrorCodeEnum errorCode, String message)
	{
		if (!StringUtils.hasLength(text))
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void hasLength(String text, ErrorCodeEnum errorCode)
	{
		hasLength(text, errorCode, errorCode.getDesc());
	}

	public static void hasText(String text, ErrorCodeEnum errorCode, String message)
	{
		if (!StringUtils.hasText(text))
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void hasText(String text, ErrorCodeEnum errorCode)
	{
		hasText(text, errorCode, errorCode.getDesc());
	}

	public static void doesNotContain(String textToSearch, String substring, ErrorCodeEnum errorCode, String message)
	{
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring))
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void doesNotContain(String textToSearch, String substring, ErrorCodeEnum errorCode)
	{
		doesNotContain(textToSearch, substring, errorCode, errorCode.getDesc());
	}

	public static void notEmpty(Object[] array, ErrorCodeEnum errorCode, String message)
	{
		if (ObjectUtils.isEmpty(array))
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void notEmpty(Object[] array, ErrorCodeEnum errorCode)
	{
		notEmpty(array, errorCode, errorCode.getDesc());
	}

	public static void noNullElements(Object[] array, ErrorCodeEnum errorCode, String message)
	{
		if (array != null)
		{
			for (Object element : array)
			{
				if (element == null)
				{
					throw new BusinessException(errorCode, message);
				}
			}
		}

	}

	public static void noNullElements(Object[] array, ErrorCodeEnum errorCode)
	{
		noNullElements(array, errorCode, errorCode.getDesc());
	}

	public static void notEmpty(Collection<?> collection, ErrorCodeEnum errorCode, String message)
	{
		if (CollectionUtils.isEmpty(collection))
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void notEmpty(Collection<?> collection, ErrorCodeEnum errorCode)
	{
		notEmpty(collection, errorCode, errorCode.getDesc());
	}

	public static void notEmpty(Map<?, ?> map, ErrorCodeEnum errorCode, String message)
	{
		if (CollectionUtils.isEmpty(map))
		{
			throw new BusinessException(errorCode, message);
		}
	}

	public static void notEmpty(Map<?, ?> map, ErrorCodeEnum errorCode)
	{
		notEmpty(map, errorCode, errorCode.getDesc());
	}

	public static void isInstanceOf(Class<?> clazz, Object obj, ErrorCodeEnum errorCode)
	{
		isInstanceOf(clazz, obj, errorCode, errorCode.getDesc());
	}

	public static void isInstanceOf(Class<?> type, Object obj, ErrorCodeEnum errorCode, String message)
	{
		notNull(type, errorCode, "Type to check against must not be null");
		if (!type.isInstance(obj))
		{
			throw new BusinessException(errorCode,
					(StringUtils.hasLength(message) ? message + " " : "") + "Object of class ["
							+ (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
		}
	}

	public static void isAssignable(Class<?> superType, Class<?> subType, ErrorCodeEnum errorCode)
	{
		isAssignable(superType, subType, errorCode, "");
	}

	public static void isAssignable(Class<?> superType, Class<?> subType, ErrorCodeEnum errorCode, String message)
	{
		notNull(superType, errorCode, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType))
		{
			throw new BusinessException(errorCode, message + subType + " is not assignable to " + superType);
		}
	}
}
