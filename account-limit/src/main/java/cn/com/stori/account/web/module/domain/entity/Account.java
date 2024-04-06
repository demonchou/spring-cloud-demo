/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.stori.account.web.module.domain.entity;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

import cn.com.stori.account.web.common.util.PrivacyEncryptionUtil;
import cn.com.stori.account.web.module.enums.AccountStatusEnum;
import lombok.Data;

/**
 * 用户账户
 * @author hzzhouhongfei
 * @version $$ Account, 2024/3/21 9:23 PM hzzhouhongfei $$
 */
@Data
public class Account
{
	/**
	 * 用户ID
	 */
	private String accountId;

	/**
	 * 状态 {@link AccountStatusEnum}
	 */
	private String status;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 用户姓名
	 */
	private String nameEnc;

	/**
	 * 用户姓名
	 */
	private String nameIndex;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	private Timestamp updateTime;

	public boolean isNormal()
	{
		return this.status.equals(AccountStatusEnum.NORMAL.getValue());
	}


	public String getName()
	{
		if (StringUtils.isNotBlank(name) || StringUtils.isBlank(accountId))
		{
			return name;
		}
		return PrivacyEncryptionUtil.sm4DecryptEcb(nameEnc, accountId);
	}

	public String getNameEnc()
	{
		if (StringUtils.isBlank(nameEnc))
		{
			if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(accountId))
			{
				return PrivacyEncryptionUtil.sm4EncryptEcb(name, accountId);
			}
		}
		return nameEnc;
	}

	public String getNameIndex()
	{
		return PrivacyEncryptionUtil.getSm3Index(getName());
	}
}
