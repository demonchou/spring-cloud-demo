package cn.com.stori.account.web.module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 额度状态枚举（列举，视具体业务场景需要进行调整）
 * @author hzzhouhongfei
 * @version $$ UserQuotaStatusEnum, 2024/3/21 9:54 PM hzzhouhongfei $$
 */
@Getter
@AllArgsConstructor
public enum AccountLimitStatusEnum
{
	/**
	 * INIT:初始化
	 */
	INIT("INIT", "初始化"),

	/**
	 * ACTIVE:生效
	 */
	ACTIVE("ACTIVE", "生效"),

	/**
	 * PAUSE:暂停
	 */
	PAUSE("PAUSE", "暂停"),

	/**
	 * INACTIVE:失效
	 */
	INACTIVE("INACTIVE", "失效");

	/**
	 * 状态值
	 */
	private String value;

	/**
	 * 描述
	 */
	private String desc;

}
