package cn.com.stori.account.web.module.service.impl;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.stori.account.base.MysqlBaseTest;
import cn.com.stori.account.web.module.domain.entity.Account;
import cn.com.stori.account.web.module.domain.mapper.AccountMapper;
import cn.com.stori.account.web.module.service.AccountService;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountServiceImplTest, 2024/3/25 11:13 PM hzzhouhongfei $$
 */
public class AccountServiceImplTest extends MysqlBaseTest
{
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountMapper accountMapper;

	@Test
	public void addAccount()
	{
		String accountId = accountService.addAccount("name1");
		Account account = accountMapper.selectByAccountId(accountId);
		Assert.assertNotNull(account);
	}
}