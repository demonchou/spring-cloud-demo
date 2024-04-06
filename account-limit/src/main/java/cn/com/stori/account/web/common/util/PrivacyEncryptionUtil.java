package cn.com.stori.account.web.common.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.bouncycastle.util.encoders.Hex;

import lombok.extern.slf4j.Slf4j;

/**
 * 加解密工具类，本期是本地实现。实际应用需要调用公共的隐私平台实现
 * @author hzzhouhongfei
 * @version $$ PrivacyEncryptionUtil, 2024/3/24 4:05 PM hzzhouhongfei $$
 */
@Slf4j
public class PrivacyEncryptionUtil
{
	/**
	 * 这是对称加密中的干扰串，实际应用中，来自服务器端，代码端以及数据库三个地方存储的串进行拼接得到。加密解密是，会和传入的seed做拼接
	 */
	public static String DEFAULT_SECURE_KEY = "9^tv5lad" + "xcggdneA" + "tYO@Gdj)c";

	public static String SECURE_KEY;

	public static final String ALGORITHM_NAME = "SM4";

	/**
	 * 加密算法/分组加密模式/分组填充方式
	 * PKCS7Padding-以8个字节为一组进行分组加密
	 * 定义分组加密模式使用：PKCS5Padding
	 */
	public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS7Padding";

	protected static String keyFromCode = "LOYIIG4H>,IF;Zklc%5uJGo0Z@JVe1t5IHx5";

