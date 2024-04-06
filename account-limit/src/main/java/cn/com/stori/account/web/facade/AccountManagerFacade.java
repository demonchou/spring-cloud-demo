package cn.com.stori.account.web.facade;

import java.util.List;

import cn.com.stori.account.web.dto.ResultDTO;

/**
 * 账号操作门面
 * @author hzzhouhongfei
 * @version $$ AccountManagerFacade, 2024/3/26 6:33 PM hzzhouhongfei $$
 */
public interface AccountManagerFacade
{
	/**
	 * 新增账户
	 * @param name
	 * @return
	 */
	ResultDTO<String> addAccount(String name);

	ResultDTO<List<String>> getAllNormalAccountIds();
}
