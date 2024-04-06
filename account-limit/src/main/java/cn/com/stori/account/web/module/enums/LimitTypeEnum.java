package cn.com.stori.account.web.module.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 额度类型
 * @author hzzhouhongfei
 * @version $$ LimitTypeEnum, 2024/3/25 4:59 PM hzzhouhongfei $$
 */
@Getter
@AllArgsConstructor
public enum LimitTypeEnum
{
	/**
	 * WITHDRAW_DAILY_LIMIT:单日提现额度
	 */
	WITHDRAW_DAILY_LIMIT("WITHDRAW_DAILY_LIMIT", "单日提现额度"),
	/**
	 * TRANSFER_DAILY_LIMIT:单日转账额度
	 */
	TRANSFER_DAILY_LIMIT("TRANSFER_DAILY_LIMIT", "单日转账额度");

	/**
	 * 操作代码
	 */
	private String value;

	/**
	 * 描述
	 */
	private String desc;

	public static List<String> getLimitTypeList()
	{
		List<String> limitTypeList = new ArrayList<>();
		for (LimitTypeEnum typeEnum : values())
		{
			limitTypeList.add(typeEnum.value);
		}
		return limitTypeList;
	}

}
