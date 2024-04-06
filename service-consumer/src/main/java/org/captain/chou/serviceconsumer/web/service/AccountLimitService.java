package org.captain.chou.serviceconsumer.web.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import cn.com.stori.account.web.vo.AccountLimitManageVo;

/**
 * 额度管理服务feign调用接口
 * @author hzzhouhongfei
 * @version $$ AccountLimitService, 2024/4/5 9:53 PM hzzhouhongfei $$
 */
@FeignClient("account-limit") //申明要调用的服务
public interface AccountLimitService
{
	/**
	 * 初始化额度
	 * @param accountLimitManageVo
	 * @return
	 */
	@PostMapping("/accountLimit/initAccountLimit")
	void initAccountLimit(AccountLimitManageVo accountLimitManageVo);

	/**
	 * 增加账户额度
	 * @param accountLimitManageVo
	 */
	@PostMapping("/accountLimit/increaseAccountLimit")
	void increaseAccountLimit(AccountLimitManageVo accountLimitManageVo);

	/**
	 * 减少账户额度
	 * @param accountLimitManageVo
	 */
	@PostMapping("/accountLimit/deductAccountLimit")
	void deductAccountLimit(AccountLimitManageVo accountLimitManageVo);
}
