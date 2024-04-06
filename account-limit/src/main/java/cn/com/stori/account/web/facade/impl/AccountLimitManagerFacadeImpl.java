package cn.com.stori.account.web.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.stori.account.web.common.constant.LimitAmountConstants;
import cn.com.stori.account.web.common.errorcode.ErrorCodeEnum;
import cn.com.stori.account.web.common.exception.BusinessException;
import cn.com.stori.account.web.common.util.Assert;
import cn.com.stori.account.web.common.util.BeanConvertUtil;
import cn.com.stori.account.web.dto.AccountLimitDTO;
import cn.com.stori.account.web.dto.AccountLimitManageDTO;
import cn.com.stori.account.web.dto.AccountLimitQueryReqDTO;
import cn.com.stori.account.web.dto.AccountLimitQueryRespDTO;
import cn.com.stori.account.web.dto.ResultDTO;
import cn.com.stori.account.web.facade.AccountLimitManagerFacade;
import cn.com.stori.account.web.module.dto.AccountLimitDto;
import cn.com.stori.account.web.module.dto.AccountLimitManageDto;
import cn.com.stori.account.web.module.dto.AccountLimitQueryDto;
import cn.com.stori.account.web.module.dto.PageDto;
import cn.com.stori.account.web.module.service.AccountLimitService;
import lombok.extern.slf4j.Slf4j;

/**
 * 账户额度管理门面实现
 * @author hzzhouhongfei
 * @version $$ AccountLimitManagerFacadeImpl, 2024/3/25 2:12 PM hzzhouhongfei $$
 */
@Slf4j
@Service
public class AccountLimitManagerFacadeImpl implements AccountLimitManagerFacade
{

	@Autowired
	private AccountLimitService accountLimitService;

	@Override
	public ResultDTO<Boolean> initAccountLimit(AccountLimitManageDTO accountLimitManageDTO)
	{

		ResultDTO<Boolean> resultDTO = new ResultDTO<>();
		try
		{
			// 必要参数校验
			commonParaCheck(accountLimitManageDTO);

			AccountLimitManageDto manageDto = new AccountLimitManageDto();
			manageDto.setAccountId(accountLimitManageDTO.getAccountId());
			manageDto.setLimitType(accountLimitManageDTO.getLimitType());
			manageDto.setOperationAmount(accountLimitManageDTO.getOperationAmount());
			accountLimitService.initAccountLimit(manageDto);
			resultDTO.success(Boolean.TRUE);
		}
		catch (BusinessException e)
		{
			log.warn("初始化额度业务异常，原因：{}，参数：{}", e.getMessage(), accountLimitManageDTO.toString(), e);
			resultDTO.businessError(e);
		}
		catch (Exception e)
		{
			log.error("【E1】保存附件系统异常，原因：{}，参数：{}", e.getMessage(), accountLimitManageDTO.toString(), e);
			resultDTO.error(e);
		}
		return resultDTO;
	}

	@Override
	public ResultDTO<Boolean> increaseAccountLimit(AccountLimitManageDTO accountLimitManageDTO)
	{
		ResultDTO<Boolean> resultDTO = new ResultDTO<>();
		try
		{
			// 必要参数校验
			commonParaCheck(accountLimitManageDTO);

			Double operationAmount = accountLimitManageDTO.getOperationAmount();
			Assert.notNull(operationAmount, ErrorCodeEnum.LIMIT_OPERATION_AMOUNT_IS_NULL);
			Assert.isTrue(operationAmount > 0 && operationAmount < LimitAmountConstants.MAX_LIMIT_OPERATION_AMOUNT,
					ErrorCodeEnum.LIMIT_OPERATION_AMOUNT_ILLEGAL);

			AccountLimitManageDto manageDto = new AccountLimitManageDto();
			manageDto.setAccountId(accountLimitManageDTO.getAccountId());
			manageDto.setLimitType(accountLimitManageDTO.getLimitType());
			manageDto.setOperationAmount(accountLimitManageDTO.getOperationAmount());

			accountLimitService.increaseAccountLimit(manageDto);
			resultDTO.success(Boolean.TRUE);
		}
		catch (BusinessException e)
		{
			log.warn("增加额度业务异常，原因：{}，参数：{}", e.getMessage(), accountLimitManageDTO.toString(), e);
			resultDTO.businessError(e);
			resultDTO.setResult(Boolean.FALSE);
		}
		catch (Exception e)
		{
			log.error("【E1】增加额度系统异常，原因：{}，参数：{}", e.getMessage(), accountLimitManageDTO.toString(), e);
			resultDTO.error(e);
			resultDTO.setResult(Boolean.FALSE);
		}
		return resultDTO;
	}

