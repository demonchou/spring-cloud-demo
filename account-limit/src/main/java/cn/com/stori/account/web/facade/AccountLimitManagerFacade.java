package cn.com.stori.account.web.facade;

import cn.com.stori.account.web.dto.AccountLimitManageDTO;
import cn.com.stori.account.web.dto.AccountLimitQueryReqDTO;
import cn.com.stori.account.web.dto.AccountLimitQueryRespDTO;
import cn.com.stori.account.web.dto.ResultDTO;

/**
 * 账户额度管理门面
 * @author hzzhouhongfei
 * @version $$ AccountLimitManagerFacade, 2024/3/25 2:06 PM hzzhouhongfei $$
 */
public interface AccountLimitManagerFacade
{
	/**
	 * 初始化额度
	 * @param accountLimitManageDTO
	 * @return ResultDTO
	 */
	ResultDTO<Boolean> initAccountLimit(AccountLimitManageDTO accountLimitManageDTO);

	/**
	 * 增加账户额度
	 * @param accountLimitManageDTO
	 * @return
	 */
	ResultDTO<Boolean> increaseAccountLimit(AccountLimitManageDTO accountLimitManageDTO);

	/**
	 * 减少账户额度
	 * @param accountLimitManageDTO
	 * @return
	 */
	ResultDTO<Boolean> deductAccountLimit(AccountLimitManageDTO accountLimitManageDTO);

	/**
	 * 分页查询
	 * @param accountLimitQueryReqDTO
	 * @return
	 */
	ResultDTO<AccountLimitQueryRespDTO> pageQuery(AccountLimitQueryReqDTO accountLimitQueryReqDTO);
}
