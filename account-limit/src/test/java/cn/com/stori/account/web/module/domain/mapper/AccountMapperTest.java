package cn.com.stori.account.web.module.domain.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.stori.account.base.MysqlBaseTest;
import cn.com.stori.account.web.common.util.BizSerialNumberUtil;
import cn.com.stori.account.web.module.domain.entity.Account;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountMapperTest, 2024/3/24 9:13 PM hzzhouhongfei $$
 */
public class AccountMapperTest extends MysqlBaseTest
{

	@Autowired
	private AccountMapper accountMapper;
	private String accountId;

	@Before
	public void setUp() throws Exception
	{
		accountId = BizSerialNumberUtil.genBizSerialNumber("AC");
		Account account = new Account();
		account.setAccountId(accountId);
		account.setName("name");
		account.setStatus("ACTIVE");
		int insert = accountMapper.insert(account);
		Assert.assertEquals(1, insert);
	}

	@Test
	public void testSelectByAccountId()
	{
		// 6ed5376774cdd0b1d994229ff637f17874bf7a8719da4c203a3b19b169b761c9
		Account account = accountMapper.selectByAccountId("2024032500AC00000501");
		System.err.println("===>" + account);
		Assert.assertNotNull(account);
	}




}