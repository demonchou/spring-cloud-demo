package cn.com.stori.account.web.common.constant;

/**
 * 额度配置的金额常量
 * @author hzzhouhongfei
 * @version $$ CommonConstants, 2024/3/25 9:02 PM hzzhouhongfei $$
 */
public class LimitAmountConstants
{

	/**
	 * 额度最大值，可以做成从配置中心获取
	 */
	public static final double MAX_LIMIT_AMOUNT = 999999999;
	/**
	 * 最大值额度调整操作值，可以做成从配置中心获取
	 */
	public static final double MAX_LIMIT_OPERATION_AMOUNT = 100000;

	/**
	 * 默认初始化的额度值
	 */
	public static final double DEFAULT_LIMIT_AMOUNT = 0;
}
