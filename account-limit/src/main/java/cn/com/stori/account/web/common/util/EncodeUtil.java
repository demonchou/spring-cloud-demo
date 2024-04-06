package cn.com.stori.account.web.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;

import cn.com.stori.account.web.common.errorcode.ErrorCodeEnum;
import cn.com.stori.account.web.common.exception.SystemException;

/**
 * 编码类工具
 * @author hzzhouhongfei
 * @version $$ EncodeUtil, 2024/3/24 4:28 PM hzzhouhongfei $$
 */
public class EncodeUtil
{
	/**
	 * md5摘要
	 *
	 * @param s 原始串
	 * @return
	 */
	public static String md5(String s)
	{
		return md5(s, CharEncoding.UTF_8);
	}

	/**
	 * md5摘要
	 *
	 * @param s      原始串
	 * @param encode 编码
	 * @return
	 */
	public static String md5(String s, String encode)
	{
		byte[] bytes;
		MessageDigest messagedigest;
		try
		{
			messagedigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException nosuchalgorithmexception)
		{
			throw new SystemException(ErrorCodeEnum.UNKNOWN_EXCEPTION.getCode(),
					ErrorCodeEnum.UNKNOWN_EXCEPTION.getCodeEn(), "no md5 support", nosuchalgorithmexception);
		}

		try
		{
			messagedigest.update(s.getBytes(encode));
		}
		catch (UnsupportedEncodingException e)
		{
			throw new SystemException(ErrorCodeEnum.UNKNOWN_EXCEPTION.getCode(),
					ErrorCodeEnum.UNKNOWN_EXCEPTION.getCodeEn(), "不支持的编码", e);
		}
		bytes = messagedigest.digest();
		return Hex.encodeHexString(bytes);
	}
}
