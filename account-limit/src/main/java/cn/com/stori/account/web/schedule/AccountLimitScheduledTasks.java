package cn.com.stori.account.web.schedule;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.stori.account.web.common.util.Assert;
import cn.com.stori.account.web.common.util.DateUtil;
import cn.com.stori.account.web.dto.AccountLimitManageDTO;
import cn.com.stori.account.web.dto.AccountLimitQueryReqDTO;
import cn.com.stori.account.web.dto.AccountLimitQueryRespDTO;
import cn.com.stori.account.web.dto.ResultDTO;
import cn.com.stori.account.web.facade.AccountLimitManagerFacade;
import cn.com.stori.account.web.facade.AccountManagerFacade;
import cn.com.stori.account.web.module.enums.LimitTypeEnum;
import cn.com.stori.account.web.module.enums.OperationTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * 调度任务
 * @author hzzhouhongfei
 * @version $$ AccountLimitScheduledTasks, 2024/3/25 1:33 PM hzzhouhongfei $$
 */
@Slf4j
@Component
public class AccountLimitScheduledTasks
{
	@Autowired
	private AccountLimitManagerFacade accountLimitManagerFacade;

	@Autowired
	private AccountManagerFacade accountManagerFacade;

	@Autowired
	private Executor customThreadPool;

	private List<String> allNormalAccountIds = Collections.emptyList();

	/**
	 * 每1分钟执行一次新增用户账户
	 */
	@Scheduled(fixedRate = 30000)
	public void addAccountAndGetAccountIds()
	{
		log.info("=== 开启 模拟新增账户 ===");
		try
		{
			String name = DateUtil.formatDate(DateUtil.getCurrentTime(), DateUtil.FMT_DATE_YYYYMMDDHHMMSS);
			ResultDTO<String> resultDTO = accountManagerFacade.addAccount(name);

			Assert.isTrue(resultDTO.isSuccess());
			log.info("=== 新增一个用户完成，name：{}, accountId：{} ===", name, resultDTO.getResult());

			ResultDTO<List<String>> resultDTO2 = accountManagerFacade.getAllNormalAccountIds();
			Assert.isTrue(resultDTO.isSuccess());

			// 更新缓存用于后去模拟操作用
			allNormalAccountIds = resultDTO2.getResult();
			log.info("=== 结束 模拟新增账户 ===");
		}
		catch (Exception e)
		{
			log.error("【E0】新增用户账户失败");
		}
	}

	/**
	 * 系统起来3秒后，每5秒执行一次
	 */
	@Scheduled(initialDelay = 3000, fixedRate = 5000)
	public void performAccountLimitOperations()
	{

		for (int i = 0; i < 10; i++)
		{
			customThreadPool.execute(() -> worker());
		}

	}

	private void worker()
	{
		Random rand = new Random();
		// 随机一个用户
		String accountId = allNormalAccountIds.get(rand.nextInt(allNormalAccountIds.size()));
		// 随机一个额度类型
		List<String> limitTypeList = LimitTypeEnum.getLimitTypeList();
		String limitType = limitTypeList.get(rand.nextInt(limitTypeList.size()));

		AccountLimitManageDTO limitManageDTO = new AccountLimitManageDTO();
		limitManageDTO.setAccountId(accountId);
		limitManageDTO.setLimitType(limitType);

		AccountLimitQueryReqDTO queryReqDTO = new AccountLimitQueryReqDTO();
		queryReqDTO.setQueryPageNo(1);
		queryReqDTO.setPageSize(10);
		queryReqDTO.setAccountId(accountId);
		queryReqDTO.setLimitType(limitType);

		List<String> operationTypeList = OperationTypeEnum.getOperationTypeList();
		operationTypeList.add("query");
		String operationType = operationTypeList.get(rand.nextInt(operationTypeList.size()));

		log.info("=== 开始 模拟额度管理 accountId:【{}】limitType:【{}】operationType:【{}】 ===", accountId, limitType,
				operationType);
		switch (operationType)
		{
			case "INIT":
				// 初始化
				limitManageDTO.setOperationAmount(10000.0);
				accountLimitManagerFacade.initAccountLimit(limitManageDTO);
				break;
			case "ADD":
				// 增加额度 10
				limitManageDTO.setOperationAmount(10.0);
				accountLimitManagerFacade.increaseAccountLimit(limitManageDTO);
				break;
			case "MINUS":
				// 减去额度 10
				limitManageDTO.setOperationAmount(10.0);
				accountLimitManagerFacade.deductAccountLimit(limitManageDTO);
				break;
			case "query":
				ResultDTO<AccountLimitQueryRespDTO> resultDTO = accountLimitManagerFacade.pageQuery(queryReqDTO);
				if (resultDTO.isSuccess())
				{
					log.info("查询结果：totalCount:{}, 第一个结果是：{}", resultDTO.getResult().getTotalSize(),
							resultDTO.getResult().getList().get(0).toString());
				}
				break;
			default:
				log.error("【E1】此业务类型暂不支持券事件保存，业务类型：{}", operationType);
		}
		log.info("=== 结束 模拟额度管理 accountId:【{}】limitType:【{}】operationType:【{}】 ===", accountId, limitType,
				operationType);
	}
}
