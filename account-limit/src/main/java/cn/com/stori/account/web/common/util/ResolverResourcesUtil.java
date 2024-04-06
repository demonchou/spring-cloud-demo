package cn.com.stori.account.web.common.util;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 资源解析器工具类
 * @author hzzhouhongfei
 * @version $$ ResolverResourcesUtil, 2024/3/24 10:11 PM hzzhouhongfei $$
 */
public class ResolverResourcesUtil
{
	private static final PathMatchingResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();


	public static Resource[] resolverResources(String... resources)
	{
		return Stream.of(resources).flatMap(resource -> Stream.of(getResources(resource))).toArray(Resource[]::new);
	}

	public static Resource[] getResources(String location)
	{
		try
		{
			return RESOLVER.getResources(location);
		}
		catch (IOException e)
		{
			return new Resource[0];
		}
	}
}
