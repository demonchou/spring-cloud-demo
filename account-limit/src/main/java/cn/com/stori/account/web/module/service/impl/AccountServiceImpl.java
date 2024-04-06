package cn.com.stori.account.web.module.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.com.stori.account.web.common.util.BizSerialNumberUtil;
import cn.com.stori.account.web.module.constant.BizNoPrefix;
import cn.com.stori.account.web.module.domain.entity.Account;
import cn.com.stori.account.web.module.domain.mapper.AccountMapper;
import cn.com.stori.account.web.module.enums.AccountStatusEnum;
import cn.com.stori.account.web.module.service.AccountService;

/**
 * 账户接口
 * @author hzzhouhongfei
 * @version $$ AccountServiceImpl, 2024/3/25 1:40 PM hzzhouhongfei $$
 */
@Service
public class AccountServiceImpl implements AccountService
{
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public String addAccount(String name)
	{
		Account account = new Account();
		account.setAccountId(BizSerialNumberUtil.genBizSerialNumber(BizNoPrefix.ACCOUNT_ID_PREFIX));
		account.setName(name);
		account.setStatus(AccountStatusEnum.NORMAL.getValue());
		Assert.isTrue(accountMapper.insert(account) == 1, "账户入库异常");
		return account.getAccountId();
	}

	@Override
	public List<String> getAllNormalAccountIds()
	{
		return accountMapper.selectAllNormalAccountIds();
	}

}
