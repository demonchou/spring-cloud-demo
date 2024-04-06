package cn.com.stori.account.web.common.exception;

import cn.com.stori.account.web.common.errorcode.ErrorCodeEnum;

/**
 * 业务异常
 * @author hzzhouhongfei
 * @version $$ BusinessException, 2024/3/24 4:57 PM hzzhouhongfei $$
 */
public class BusinessException extends BaseException
{

	public BusinessException(String code, String codeEn, String msg)
	{
		super(code, codeEn, msg);
	}

	public BusinessException(String code, String codeEn, String msg, Throwable t)
	{
		super(code, codeEn, msg, t);
	}

	public BusinessException(ErrorCodeEnum errorCodeEnum, String message, Throwable t)
	{
		super(errorCodeEnum.getCode(), errorCodeEnum.getCodeEn(), message, t);
	}

	public BusinessException(ErrorCodeEnum errorCodeEnum, String message)
	{
		super(errorCodeEnum.getCode(), errorCodeEnum.getCodeEn(), message);
	}
}
