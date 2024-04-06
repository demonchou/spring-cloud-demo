package cn.com.stori.account.web.common.errorcode;

import cn.com.stori.account.web.common.constant.SystemName;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务错误码e
 * @author hzzhouhongfei
 * @version $$ ErrorCodeEnum, 2024/3/24 4:52 PM hzzhouhongfei $$
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum
{
	/**
	 * 成功
	 */
	SUCCESS("0000", "SUCCESS", "成功"),
	/**
	 * 非法参数
	 */
	ILLEGAL_PARAM("0001", "ILLEGAL_PARAM", "非法参数"),
	/**
	 * 业务异常
	 */
	BUSINESS_EXCEPTION("0002", "BUSINESS_EXCEPTION", "业务异常"),
	/**
	 * 未知异常
	 */
	UNKNOWN_EXCEPTION("0003", "UNKNOWN_EXCEPTION", "未知异常"),
	/**
	 * 账户状态异常
	 */
	ACCOUNT_STATUS_EXCEPTION("0004", "ACCOUNT_STATUS_EXCEPTION", "账户状态异常"),
	/**
	 * 账户不存在
	 */
	ACCOUNT_NOT_EXIST("0005", "ACCOUNT_NOT_EXIST", "账户不存在"),
	/**
	 * 额度初始化异常
	 */
	LIMIT_INIT_EXCEPTION("0006", "LIMIT_INIT_EXCEPTION", "额度初始化异常"),
	/**
	 * 增加额度异常
	 */
	LIMIT_ADD_EXCEPTION("0007", "LIMIT_ADD_EXCEPTION", "增加额度异常"),
	/**
	 * 减少额度异常
	 */
	LIMIT_MINUS_EXCEPTION("0008", "LIMIT_MINUS_EXCEPTION", "减少额度异常"),
	/**
	 * 额度查询异常
	 */
	LIMIT_QUERY_EXCEPTION("0009", "LIMIT_QUERY_EXCEPTION", "额度查询异常"),
	/**
	 * 额度调整值非法
	 */
	LIMIT_OPERATION_AMOUNT_ILLEGAL("0010", "LIMIT_OPERATION_AMOUNT_ILLEGAL", "额度调整值非法"),
	/**
	 * 额度调整值为空
	 */
	LIMIT_OPERATION_AMOUNT_IS_NULL("0011", "LIMIT_OPERATION_AMOUNT_IS_NULL", "额度调整值为空"),
	/**
	 * 账户额度不存在
	 */
	ACCOUNT_LIMIT_NOT_EXISTS("0012", "ACCOUNT_LIMIT_NOT_EXISTS", "账户额度不存在"),
	/**
	 * 账户额度已经存在
	 */
	ACCOUNT_LIMIT_ALREADY_EXISTS("0013", "ACCOUNT_LIMIT_ALREADY_EXISTS", "账户额度已经存在"),
	/**
	 * 新增账户额度操作记录异常
	 */
	LIMIT_RECORD_ADD_EXCEPTION("0014", "LIMIT_RECORD_ADD_EXCEPTION", "新增账户额度操作记录异常"),
	/**
	 * 获取初始化账户额度分布式锁失败
	 */
	GET_LIMIT_INIT_LOCK_FAIL("0015", "GET_LIMIT_INIT_LOCK_FAIL", "获取初始化账户额度分布式锁失败"),
	/**
	 * 获取初始化账户额度分布式锁异常
	 */
	GET_LIMIT_INIT_LOCK_EXCEPTION("0016", "GET_LIMIT_INIT_LOCK_EXCEPTION", "获取初始化账户额度分布式锁异常"),
	/**
	 * 获取增加账户额度分布式锁失败
	 */
	GET_LIMIT_ADD_LOCK_FAIL("0017", "GET_LIMIT_ADD_LOCK_FAIL", "获取增加账户额度分布式锁失败"),
	/**
	 * 获取增加账户额度分布式锁异常
	 */
	GET_LIMIT_ADD_LOCK_EXCEPTION("0018", "GET_LIMIT_ADD_LOCK_EXCEPTION", "获取增加账户额度分布式锁异常"),
	/**
	 * 获取减少账户额度分布式锁失败
	 */
	GET_LIMIT_MINUS_LOCK_FAIL("0019", "GET_LIMIT_MINUS_LOCK_FAIL", "获取减少账户额度分布式锁失败"),
	/**
	 * 获取减少账户额度分布式锁异常
	 */
	GET_LIMIT_MINUS_LOCK_EXCEPTION("0020", "GET_LIMIT_MINUS_LOCK_EXCEPTION", "获取减少账户额度分布式锁异常"),
	;

	private final String code;
	private final String codeEn;
	private final String desc;

	/**
	 * 前两位为系统代码，后四位为内部错误码 形如：010000
	 * @return
	 */
	public String getCode()
	{
		return SystemName.ACCOUNT_LIMIT.getCode() + this.code;
	}
}
