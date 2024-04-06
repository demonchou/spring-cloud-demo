package cn.com.stori.account.web.module.domain.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.com.stori.account.web.module.domain.entity.AccountLimit;
import cn.com.stori.account.web.module.domain.entity.AccountLimitRecord;

/**
 * 额度记录mapper
 * @author hzzhouhongfei
 * @version $$ CreditLimitMapper, 2024/3/21 9:26 PM hzzhouhongfei $$
 */
@Repository
public interface AccountLimitRecordMapper extends BaseMapper<AccountLimitRecord>
{
	/**
	 * 业务主键查询
	 * @param recordId
	 * @return
	 */
	AccountLimitRecord selectById(@Param("recordId") String recordId);
}
