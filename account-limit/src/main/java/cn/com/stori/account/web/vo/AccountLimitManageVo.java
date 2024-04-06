package cn.com.stori.account.web.vo;

import cn.com.stori.account.web.dto.AccountLimitManageDTO;
import lombok.Data;

/**
 * 额度管理VO
 * @author hzzhouhongfei
 * @version $$ AccountLimitManageVo, 2024/4/5 10:02 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitManageVo
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
	 * 操作金额
	 */
	private Double operationAmount;

	public AccountLimitManageDTO convertToDTO()
	{
		AccountLimitManageDTO accountLimitManageDTO = new AccountLimitManageDTO();
		accountLimitManageDTO.setAccountId(getAccountId());
		accountLimitManageDTO.setLimitType(getLimitType());
		accountLimitManageDTO.setOperationAmount(getOperationAmount());
		return accountLimitManageDTO;
	}
}
