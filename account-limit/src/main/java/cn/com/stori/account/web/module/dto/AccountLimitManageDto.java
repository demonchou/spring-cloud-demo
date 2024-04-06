package cn.com.stori.account.web.module.dto;

import lombok.Data;

/**
 * 额度管理dto
 * @author hzzhouhongfei
 * @version $$ AccountLimitManageDto, 2024/3/25 2:11 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitManageDto
{
	/**
	 * 账户ID
	 */
	private String accountId;

	/**
	 * 额度类型
	 */
	private String limitType;

	/**
	 * 额度金额
	 */
	private double operationAmount;

}
