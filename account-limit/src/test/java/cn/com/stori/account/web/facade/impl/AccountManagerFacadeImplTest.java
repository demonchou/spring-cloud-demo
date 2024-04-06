package cn.com.stori.account.web.facade.impl;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.stori.account.base.MysqlBaseTest;
import cn.com.stori.account.web.dto.ResultDTO;
import cn.com.stori.account.web.facade.AccountManagerFacade;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountManagerFacadeImplTest, 2024/3/26 6:52 PM hzzhouhongfei $$
 */
public class AccountManagerFacadeImplTest extends MysqlBaseTest
{
	@Autowired
	private AccountManagerFacade accountManagerFacade;
	@Test
	public void addAccount()
	{
		ResultDTO<String> resultDTO = accountManagerFacade.addAccount("name");
		Assert.assertNotNull(resultDTO.getResult());
	}
}