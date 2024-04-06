package cn.com.stori.account.web.component.databease.redis.helper;

import cn.com.stori.account.web.common.constant.CommonConstant;

/**
 * redis的key工具类
 * @author hzzhouhongfei
 * @version $$ RedisKeyHelper, 2024/3/26 4:58 PM hzzhouhongfei $$
 */
public class RedisKeyHelper
{
	private static final String STORI_ACC_REDIS_BASE_KEY = "stori_acc:";

	private static final String INIT_ACCOUNT_LIMIT_LABEL = "initAccountLimit:";

	private static final String OPERATE_ACCOUNT_LIMIT_LABEL = "operateAccountLimit:";

	/**
	 * REDIS分布式锁KEY后缀
	 */
	private static final String REDIS_DISTRIBUTED_LOCK_SUFFIX = ":lock";

	public static String getOperateAccountLimitRedisKey(String accountId, String limitType)
	{
		return STORI_ACC_REDIS_BASE_KEY + CommonConstant.COLON_SYMBOL + INIT_ACCOUNT_LIMIT_LABEL + accountId
				+ CommonConstant.COLON_SYMBOL + limitType + REDIS_DISTRIBUTED_LOCK_SUFFIX;
	}
}
