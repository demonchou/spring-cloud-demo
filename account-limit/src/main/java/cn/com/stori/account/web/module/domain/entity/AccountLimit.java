package cn.com.stori.account.web.module.domain.entity;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

import cn.com.stori.account.web.common.util.DateUtil;
import cn.com.stori.account.web.module.enums.AccountLimitStatusEnum;
import cn.com.stori.account.web.module.enums.CurrencyEnum;
import cn.com.stori.account.web.module.enums.LimitTypeEnum;
import lombok.Data;

/**
 * 账户额度
 * @author hzzhouhongfei
 * @version $$ AccountLimit, 2024/3/21 9:23 PM hzzhouhongfei $$
 */
@Data
public class AccountLimit
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

	public AccountLimit(String accountId, String limitType, double limitAmount,
			Timestamp startDate, Timestamp endDate)
	{
		this.accountId = accountId;
		this.limitType = limitType;
		this.status = AccountLimitStatusEnum.ACTIVE.getValue();
		this.limitAmount = limitAmount;
		this.currency = CurrencyEnum.CNY.getCode();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public AccountLimit()
	{
	}

	public boolean isActive()
	{
		return StringUtils.equalsIgnoreCase(AccountLimitStatusEnum.ACTIVE.getValue(), status) && startDate.before(
				DateUtil.getCurrentTime()) && endDate.after(DateUtil.getCurrentTime());
	}

}
