package cn.com.stori.account.base;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cn.com.stori.account.web.common.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author hzzhouhongfei
 * @version $$ PowerMockBaseTest, 2024/3/24 7:28 PM hzzhouhongfei $$
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest(
		{ SpringContextUtil.class})
@PowerMockIgnore({"org.xml.*", "javax.xml.*", "javax.crypto.*", "javax.management.*", "com.sun.org.apache.*",
		"org.w3c.dom.*"})
public class PowerMockBaseTest
{
	@Before
	public void setUp()
	{
		log.info("开始测试");
	}

	@After
	public void after()
	{
		log.info("结束测试");
	}

	@Test
	public void testInit()
	{

	}
}
