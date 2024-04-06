package cn.com.stori.account.web.module.service;

import java.util.List;

/**
 * 账户接口类
 * @author hzzhouhongfei
 * @version $$ AccountService, 2024/3/25 1:28 PM hzzhouhongfei $$
 */
public interface AccountService
{
	/**
	 * 新增账户
	 * @param name
	 * @return
	 */
	String addAccount(String name);

	/**
	 * 获取所有有效的账号的accountId
	 * @return
	 */
	List<String> getAllNormalAccountIds();
}
