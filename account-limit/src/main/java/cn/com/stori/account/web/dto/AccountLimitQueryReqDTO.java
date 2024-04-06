package cn.com.stori.account.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 额度查询请求
 * @author hzzhouhongfei
 * @version $$ AccountLimitQueryReqDTO, 2024/3/25 3:30 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitQueryReqDTO extends PageRequestDTO implements Serializable
{
	private static final long serialVersionUID = 1763074252233122649L;

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
