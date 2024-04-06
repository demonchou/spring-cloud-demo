package cn.com.stori.account.web.module.domain.mapper;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.stori.account.base.MysqlBaseTest;
import cn.com.stori.account.web.common.util.BizSerialNumberUtil;
import cn.com.stori.account.web.common.util.DateUtil;
import cn.com.stori.account.web.module.domain.entity.AccountLimit;
import cn.com.stori.account.web.module.enums.AccountLimitStatusEnum;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountLimitMapperTest, 2024/3/24 9:11 PM hzzhouhongfei $$
 */
public class AccountLimitMapperTest extends MysqlBaseTest
{
	@Autowired
	private AccountLimitMapper accountLimitMapper;

	private String limitId;

	@Before
	public void setUp() throws Exception
	{
		AccountLimit accountLimit = new AccountLimit();
		limitId = BizSerialNumberUtil.genBizSerialNumber("AL");
		accountLimit.setLimitId(limitId);
		accountLimit.setAccountId(BizSerialNumberUtil.genBizSerialNumber("AC"));
		accountLimit.setLimitType("TRANSFOR");
		accountLimit.setLimitAmount(50000);
		accountLimit.setStatus(AccountLimitStatusEnum.ACTIVE.getValue());
		accountLimit.setStartDate(DateUtil.getCurrentTime());
		accountLimit.setEndDate(DateUtil.getDefaultLongTermTimestamp());
		accountLimit.setCurrency("CNY");
		int insert = accountLimitMapper.insert(accountLimit);
		Assert.assertEquals(1, insert);
	}

	@Test
	public void testUpdateLimitAmount()
	{
		AccountLimit accountLimit = accountLimitMapper.selectById(limitId);
		accountLimit.setLimitAmount(60000);
		int count = accountLimitMapper.updateLimitAmount(accountLimit);
		Assert.assertEquals(1, count);
	}

	@Test
	public void testSelectByAccountIdAndLimitType()
	{
		AccountLimit accountLimit = accountLimitMapper.selectById(limitId);
		List<AccountLimit> accountLimits = accountLimitMapper.selectByAccountIdAndLimitType(accountLimit.getAccountId(),
				accountLimit.getLimitType());
		Assert.assertTrue(CollectionUtils.isNotEmpty(accountLimits));

	}

}