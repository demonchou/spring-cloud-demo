package cn.com.stori.account.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 额度管理DTO（对外）
 * @author hzzhouhongfei
 * @version $$ AccountLimitManageDTO, 2024/3/25 2:11 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitManageDTO implements Serializable
{
	private static final long serialVersionUID = 5942730661206596105L;

	/**
	 * 账户ID
	 */
	private String accountId;

	/**
	 * 额度类型
	 */
	private String limitType;

	/**
	 * 操作金额
	 */
	private Double operationAmount;

}