	static
	{
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * sm4加密
	 *
	 * @param plainText 原文
	 * @param seed      秘钥盐
	 * @return
	 * @explain 加密模式：ECB
	 * 密文长度不固定，会随着被加密字符串长度的变化而变化
	 */
	public static String sm4EncryptEcb(String plainText, String seed)
	{
		return sm4EncryptEcb(plainText, seed, CharEncoding.UTF_8);
	}

	/**
	 * 根据指定密钥种子及编码类型，对明文进行国密加密
	 *
	 * @param plainText
	 * @param seed
	 * @param charsetName
	 * @return
	 */
	public static String sm4EncryptEcb(String plainText, String seed, String charsetName)
	{

		try
		{
			if (StringUtils.isBlank(plainText))
			{
				return plainText;
			}
			if (StringUtils.isBlank(seed))
			{
				throw new IllegalStateException(String.format("隐私信息国密算法加密时，seed为空，明文：%s", plainText));
			}
			if (StringUtils.isBlank(charsetName))
			{
				throw new IllegalStateException(
						String.format("隐私信息国密算法加密时，charsetName为空，明文：%s", plainText));
			}
			// 16进制字符串-->byte[]
			byte[] keyData = ByteUtils.fromHexString(getHexKey(seed));
			// String-->byte[]
			byte[] srcData = plainText.getBytes(charsetName);
			// 加密后的数组
			byte[] cipherArray = encrypt_Ecb_Padding(keyData, srcData);
			return Base64.encodeBase64String(cipherArray);
		}
		catch (IllegalArgumentException | UnsupportedEncodingException e)
		{
			log.warn(String.format("隐私信息国密算法加密异常，plainText：%s，seed：%s，charsetName：%s", plainText, seed,
							charsetName),
					e);
			return null;
		}
		catch (Exception e)
		{
			log.error(String.format("【E0】隐私信息国密算法加密异常，plainText：%s，seed：%s，charsetName：%s", plainText, seed,
					charsetName), e);
			return null;
		}
	}

	/**
	 * sm4解密
	 *
	 * @param cipherText 密文
	 * @param seed       加密盐
	 * @return 原文
	 * @explain 解密模式：采用ECB
	 * 密文长度不固定，会随着被加密字符串长度的变化而变化
	 */
	public static String sm4DecryptEcb(String cipherText, String seed)
	{
		return sm4DecryptEcb(cipherText, seed, CharEncoding.UTF_8);
	}

	/**
	 * sm4解密
	 *
	 * @param cipherText  原文
	 * @param seed        加密盐
	 * @param charsetName 编码格式
	 * @return 解密后的字符串
	 * @explain 解密模式：采用ECB
	 * 密文长度不固定，会随着被加密字符串长度的变化而变化
	 */
	public static String sm4DecryptEcb(String cipherText, String seed, String charsetName)
	{
		try
		{
			if (StringUtils.isBlank(cipherText))
			{
				return cipherText;
			}
			if (StringUtils.isBlank(seed))
			{
				throw new IllegalStateException("隐私信息国密算法解密时，seed为空，请检查");
			}
			if (StringUtils.isBlank(charsetName))
			{
				throw new IllegalStateException("隐私信息国密算法解密时，charsetName为空");
			}
			// hexString-->byte[]
			byte[] keyData = ByteUtils.fromHexString(getHexKey(seed));
			// 解密
			byte[] srcData = decrypt_Ecb_Padding(keyData, Base64.decodeBase64(cipherText));
			return new String(srcData, charsetName);
		}
		catch (IllegalArgumentException e)
		{
			log.warn(
					String.format("隐私信息国密算法解密密钥错误，cipherText：%s，seed：%s，charsetName：%s", cipherText, seed,
							charsetName), e);
			return null;
		}
		catch (Exception e)
		{
			log.error(String.format("【E0】隐私信息国密算法解密异常，cipherText：%s，seed：%s，charsetName：%s", cipherText,
					seed, charsetName), e);
			return null;
		}
	}

	/**
	 * 国密SM3，索引字段
	 *
	 * @param cipherText 原文
	 * @return 隐私数据索引字段
	 * @explain 国密SM3：消息摘要，可以用MD5作为对比理解，校验结果为256位。
	 * 密文长度不固定，会随着被加密字符串长度的变化而变化
	 */
	public static String getSm3Index(String cipherText)
	{
		return getSm3Index(cipherText, CharEncoding.UTF_8);
	}

	/**
	 * 国密SM3，索引字段
	 *
	 * @param cipherText  原文
	 * @param charsetName 编码格式
	 * @return 隐私数据索引字段
	 * @explain 国密SM3：消息摘要，可以用MD5作为对比理解，校验结果为256位。
	 * 密文长度不固定，会随着被加密字符串长度的变化而变化
	 */
	public static String getSm3Index(String cipherText, String charsetName)
	{
		if (StringUtils.isBlank(cipherText))
		{
			return cipherText;
		}
		else if (StringUtils.isBlank(charsetName))
		{
			throw new IllegalStateException("charsetName为空");
		}
		else
		{
			try
			{
				byte[] srcData = (cipherText + keyFromCode).getBytes(charsetName);
				SM3Digest sm3Digest = new SM3Digest();
				sm3Digest.update(srcData, 0, srcData.length);
				byte[] sumBytes = new byte[sm3Digest.getDigestSize()];
				sm3Digest.doFinal(sumBytes, 0);
				return (new String(Hex.encode(sumBytes), charsetName)).toLowerCase();
			}
			catch (Exception var6)
			{
				log.warn("获取信息摘要失败，原因：{}", var6.getMessage(), var6);
				return "";
			}
		}
	}

	/**
	 * 加密模式之Ecb
	 *
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 * @explain
	 */
	private static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data) throws Exception
	{
		// 获取加密方案
		Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING, BouncyCastleProvider.PROVIDER_NAME);
		Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, sm4Key);
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 *
	 * @param key
	 * @param cipherText
	 * @return
	 * @throws Exception
	 * @explain
	 */
	private static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText) throws Exception
	{
		// 获取解密方案
		Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING, BouncyCastleProvider.PROVIDER_NAME);
		Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
		cipher.init(Cipher.DECRYPT_MODE, sm4Key);
		return cipher.doFinal(cipherText);
	}

	private static String getHexKey(String seed)
	{
		return EncodeUtil.md5(SECURE_KEY + seed).toLowerCase();
	}

	public void init() throws Exception
	{
		if (StringUtils.isEmpty(SECURE_KEY))
		{
			// 此处简单做，实际应用使用的是专门的隐私信息平台提供三段key
			SECURE_KEY = DEFAULT_SECURE_KEY;
			if (StringUtils.isBlank(SECURE_KEY))
			{
				log.error("【E0】公共密钥加载失败");
				throw new Exception("公共密钥加载失败");
			}
			log.info("====公共密钥加载完成！====");
		}
	}

	public static void main(String[] args)
	{
		String s = PrivacyEncryptionUtil.sm4EncryptEcb("name", "2024032500AC00000451");
		System.out.println(s);
		System.out.println(s.equals("06srKgmelpPhccat28s/Yg=="));
		String s1 = PrivacyEncryptionUtil.sm4DecryptEcb("tiH90kmz4vlWmZLAv8nJSQ==", "2024032500AC00000501");
		System.out.println(s1);
	}
}
