package cn.com.stori.account.web.module.domain.mapper;

import static org.junit.Assert.assertFalse;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.stori.account.base.MysqlBaseTest;
import cn.com.stori.account.web.module.domain.entity.SysSequence;

/**
 *
 * @author hzzhouhongfei
 * @version $$ SysSequenceMapperTest, 2024/3/25 12:49 AM hzzhouhongfei $$
 */
public class SysSequenceMapperTest extends MysqlBaseTest
{

	@Autowired
	private SysSequenceMapper sysSequenceMapper;

	@Test
	public void getSysdate()
	{
		Date sysdate = sysSequenceMapper.getSysdate();
		Assert.assertNotNull(sysdate);
	}

	@Test
	public void updateSeqByIncrement()
	{
		int count = sysSequenceMapper.updateSeqByIncrement("STORI_ACC_SEQ");
		Assert.assertEquals(1, count);
	}

	@Test
	public void getAllSequenceList()
	{
		List<SysSequence> allSequenceList = sysSequenceMapper.getAllSequenceList();
		assertFalse(allSequenceList.isEmpty());
	}

	@Test
	public void getSequenceByName()
	{
		SysSequence storiAccSeq = sysSequenceMapper.getSequenceByName("STORI_ACC_SEQ");
		Assert.assertNotNull(storiAccSeq);
	}
}