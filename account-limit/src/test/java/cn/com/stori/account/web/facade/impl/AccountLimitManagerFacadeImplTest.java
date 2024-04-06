package cn.com.stori.account.web.facade.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.stori.account.base.MysqlBaseTest;
import cn.com.stori.account.web.dto.AccountLimitManageDTO;
import cn.com.stori.account.web.dto.AccountLimitQueryReqDTO;
import cn.com.stori.account.web.dto.AccountLimitQueryRespDTO;
import cn.com.stori.account.web.dto.ResultDTO;
import cn.com.stori.account.web.facade.AccountLimitManagerFacade;
import cn.com.stori.account.web.module.domain.entity.AccountLimit;
import cn.com.stori.account.web.module.domain.mapper.AccountLimitMapper;
import cn.com.stori.account.web.module.enums.LimitTypeEnum;
import cn.com.stori.account.web.module.service.AccountLimitService;
import cn.com.stori.account.web.module.service.AccountService;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountLimitManagerFacadeImplTest, 2024/3/25 7:10 PM hzzhouhongfei $$
 */
public class AccountLimitManagerFacadeImplTest extends MysqlBaseTest
{

	@Autowired
	private AccountLimitManagerFacade accountLimitManagerFacade;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountLimitMapper accountLimitMapper;

	private String accountId;

	@Before
	public void setUp() throws Exception
	{
		accountId = accountService.addAccount("testName2");

		AccountLimitManageDTO manageDTO = new AccountLimitManageDTO();
		manageDTO.setAccountId(accountId);
		manageDTO.setLimitType(LimitTypeEnum.WITHDRAW_DAILY_LIMIT.getValue());
		manageDTO.setOperationAmount(0.1);
		ResultDTO<Boolean> resultDTO = accountLimitManagerFacade.initAccountLimit(manageDTO);
		Assert.assertTrue(resultDTO.getResult());
		List<AccountLimit> accountLimits = accountLimitMapper.selectByAccountIdAndLimitType(accountId,
				manageDTO.getLimitType());
		Assert.assertTrue(CollectionUtils.isNotEmpty(accountLimits));

		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void increaseAccountLimit()
	{
		AccountLimitManageDTO manageDTO = new AccountLimitManageDTO();
		manageDTO.setAccountId(accountId);
		manageDTO.setLimitType(LimitTypeEnum.WITHDRAW_DAILY_LIMIT.getValue());
		manageDTO.setOperationAmount(10000.0);
		ResultDTO<Boolean> resultDTO = accountLimitManagerFacade.increaseAccountLimit(manageDTO);
		Assert.assertTrue(resultDTO.getResult());
		AccountLimit accountLimit = accountLimitMapper.selectActiveByAccountIdAndLimitType(accountId,
				manageDTO.getLimitType());
		Assert.assertTrue(10000.1 == accountLimit.getLimitAmount());
	}

	@Test
	public void deductAccountLimit()
	{
		AccountLimitManageDTO manageDTO = new AccountLimitManageDTO();
		manageDTO.setAccountId(accountId);
		manageDTO.setLimitType(LimitTypeEnum.WITHDRAW_DAILY_LIMIT.getValue());
		manageDTO.setOperationAmount(5000.0);
		ResultDTO<Boolean> resultDTO = accountLimitManagerFacade.increaseAccountLimit(manageDTO);
		Assert.assertTrue(resultDTO.getResult());
		AccountLimit accountLimit = accountLimitMapper.selectActiveByAccountIdAndLimitType(accountId,
				manageDTO.getLimitType());
		Assert.assertTrue(5000.1 == accountLimit.getLimitAmount());
	}

	@Test
	public void pageQuery()
	{
		AccountLimitQueryReqDTO queryReqDTO = new AccountLimitQueryReqDTO();
		queryReqDTO.setPageSize(10);
		queryReqDTO.setQueryPageNo(1);
		queryReqDTO.setAccountId(accountId);
		ResultDTO<AccountLimitQueryRespDTO> resultDTO = accountLimitManagerFacade.pageQuery(
				queryReqDTO);
		Assert.assertTrue(1 == resultDTO.getResult().getTotalSize().longValue());
		Assert.assertTrue(CollectionUtils.isNotEmpty(resultDTO.getResult().getList()));
		queryReqDTO.setQueryPageNo(2);
		resultDTO = accountLimitManagerFacade.pageQuery(queryReqDTO);
		Assert.assertTrue(CollectionUtils.isEmpty(resultDTO.getResult().getList()));
	}
}