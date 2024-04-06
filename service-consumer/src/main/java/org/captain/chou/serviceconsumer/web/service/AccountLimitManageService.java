package org.captain.chou.serviceconsumer.web.service;

import cn.com.stori.account.web.vo.AccountLimitManageVo;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountLimitManageService, 2024/4/5 9:45 PM hzzhouhongfei $$
 */
public interface AccountLimitManageService
{
	/**
	 * 初始化额度
	 * @param accountLimitManageVo
	 * @return
	 */
	void initAccountLimit(AccountLimitManageVo accountLimitManageVo);

	/**
	 * 增加账户额度
	 * @param accountLimitManageVo
	 */
	void increaseAccountLimit(AccountLimitManageVo accountLimitManageVo);

	/**
	 * 减少账户额度
	 * @param accountLimitManageVo
	 */
	void deductAccountLimit(AccountLimitManageVo accountLimitManageVo);
}
