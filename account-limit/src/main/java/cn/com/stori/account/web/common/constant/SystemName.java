package cn.com.stori.account.web.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统名称
 * @author hzzhouhongfei
 * @version $$ SystemType, 2024/3/24 4:41 PM hzzhouhongfei $$
 */
@Getter
@AllArgsConstructor
public enum SystemName
{
	ACCOUNT_LIMIT("01", "account-limit", "账户额度系统");

	/**
	 * 系统编号
	 */
	private String code;

	/**
	 * 系统英文名
	 */
	private String nameEn;

	/**
	 * 系统中文名
	 */
	private String nameCn;
}
