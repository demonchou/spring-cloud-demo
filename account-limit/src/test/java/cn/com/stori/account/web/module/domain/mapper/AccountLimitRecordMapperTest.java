package cn.com.stori.account.web.module.domain.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.stori.account.base.MysqlBaseTest;
import cn.com.stori.account.web.common.util.BizSerialNumberUtil;
import cn.com.stori.account.web.common.util.DateUtil;
import cn.com.stori.account.web.module.domain.entity.AccountLimitRecord;
import cn.com.stori.account.web.module.enums.OperationTypeEnum;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountLimitRecordMapperTest, 2024/3/25 12:56 PM hzzhouhongfei $$
 */
public class AccountLimitRecordMapperTest extends MysqlBaseTest
{

	@Autowired
	private AccountLimitRecordMapper accountLimitRecordMapper;

	@Test
	public void testInsert()
	{
		AccountLimitRecord accountLimitRecord = new AccountLimitRecord();
		String recordId = BizSerialNumberUtil.genBizSerialNumber("LR");
		accountLimitRecord.setRecordId(recordId);
		accountLimitRecord.setAccountId("accountId");
		accountLimitRecord.setLimitType("WITHDROW");
		accountLimitRecord.setLimitId(BizSerialNumberUtil.genBizSerialNumber("AL"));
		accountLimitRecord.setOperationType(OperationTypeEnum.ADD.getValue());
		accountLimitRecord.setOperationAmount(122);
		accountLimitRecord.setOutBizNo(BizSerialNumberUtil.genBizSerialNumber("LR"));
		accountLimitRecord.setOutBizDate(DateUtil.getCurrentTime());
		int insert = accountLimitRecordMapper.insert(accountLimitRecord);
		Assert.assertEquals(1, insert);
		AccountLimitRecord accountLimitRecord1 = accountLimitRecordMapper.selectById(recordId);
		Assert.assertNotNull(accountLimitRecord1);
	}

}