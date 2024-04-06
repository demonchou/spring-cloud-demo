package cn.com.stori.account.web.module.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.stori.account.web.common.constant.LimitAmountConstants;
import cn.com.stori.account.web.common.errorcode.ErrorCodeEnum;
import cn.com.stori.account.web.common.exception.BusinessException;
import cn.com.stori.account.web.common.util.Assert;
import cn.com.stori.account.web.common.util.BeanConvertUtil;
import cn.com.stori.account.web.common.util.BizSerialNumberUtil;
import cn.com.stori.account.web.common.util.DateUtil;
import cn.com.stori.account.web.component.databease.redis.helper.RedisKeyHelper;
import cn.com.stori.account.web.module.constant.BizNoPrefix;
import cn.com.stori.account.web.module.domain.entity.Account;
import cn.com.stori.account.web.module.domain.entity.AccountLimit;
import cn.com.stori.account.web.module.domain.entity.AccountLimitRecord;
import cn.com.stori.account.web.module.domain.mapper.AccountLimitMapper;
import cn.com.stori.account.web.module.domain.mapper.AccountLimitRecordMapper;
import cn.com.stori.account.web.module.domain.mapper.AccountMapper;
import cn.com.stori.account.web.module.dto.AccountLimitDto;
import cn.com.stori.account.web.module.dto.AccountLimitManageDto;
import cn.com.stori.account.web.module.dto.AccountLimitQueryDto;
import cn.com.stori.account.web.module.dto.PageDto;
import cn.com.stori.account.web.module.enums.OperationTypeEnum;
import cn.com.stori.account.web.module.service.AccountLimitService;
import lombok.extern.slf4j.Slf4j;

/**
 * 账户额度接口类
 * @author hzzhouhongfei
 * @version $$ AccountLimitServiceImpl, 2024/3/25 6:05 PM hzzhouhongfei $$
 */
