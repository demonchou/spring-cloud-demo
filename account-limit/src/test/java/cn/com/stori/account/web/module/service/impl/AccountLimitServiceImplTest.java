package cn.com.stori.account.web.module.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.stori.account.base.MysqlBaseTest;
import cn.com.stori.account.web.module.domain.entity.AccountLimit;
import cn.com.stori.account.web.module.domain.mapper.AccountLimitMapper;
import cn.com.stori.account.web.module.dto.AccountLimitDto;
import cn.com.stori.account.web.module.dto.AccountLimitManageDto;
import cn.com.stori.account.web.module.dto.AccountLimitQueryDto;
import cn.com.stori.account.web.module.dto.PageDto;
import cn.com.stori.account.web.module.enums.LimitTypeEnum;
import cn.com.stori.account.web.module.service.AccountLimitService;
import cn.com.stori.account.web.module.service.AccountService;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountLimitServiceImplTest, 2024/3/25 7:10 PM hzzhouhongfei $$
 */
public class AccountLimitServiceImplTest extends MysqlBaseTest
{
	@Autowired
	private AccountLimitService accountLimitService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountLimitMapper accountLimitMapper;

	private String accountId;

	@Before
	public void setUp() throws Exception
	{
		accountId = accountService.addAccount("testName2");

		AccountLimitManageDto manageDto = new AccountLimitManageDto();
		manageDto.setAccountId(accountId);
		manageDto.setLimitType(LimitTypeEnum.WITHDRAW_DAILY_LIMIT.getValue());
		manageDto.setOperationAmount(0.1);
		accountLimitService.initAccountLimit(manageDto);
		List<AccountLimit> accountLimits = accountLimitMapper.selectByAccountIdAndLimitType(accountId,
				manageDto.getLimitType());
		Assert.assertTrue(CollectionUtils.isNotEmpty(accountLimits));
		AccountLimitQueryDto queryDto = new AccountLimitQueryDto();
		queryDto.setPageSize(10);
		queryDto.setCurrentPage(1);
		queryDto.setAccountId(accountId);
		PageDto<AccountLimitDto> pageDto = accountLimitService.pageQuery(queryDto);
		Assert.assertEquals(pageDto.getTotalCount(), 1);
		Assert.assertTrue(CollectionUtils.isNotEmpty(pageDto.getResult()));
		queryDto.setCurrentPage(2);
		pageDto = accountLimitService.pageQuery(queryDto);
		Assert.assertTrue(CollectionUtils.isEmpty(pageDto.getResult()));

		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}

		// 不存在的账号
		manageDto.setAccountId(accountId + 1);
		try
		{
			accountLimitService.initAccountLimit(manageDto);
		}
		catch (Exception e)
		{
			Assert.assertNotNull(e.getMessage());
		}

	}

	@Test
	public void increaseAccountLimit()
	{
		AccountLimitManageDto manageDto = new AccountLimitManageDto();
		manageDto.setAccountId(accountId);
		manageDto.setLimitType(LimitTypeEnum.WITHDRAW_DAILY_LIMIT.getValue());
		manageDto.setOperationAmount(10000);
		accountLimitService.increaseAccountLimit(manageDto);
		AccountLimit accountLimit = accountLimitMapper.selectActiveByAccountIdAndLimitType(accountId,
				manageDto.getLimitType());
		Assert.assertTrue(10000.1 == accountLimit.getLimitAmount());
	}

	@Test
	public void deductAccountLimit()
	{
		AccountLimitManageDto manageDto = new AccountLimitManageDto();
		manageDto.setAccountId(accountId);
		manageDto.setLimitType(LimitTypeEnum.WITHDRAW_DAILY_LIMIT.getValue());
		manageDto.setOperationAmount(10000);
		accountLimitService.increaseAccountLimit(manageDto);
		AccountLimit accountLimit = accountLimitMapper.selectActiveByAccountIdAndLimitType(accountId,
				manageDto.getLimitType());
		Assert.assertTrue(10000.1 == accountLimit.getLimitAmount());

		manageDto.setOperationAmount(5000);
		accountLimitService.deductAccountLimit(manageDto);
		AccountLimit accountLimit2 = accountLimitMapper.selectActiveByAccountIdAndLimitType(accountId,
				manageDto.getLimitType());
		Assert.assertTrue(5000.1 == accountLimit2.getLimitAmount());
	}
}