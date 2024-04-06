package cn.com.stori.account.web.module.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户状态枚举
 * @author hzzhouhongfei
 * @version $$ AccountStatusEnum, 2024/3/21 9:46 PM hzzhouhongfei $$
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum
{
	/**
	 * NORMAL:正常
	 */
	NORMAL("NORMAL", "正常"),

	/**
	 * PAUSE:暂停
	 */
	PAUSE("PAUSE", "暂停"),

	/**
	 * CANCELED:注销
	 */
	CANCELED("CANCELED", "注销");


	/**
	 * 状态值
	 */
	private String value;

	/**
	 * 描述
	 */
	private String desc;

	public static AccountStatusEnum getStatusByValue(String value)
	{
		if (StringUtils.isBlank(value))
		{
			return null;
		}

		for (AccountStatusEnum status : values())
		{
			if (StringUtils.equalsIgnoreCase(value, status.getValue()))
			{
				return status;
			}
		}
		return null;
	}
}