@Slf4j
@Service
public class AccountLimitServiceImpl implements AccountLimitService
{
	@Autowired
	private AccountLimitMapper accountLimitMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountLimitRecordMapper accountLimitRecordMapper;
	@Autowired
	private RedissonClient redissonClient;

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void initAccountLimit(AccountLimitManageDto accountLimitManageDto)
	{
		// 必要参数校验
		commonParaCheck(accountLimitManageDto);

		RLock initAccountLimitLock = redissonClient.getLock(
				RedisKeyHelper.getOperateAccountLimitRedisKey(accountLimitManageDto.getAccountId(),
						accountLimitManageDto.getLimitType()));
		boolean hasLocked = false;
		try
		{
			hasLocked = initAccountLimitLock.tryLock(2, 5, TimeUnit.SECONDS);

			if (hasLocked)
			{
				AccountLimit accountLimitDb = accountLimitMapper.selectActiveByAccountIdAndLimitType(
						accountLimitManageDto.getAccountId(),
						accountLimitManageDto.getLimitType());

				Assert.isNull(accountLimitDb, ErrorCodeEnum.ACCOUNT_LIMIT_ALREADY_EXISTS);

				// 构建额度Dto，初始化额度为0
				double operationAmount = accountLimitManageDto.getOperationAmount();
				Assert.isTrue(operationAmount > 0 && operationAmount < LimitAmountConstants.MAX_LIMIT_OPERATION_AMOUNT,
						ErrorCodeEnum.LIMIT_OPERATION_AMOUNT_ILLEGAL);
				AccountLimit accountLimit = new AccountLimit(accountLimitManageDto.getAccountId(),
						accountLimitManageDto.getLimitType(), operationAmount, DateUtil.getCurrentTime(),
						DateUtil.getDefaultLongTermTimestamp());
				accountLimit.setLimitId(BizSerialNumberUtil.genBizSerialNumber(BizNoPrefix.LIMIT_ID_PREFIX));

				int insertCount = accountLimitMapper.insert(accountLimit);
				Assert.isTrue(1 == insertCount, ErrorCodeEnum.LIMIT_INIT_EXCEPTION);

				// 操作记录
				addRecord(operationAmount, accountLimit, OperationTypeEnum.INIT.getValue());
			}
			else
			{
				throw new BusinessException(ErrorCodeEnum.GET_LIMIT_INIT_LOCK_FAIL,
						String.format("获取初始化账户额度redis锁失败，accountId：%s, limitType：%s",
								accountLimitManageDto.getAccountId(), accountLimitManageDto.getLimitType()));
			}

		}
		catch (InterruptedException e)
		{
			throw new BusinessException(ErrorCodeEnum.GET_LIMIT_INIT_LOCK_EXCEPTION,
					String.format("获取初始化账户额度redis锁异常，账户ID：%s，额度类型：%s，异常原因：%s",
							accountLimitManageDto.getAccountId(), accountLimitManageDto.getLimitType(),
							e.getMessage()), e);
		}
		finally
		{
			if (hasLocked)
			{
				try
				{
					initAccountLimitLock.forceUnlock();
				}
				catch (Exception e)
				{
					log.error("【E1】释放初始化额度锁异常，异常原因：{}", e.getMessage(), e);
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void increaseAccountLimit(AccountLimitManageDto accountLimitManageDto)
	{
		// 必要参数校验
		commonParaCheck(accountLimitManageDto);

		RLock increaseAccountLimitLock = redissonClient.getLock(
				RedisKeyHelper.getOperateAccountLimitRedisKey(accountLimitManageDto.getAccountId(),
						accountLimitManageDto.getLimitType()));
		boolean hasLocked = false;

		try
		{
			hasLocked = increaseAccountLimitLock.tryLock(2, 5, TimeUnit.SECONDS);

			if (hasLocked)
			{
				double operationAmount = accountLimitManageDto.getOperationAmount();
				Assert.isTrue(operationAmount > 0 && operationAmount < LimitAmountConstants.MAX_LIMIT_OPERATION_AMOUNT,
						ErrorCodeEnum.LIMIT_OPERATION_AMOUNT_ILLEGAL);

				AccountLimit accountLimit = accountLimitMapper.selectActiveByAccountIdAndLimitType(
						accountLimitManageDto.getAccountId(), accountLimitManageDto.getLimitType());

				Assert.notNull(accountLimit, ErrorCodeEnum.ACCOUNT_LIMIT_NOT_EXISTS);

				accountLimit.setLimitAmount(accountLimit.getLimitAmount() + operationAmount);
				int updateCount = accountLimitMapper.updateLimitAmount(accountLimit);
				Assert.isTrue(1 == updateCount, ErrorCodeEnum.LIMIT_ADD_EXCEPTION);

				addRecord(operationAmount, accountLimit, OperationTypeEnum.ADD.getValue());
			}
			else
			{
				throw new BusinessException(ErrorCodeEnum.GET_LIMIT_INIT_LOCK_FAIL,
						String.format("获取增加账户额度redis锁失败，accountId：%s, limitType：%s",
								accountLimitManageDto.getAccountId(), accountLimitManageDto.getLimitType()));
			}
		}
		catch (InterruptedException e)
		{
			throw new BusinessException(ErrorCodeEnum.GET_LIMIT_INIT_LOCK_EXCEPTION,
					String.format("获取增加账户额度redis锁异常，账户ID：%s，额度类型：%s，异常原因：%s",
							accountLimitManageDto.getAccountId(), accountLimitManageDto.getLimitType(),
							e.getMessage()), e);
		}
		finally
		{
			if (hasLocked)
			{
				try
				{
					increaseAccountLimitLock.forceUnlock();
				}
				catch (Exception e)
				{
					log.error("【E1】释放增加额度锁异常，异常原因：{}", e.getMessage(), e);
				}
			}
		}

	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void deductAccountLimit(AccountLimitManageDto accountLimitManageDto)
	{
		// 必要参数校验
		commonParaCheck(accountLimitManageDto);

		RLock deductAccountLimitLock = redissonClient.getLock(
				RedisKeyHelper.getOperateAccountLimitRedisKey(accountLimitManageDto.getAccountId(),
						accountLimitManageDto.getLimitType()));
		boolean hasLocked = false;

		try
		{
			hasLocked = deductAccountLimitLock.tryLock(2, 5, TimeUnit.SECONDS);

			if (hasLocked)
			{
				double operationAmount = accountLimitManageDto.getOperationAmount();
				Assert.isTrue(operationAmount > 0 && operationAmount < LimitAmountConstants.MAX_LIMIT_OPERATION_AMOUNT,
						ErrorCodeEnum.LIMIT_OPERATION_AMOUNT_ILLEGAL);

				String accountId = accountLimitManageDto.getAccountId();

				AccountLimit accountLimit = accountLimitMapper.selectActiveByAccountIdAndLimitType(
						accountId, accountLimitManageDto.getLimitType());

				Assert.notNull(accountLimit, ErrorCodeEnum.ACCOUNT_LIMIT_NOT_EXISTS);

				double limitAmount = accountLimit.getLimitAmount() - operationAmount;

				Assert.isTrue(limitAmount >= 0, ErrorCodeEnum.LIMIT_OPERATION_AMOUNT_ILLEGAL);

				accountLimit.setLimitAmount(limitAmount);
				int updateCount = accountLimitMapper.updateLimitAmount(accountLimit);

				Assert.isTrue(1 == updateCount, ErrorCodeEnum.LIMIT_ADD_EXCEPTION);

				addRecord(operationAmount, accountLimit, OperationTypeEnum.MINUS.getValue());
			}
			else
			{
				throw new BusinessException(ErrorCodeEnum.GET_LIMIT_MINUS_LOCK_FAIL,
						String.format("获取减少账户额度redis锁失败，accountId：%s, limitType：%s",
								accountLimitManageDto.getAccountId(), accountLimitManageDto.getLimitType()));
			}
		}
		catch (InterruptedException e)
		{
			throw new BusinessException(ErrorCodeEnum.GET_LIMIT_MINUS_LOCK_EXCEPTION,
					String.format("获取减少账户额度redis锁异常，账户ID：%s，额度类型：%s，异常原因：%s",
							accountLimitManageDto.getAccountId(), accountLimitManageDto.getLimitType(),
							e.getMessage()), e);
		}
		finally
		{
			if (hasLocked)
			{
				try
				{
					deductAccountLimitLock.forceUnlock();
				}
				catch (Exception e)
				{
					log.error("【E1】释放减少额度锁异常，异常原因：{}", e.getMessage(), e);
				}
			}
		}

	}

	@Override
	public PageDto<AccountLimitDto> pageQuery(AccountLimitQueryDto accountLimitQueryDto)
	{
		Integer count = accountLimitMapper.count(accountLimitQueryDto);
		List<AccountLimit> accountLimits = Collections.emptyList();
		if (count > 0)
		{
			accountLimits = accountLimitMapper.pageQuery(accountLimitQueryDto);
		}

		PageDto<AccountLimitDto> pageDto = new PageDto<>();
		pageDto.setPageSize(accountLimitQueryDto.getPageSize());
		pageDto.setPageNo(accountLimitQueryDto.getCurrentPage());
		pageDto.setResult(BeanConvertUtil.convertBeanList(accountLimits, AccountLimitDto.class));
		pageDto.setTotalCount(count);
		return pageDto;
	}

	private void commonParaCheck(AccountLimitManageDto accountLimitManageDto)
	{
		// 必要参数校验
		Assert.notNull(accountLimitManageDto, "accountLimitManageDto is null");
		String accountId = accountLimitManageDto.getAccountId();
		Assert.hasText(accountId, "accountId为空");
		String limitType = accountLimitManageDto.getLimitType();
		Assert.hasText(limitType, "limitType为空");

		// 判断账号是否异常
		accountIsNormal(accountLimitManageDto.getAccountId());
	}

	/**
	 * 判断账户是否正常，包括存在性判断和状态的判断
	 * @param accountId
	 */
	private void accountIsNormal(String accountId)
	{
		Account account = accountMapper.selectByAccountId(accountId);
		Assert.notNull(account, ErrorCodeEnum.ACCOUNT_NOT_EXIST);
		Assert.isTrue(account.isNormal(), ErrorCodeEnum.ACCOUNT_STATUS_EXCEPTION);
	}

	private void addRecord(double operationAmount, AccountLimit accountLimit, String operationType)
	{
		AccountLimitRecord accountLimitRecord = new AccountLimitRecord();
		accountLimitRecord.setRecordId(BizSerialNumberUtil.genBizSerialNumber(BizNoPrefix.RECORD_ID_PREFIX));
		accountLimitRecord.setAccountId(accountLimit.getAccountId());
		accountLimitRecord.setLimitId(accountLimit.getLimitId());
		accountLimitRecord.setLimitType(accountLimit.getLimitType());
		accountLimitRecord.setOutBizNo(accountLimit.getLimitId());
		accountLimitRecord.setOutBizDate(DateUtil.getCurrentTime());
		accountLimitRecord.setOperationType(operationType);
		accountLimitRecord.setOperationAmount(operationAmount);
		int insert = accountLimitRecordMapper.insert(accountLimitRecord);
		Assert.isTrue(1 == insert, ErrorCodeEnum.LIMIT_RECORD_ADD_EXCEPTION);
	}
}
