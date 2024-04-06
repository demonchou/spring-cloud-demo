package cn.com.stori.account.web.module.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import cn.com.stori.account.web.module.domain.entity.AccountLimit;
import cn.com.stori.account.web.module.dto.AccountLimitQueryDto;

/**
 * 额度mapper
 * @author hzzhouhongfei
 * @version $$ CreditLimitMapper, 2024/3/21 9:26 PM hzzhouhongfei $$
 */
@Repository
public interface AccountLimitMapper extends BaseMapper<AccountLimit>
{

	/**
	 * 更新额度
	 * @param accountLimit
	 * @return
	 */
	int updateLimitAmount(AccountLimit accountLimit);

	/**
	 * 主键查询
	 * @param limitId
	 * @return
	 */
	AccountLimit selectById(@Param("limitId") String limitId);

	/**
	 * 分页查询
	 * @param queryDto
	 * @return
	 */
	List<AccountLimit> pageQuery(AccountLimitQueryDto queryDto);

	/**
	 * 分页查询
	 * @param queryDto
	 * @return
	 */
	Integer count(AccountLimitQueryDto queryDto);

	/**
	 * 根据账户ID和额度类型
	 * @param accountId
	 * @param limitType
	 * @return
	 */
	List<AccountLimit> selectByAccountIdAndLimitType(@Param("accountId") String accountId,
			@Param("limitType") String limitType);

	/**
	 * 根据账户ID和额度类型
	 * @param accountId
	 * @param limitType
	 * @return
	 */
	AccountLimit selectActiveByAccountIdAndLimitType(@Param("accountId") String accountId,
			@Param("limitType") String limitType);

}
