package cn.com.stori.account.web.module.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.com.stori.account.web.module.domain.entity.Account;

/**
 * 账户mapper
 * @author hzzhouhongfei
 * @version $$ UserMapper, 2024/3/21 9:29 PM hzzhouhongfei $$
 */
@Repository
public interface AccountMapper extends BaseMapper<Account>
{
	/**
	 * 根据ID获取账户实体
	 * @param accountId
	 * @return
	 */
	Account selectByAccountId(@Param("accountId") String accountId);

	/**
	 * 获取所有有效的账号的accountId
	 * @return
	 */
	List<String> selectAllNormalAccountIds();
}
