package cn.com.stori.account.base;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试基类
 * @author hzzhouhongfei
 * @version $$ MysqlBaseTest, 2024/3/24 8:38 PM hzzhouhongfei $$
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
@Transactional(transactionManager = "transactionManager")
// 不回滚则设置value = false
@Rollback(value = true)
@Slf4j
public class MysqlBaseTest
{
	@Before
	public void before()
	{
		log.info("开始测试");
	}

	@After
	public void after()
	{
		log.info("结束测试");
	}
}