	@Override
	public ResultDTO<Boolean> deductAccountLimit(AccountLimitManageDTO accountLimitManageDTO)
	{
		ResultDTO<Boolean> resultDTO = new ResultDTO<>();
		try
		{
			// 必要参数校验
			commonParaCheck(accountLimitManageDTO);

			Double operationAmount = accountLimitManageDTO.getOperationAmount();
			Assert.notNull(operationAmount, ErrorCodeEnum.LIMIT_OPERATION_AMOUNT_IS_NULL);
			Assert.isTrue(operationAmount > 0 && operationAmount < LimitAmountConstants.MAX_LIMIT_OPERATION_AMOUNT,
					ErrorCodeEnum.LIMIT_OPERATION_AMOUNT_ILLEGAL);

			AccountLimitManageDto manageDto = new AccountLimitManageDto();
			manageDto.setAccountId(accountLimitManageDTO.getAccountId());
			manageDto.setLimitType(accountLimitManageDTO.getLimitType());
			manageDto.setOperationAmount(accountLimitManageDTO.getOperationAmount());

			accountLimitService.deductAccountLimit(manageDto);
			resultDTO.success(Boolean.TRUE);
		}
		catch (BusinessException e)
		{
			log.warn("扣减额度业务异常，原因：{}，参数：{}", e.getMessage(), accountLimitManageDTO.toString(), e);
			resultDTO.businessError(e);
			resultDTO.setResult(Boolean.FALSE);
		}
		catch (Exception e)
		{
			log.error("【E1】扣减额度系统异常，原因：{}，参数：{}", e.getMessage(), accountLimitManageDTO.toString(), e);
			resultDTO.error(e);
			resultDTO.setResult(Boolean.FALSE);
		}
		return resultDTO;
	}

	@Override
	public ResultDTO<AccountLimitQueryRespDTO> pageQuery(AccountLimitQueryReqDTO accountLimitQueryReqDTO)
	{
		ResultDTO<AccountLimitQueryRespDTO> resultDTO = new ResultDTO<>();
		try
		{
			Assert.notNull(accountLimitQueryReqDTO, "accountLimitQueryReqDTO is null");

			AccountLimitQueryDto queryDto = BeanConvertUtil.convertBean(accountLimitQueryReqDTO,
					AccountLimitQueryDto.class);
			queryDto.setCurrentPage(accountLimitQueryReqDTO.getQueryPageNo());
			queryDto.setPageSize(accountLimitQueryReqDTO.getPageSize());

			PageDto<AccountLimitDto> pageDto = accountLimitService.pageQuery(queryDto);
			List<AccountLimitDto> accountLimitDtoList = pageDto.getResult();
			List<AccountLimitDTO> accountLimitDTOList = BeanConvertUtil.convertBeanList(accountLimitDtoList,
					AccountLimitDTO.class);
			AccountLimitQueryRespDTO queryRespDTO = new AccountLimitQueryRespDTO(pageDto.getStartNo(),
					pageDto.getTotalCount(), pageDto.getPageSize(), accountLimitDTOList);

			resultDTO.success(queryRespDTO);
		}
		catch (BusinessException e)
		{
			log.warn("查询额度业务异常，原因：{}，参数：{}", e.getMessage(), accountLimitQueryReqDTO.toString(), e);
			resultDTO.businessError(e);
		}
		catch (Exception e)
		{
			log.error("【E1】查询额度系统异常，原因：{}，参数：{}", e.getMessage(), accountLimitQueryReqDTO.toString(), e);
			resultDTO.error(e);
		}
		return resultDTO;
	}

	private static void commonParaCheck(AccountLimitManageDTO accountLimitManageDTO)
	{
		// 必要参数校验
		Assert.notNull(accountLimitManageDTO, "accountLimitManageDTO is null");
		String accountId = accountLimitManageDTO.getAccountId();
		Assert.hasText(accountId, "accountId为空");
		String limitType = accountLimitManageDTO.getLimitType();
		Assert.hasText(limitType, "limitType为空");
	}
}
