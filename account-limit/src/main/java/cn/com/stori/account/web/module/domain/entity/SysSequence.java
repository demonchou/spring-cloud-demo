package cn.com.stori.account.web.module.domain.entity;

import lombok.Data;

/**
 * 业务序列实体
 * @author hzzhouhongfei
 * @version $$ SysSequence, 2024/3/24 5:36 PM hzzhouhongfei $$
 */
@Data
public class SysSequence
{
	/**
	 * 序列名（对应某一业务）
	 */
	private String sequenceName;

	/**
	 * 序列初始值
	 */
	private long startBy;

	/**
	 * 序列递增值
	 */
	private long incrementBy;

	/**
	 * 最近最大序列值
	 */
	private long lastNumber;
}
