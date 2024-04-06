package cn.com.stori.account.web.common.exception;

/**
 * 系统异常
 * @author hzzhouhongfei
 * @version $$ SystemException, 2024/3/24 4:36 PM hzzhouhongfei $$
 */
public class SystemException extends BaseException
{

	public SystemException(String code, String codeEn, String msg)
	{
		super(code, codeEn, msg);
	}

	public SystemException(String code, String codeEn, String msg, Throwable t)
	{
		super(code, codeEn, msg, t);
	}
}
