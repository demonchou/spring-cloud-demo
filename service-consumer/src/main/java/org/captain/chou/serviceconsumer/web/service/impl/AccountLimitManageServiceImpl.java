package org.captain.chou.serviceconsumer.web.service.impl;

import org.captain.chou.serviceconsumer.web.service.AccountLimitManageService;
import org.captain.chou.serviceconsumer.web.service.AccountLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.stori.account.web.vo.AccountLimitManageVo;

/**
 * 账户额度管理服务实现类
 * @author hzzhouhongfei
 * @version $$ AccountLimitManageServiceImpl, 2024/4/5 9:50 PM hzzhouhongfei $$
 */
@Service
public class AccountLimitManageServiceImpl implements AccountLimitManageService
{
	@Autowired
	private AccountLimitService accountLimitService;

	@Override
	public void initAccountLimit(AccountLimitManageVo accountLimitManageVo)
	{
		accountLimitService.initAccountLimit(accountLimitManageVo);
	}

	@Override
	public void increaseAccountLimit(AccountLimitManageVo accountLimitManageVo)
	{
		accountLimitService.increaseAccountLimit(accountLimitManageVo);
	}

	@Override
	public void deductAccountLimit(AccountLimitManageVo accountLimitManageVo)
	{
		accountLimitService.deductAccountLimit(accountLimitManageVo);
	}
}
