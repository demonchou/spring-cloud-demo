package cn.com.stori.account.web.module.service;

import cn.com.stori.account.web.module.dto.AccountLimitDto;
import cn.com.stori.account.web.module.dto.AccountLimitManageDto;
import cn.com.stori.account.web.module.dto.AccountLimitQueryDto;
import cn.com.stori.account.web.module.dto.PageDto;

/**
 *
 * @author hzzhouhongfei
 * @version $$ AccountLimitService, 2024/3/25 1:48 PM hzzhouhongfei $$
 */
public interface AccountLimitService
{
	/**
	 * 初始化额度
	 * @param accountLimitManageDto
	 * @return
	 */
	void initAccountLimit(AccountLimitManageDto accountLimitManageDto);

	/**
	 * 增加账户额度
	 * @param accountLimitManageDto
	 */
	void increaseAccountLimit(AccountLimitManageDto accountLimitManageDto);

	/**
	 * 减少账户额度
	 * @param accountLimitManageDto
	 */
	void deductAccountLimit(AccountLimitManageDto accountLimitManageDto);

	/**
	 * 分页查询
	 * @param accountLimitQueryDto
	 * @return
	 */
	PageDto<AccountLimitDto> pageQuery(AccountLimitQueryDto accountLimitQueryDto);

}
