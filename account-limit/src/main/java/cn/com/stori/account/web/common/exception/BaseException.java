package cn.com.stori.account.web.common.exception;

import cn.com.stori.account.web.common.errorcode.ErrorCodeEnum;
import lombok.Getter;

/**
 * 异常基础类
 * @author hzzhouhongfei
 * @version $$ BaseException, 2024/3/24 4:30 PM hzzhouhongfei $$
 */
@Getter
public class BaseException extends RuntimeException
{
	/**
	 * 返回码
	 * @see ErrorCodeEnum#getCode()
	 */
	private String code;

	/**
	 * 返回码英文描述
	 * @see ErrorCodeEnum#getCodeEn()
	 */
	private String codeEn;

	/**
	 * 返回码描述
	 * @see ErrorCodeEnum#getDesc()
	 */
	private String msg;

	public BaseException(String code, String codeEn, String msg)
	{
		super(msg);
		this.code = code;
		this.codeEn = codeEn;
		this.msg = msg;
	}

	public BaseException(String code, String codeEn, String msg, Throwable t)
	{
		super(msg, t);
		this.code = code;
		this.codeEn = codeEn;
		this.msg = msg;
	}
}
