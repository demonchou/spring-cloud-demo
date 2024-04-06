package cn.com.stori.account.web.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.com.stori.account.web.common.errorcode.ErrorCodeEnum;
import cn.com.stori.account.web.common.exception.BusinessException;
import lombok.Data;

/**
 * 业务处理结果
 * @author hzzhouhongfei
 * @version $$ ResponseDTO, 2024/3/25 2:47 PM hzzhouhongfei $$
 */
@Data
public class ResultDTO<T> implements Serializable
{

	private static final long serialVersionUID = 5985628268761473554L;

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

	/**
	 * 返回数据
	 */
	private T result;

	/**
	 * 表单校验
	 */
	private Map<String, String> errors = new HashMap<>();

	public void success(T data)
	{
		this.code = ErrorCodeEnum.SUCCESS.getCode();
		this.result = data;
	}

	public void businessError(ErrorCodeEnum errorCodeEnum, String message)
	{
		this.code = errorCodeEnum.getCode();
		this.codeEn = errorCodeEnum.getCodeEn();
		this.msg = message;
	}

	public void businessError(ErrorCodeEnum errorCodeEnum)
	{
		this.code = errorCodeEnum.getCode();
		this.codeEn = errorCodeEnum.getCodeEn();
		this.msg = errorCodeEnum.getDesc();
	}

	public void businessError(String code, String codeEn, String msg)
	{
		this.code = code;
		this.codeEn = codeEn;
		this.msg = msg;
	}

	public void businessError(BusinessException businessException)
	{
		this.code = businessException.getCode();
		this.codeEn = businessException.getCodeEn();
		this.msg = businessException.getMsg();
	}

	public void error(Exception exception)
	{
		this.code = ErrorCodeEnum.UNKNOWN_EXCEPTION.getCode();
		this.codeEn = ErrorCodeEnum.UNKNOWN_EXCEPTION.getCodeEn();
		this.msg = exception.getMessage();
	}

	/**
	 * 判断业务是否执行成功
	 * @return
	 */
	public boolean isSuccess()
	{
		return StringUtils.equalsIgnoreCase(this.code, ErrorCodeEnum.SUCCESS.getCode());
	}
}
