package cn.com.stori.account.web.module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 币种
 * @author hzzhouhongfei
 * @version $$ CurrencyEnum, 2024/3/25 1:54 PM hzzhouhongfei $$
 */
@Getter
@AllArgsConstructor
public enum CurrencyEnum
{
	/**
	 * CNY:人民币
	 */
	CNY("CNY", "156", "人民币"),

	/**
	 * USD:美元
	 */
	USD("USD", "840", "美元");

	/**
	 * 简码
	 */
	private String code;

	/**
	 * 币种国标编码
	 */
	private String currencyCode;

	/**
	 * 描述
	 */
	private String desc;
}
