package cn.com.stori.account.web.module.dto;

import lombok.Data;

/**
 * 额度查询dto
 * @author hzzhouhongfei
 * @version $$ AccountLimitManageDto, 2024/3/25 2:11 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitQueryDto extends PageQueryDto
{

	/**
	 * 额度Id
	 */
	private String limitId;

	/**
	 * 账户ID
	 */
	private String accountId;

	/**
	 * 额度类型
	 */
	private String limitType;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 币种
	 */
	private String currency;

}
