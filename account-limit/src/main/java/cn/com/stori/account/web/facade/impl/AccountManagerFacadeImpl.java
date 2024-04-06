package cn.com.stori.account.web.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.stori.account.web.common.exception.BusinessException;
import cn.com.stori.account.web.common.util.Assert;
import cn.com.stori.account.web.dto.ResultDTO;
import cn.com.stori.account.web.facade.AccountManagerFacade;
import cn.com.stori.account.web.module.service.AccountService;
import lombok.extern.slf4j.Slf4j;

/**
 * 账号操作门面
 * @author hzzhouhongfei
 * @version $$ AccountManagerFacadeImpl, 2024/3/26 6:34 PM hzzhouhongfei $$
 */
@Slf4j
@Service
public class AccountManagerFacadeImpl implements AccountManagerFacade
{
	@Autowired
	private AccountService accountService;

	@Override
	public ResultDTO<String> addAccount(String name)
	{
		ResultDTO<String> resultDTO = new ResultDTO<>();

		try
		{
			// 必要参数校验
			Assert.hasText(name, "name is null");

			String accountId = accountService.addAccount(name);
			resultDTO.success(accountId);
		}
		catch (BusinessException e)
		{
			log.warn("新增账号业务异常，原因：{}", e.getMessage(), e);
			resultDTO.businessError(e);
		}
		catch (Exception e)
		{
			log.error("【E1】新增账号系统异常，原因：{}", e.getMessage(), e);
			resultDTO.error(e);
		}
		return resultDTO;
	}

	@Override
	public ResultDTO<List<String>> getAllNormalAccountIds()
	{
		ResultDTO<List<String>> resultDTO = new ResultDTO<>();
		try
		{
			List<String> allNormalAccountIds = accountService.getAllNormalAccountIds();
			resultDTO.success(allNormalAccountIds);
		}
		catch (BusinessException e)
		{
			log.warn("获取所有正常账户账号业务异常，原因：{}", e.getMessage(), e);
			resultDTO.businessError(e);
		}
		catch (Exception e)
		{
			log.error("【E1】获取所有正常账户账号系统异常，原因：{}", e.getMessage(), e);
			resultDTO.error(e);
		}
		return resultDTO;
	}
}
