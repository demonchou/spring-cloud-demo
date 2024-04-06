package cn.com.stori.account.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.stori.account.web.dto.ResultDTO;
import cn.com.stori.account.web.facade.AccountLimitManagerFacade;
import cn.com.stori.account.web.vo.AccountLimitManageVo;

/**
 * 账户
 * @author hzzhouhongfei
 * @version $$ AccountController, 2024/3/26 6:39 PM hzzhouhongfei $$
 */
@RequestMapping("/accountLimit")
@Controller
public class AccountLimitController
{
	@Autowired
	private AccountLimitManagerFacade accountLimitManagerFacade;

	// http://127.0.0.1:8080/accountLimit/initAccountLimit
	@RequestMapping("/initAccountLimit")
	@ResponseBody
	public String initAccountLimit(@RequestBody AccountLimitManageVo accountLimitManageVo)
	{
		ResultDTO<Boolean> resultDTO = accountLimitManagerFacade.initAccountLimit(accountLimitManageVo.convertToDTO());
		return resultDTO.isSuccess() ?
				String.format("初始化账户额度成功，accountId = %s", accountLimitManageVo.getAccountId()) :
				String.format("初始化账户额度成功，accountId = %s，失败原因 = %s", accountLimitManageVo.getAccountId(), resultDTO.getMsg());
	}


	// http://127.0.0.1:8080/accountLimit/increaseAccountLimit
	@RequestMapping("/increaseAccountLimit")
	@ResponseBody
	public String increaseAccountLimit(@RequestBody AccountLimitManageVo accountLimitManageVo)
	{
		ResultDTO<Boolean> resultDTO = accountLimitManagerFacade.increaseAccountLimit(accountLimitManageVo.convertToDTO());
		return resultDTO.isSuccess() ?
				String.format("初始化账户额度成功，accountId = %s", accountLimitManageVo.getAccountId()) :
				String.format("初始化账户额度成功，accountId = %s，失败原因 = %s", accountLimitManageVo.getAccountId(), resultDTO.getMsg());
	}


	// http://127.0.0.1:8080/accountLimit/deductAccountLimit
	@RequestMapping("/deductAccountLimit")
	@ResponseBody
	public String deductAccountLimit(@RequestBody AccountLimitManageVo accountLimitManageVo)
	{
		ResultDTO<Boolean> resultDTO = accountLimitManagerFacade.deductAccountLimit(accountLimitManageVo.convertToDTO());
		return resultDTO.isSuccess() ?
				String.format("初始化账户额度成功，accountId = %s", accountLimitManageVo.getAccountId()) :
				String.format("初始化账户额度成功，accountId = %s，失败原因 = %s", accountLimitManageVo.getAccountId(), resultDTO.getMsg());
	}

}
