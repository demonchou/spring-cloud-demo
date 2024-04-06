package cn.com.stori.account.web.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.com.stori.account.web.module.enums.AccountLimitStatusEnum;
import cn.com.stori.account.web.module.enums.LimitTypeEnum;
import lombok.Data;

/**
 * 额度DTO（对外）
 * @author hzzhouhongfei
 * @version $$ AccountLimitDTO, 2024/3/25 3:29 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitDTO implements Serializable
{
	private static final long serialVersionUID = 5881680040864012691L;

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
	private Double limitAmount;

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
	private Integer version;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
}
