package cn.com.stori.account.web.module.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import cn.com.stori.account.web.module.domain.entity.SysSequence;
import cn.com.stori.account.web.module.domain.mapper.SysSequenceMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 序列号
 * @author hzzhouhongfei
 * @version $$ SysSequenceServiceImpl, 2024/3/24 5:38 PM hzzhouhongfei $$
 */
@Data
@Slf4j
public class SysSequenceServiceImpl implements InitializingBean
{
	/**
	 * 当前序列值MAP对象，存储所有序列当前序列值
	 */
	private final ConcurrentHashMap<String, AtomicLong> currentSeqMap = new ConcurrentHashMap<>(8);
	/**
	 * 最大序列值MAP对象，存储所有序列最大序列值，用来判断是否重新从数据库加载数据
	 */
	private final ConcurrentHashMap<String, AtomicLong> maxSeqMap = new ConcurrentHashMap<>(8);

	private String sequenceName;

	private DataSourceTransactionManager transactionManager;

	@Autowired
	private SysSequenceMapper sysSequenceMapper;

	/**
	 * 查询指定序列 下一序列值
	 * 使用数据表中INCREMENT_BY字段值进行初始值计算
	 * @param seqName
	 * @return
	 */
	public synchronized long getNextSeq(String seqName)
	{
		Assert.hasText(seqName, "序列名不能为空");

		AtomicLong maxSeqAtomicLong = maxSeqMap.get(seqName);
		Assert.notNull(maxSeqAtomicLong, String.format("序列最大序列值为空，序列名：%s", seqName));
		AtomicLong curSeqAtomicLong = currentSeqMap.get(seqName);
		Assert.notNull(maxSeqAtomicLong, String.format("序列当前序列值为空，序列名：%s", seqName));

		if (curSeqAtomicLong.get() >= maxSeqAtomicLong.get())
		{
			SysSequence newUpdatedSysSequence = incrementAndGetSysSequence(seqName);
			Assert.notNull(newUpdatedSysSequence, String.format("序列不存在，序列名：%s", seqName));

			long newMaxSeq = newUpdatedSysSequence.getLastNumber();
			long newCurSeq = newMaxSeq - newUpdatedSysSequence.getIncrementBy();
			log.info("更新序列最大值，序列：{}，当前值：{}，最大值：{}，更新后当前值：{}，更新后最大值：{}", seqName,
					curSeqAtomicLong.get(),
					maxSeqAtomicLong.get(), newCurSeq, newMaxSeq);

			maxSeqAtomicLong.set(newMaxSeq);
			maxSeqMap.put(seqName, maxSeqAtomicLong);

			curSeqAtomicLong.set(newCurSeq);
			currentSeqMap.put(seqName, curSeqAtomicLong);
		}

		return curSeqAtomicLong.getAndIncrement();
	}

	@Override
	public void afterPropertiesSet()
	{
		Assert.hasText(sequenceName, "sequenceName不能为空");
		Assert.notNull(transactionManager, "transactionManager不能为null");
		Assert.notNull(sysSequenceMapper, "mysqlSequenceDao不能为null");

		// 本地全量序列初始化
		for (SysSequence sysSequence : sysSequenceMapper.getAllSequenceList())
		{
			String seqName = sysSequence.getSequenceName();
			SysSequence newUpdatedSysSequence = incrementAndGetSysSequence(seqName);
			Assert.notNull(newUpdatedSysSequence, String.format("序列不存在，序列名：%s", seqName));

			long maxSeq = newUpdatedSysSequence.getLastNumber();
			maxSeqMap.put(seqName, new AtomicLong(maxSeq));
			currentSeqMap.put(seqName, new AtomicLong(maxSeq - newUpdatedSysSequence.getIncrementBy()));
		}

		log.info("应用初始化序列信息，最大序列值：{}，当前序列值：{}", maxSeqMap, currentSeqMap);
	}

	/**
	 * 查询指定序列名的最大序列值（根据）
	 * @param seqName
	 * @return
	 */
	private SysSequence incrementAndGetSysSequence(final String seqName)
	{
		Assert.hasText(seqName, "序列名不能为空");

		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		return transactionTemplate.execute(status -> {
			int count = sysSequenceMapper.updateSeqByIncrement(seqName);
			if (count != 1)
			{
				throw new DataAccessResourceFailureException(
						String.format("更新SEQUENCE失败，更新条数为%s,更新SEQUENCE为%s,请检查 SYS_SEQUENCES配置...",
								count, seqName));
			}
			return sysSequenceMapper.getSequenceByName(seqName);
		});
	}
}
