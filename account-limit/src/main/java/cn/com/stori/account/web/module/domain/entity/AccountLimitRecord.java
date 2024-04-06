package cn.com.stori.account.web.module.domain.entity;

import java.sql.Timestamp;

import cn.com.stori.account.web.module.enums.OperationTypeEnum;
import lombok.Data;

/**
 * 账户额度记录
 * @author hzzhouhongfei
 * @version $$ AccountLimit, 2024/3/21 9:23 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitRecord
{
	/**
	 * 额度记录主键
	 */
	private String recordId;

	/**
	 * 额度主键
	 */
	private String limitId;

	/**
	 * 用户ID
	 */
	private String accountId;

	/**
	 * 额度类型
	 */
	private String limitType;

	/**
	 * 业务方业务流水号
	 */
	private String outBizNo;

	/**
	 * 业务方操作时间
	 */
	private Timestamp outBizDate;

	/**
	 * 操作类型 {@link OperationTypeEnum}
	 */
	private String operationType;

	/**
	 * 操作金额
	 */
	private double operationAmount;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	private Timestamp updateTime;

}
