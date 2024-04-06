package cn.com.stori.account.web.module.dto;

import java.sql.Timestamp;

import cn.com.stori.account.web.module.enums.AccountLimitStatusEnum;
import cn.com.stori.account.web.module.enums.LimitTypeEnum;
import lombok.Data;

/**
 * 额度dto
 * @author hzzhouhongfei
 * @version $$ AccountLimitManageDto, 2024/3/25 2:11 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitDto
{
	/**
	 * 额度主键
	 */
	private String limitId;

	/**
	 * 用户ID
	 */
	private String accountId;

	/**
	 * 额度类型 {@link LimitTypeEnum}
	 */
	private String limitType;

	/**
	 * 状态 {@link AccountLimitStatusEnum}
	 */
	private String status;

	/**
	 * 额度
	 */
	private double limitAmount;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 生效日期
	 */
	private Timestamp startDate;

	/**
	 * 失效时期，默认为9999-12-31日
	 */
	private Timestamp endDate;

	/**
	 * 版本号
	 */
	private int version;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
}
