package cn.com.stori.account.web.module.domain.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.com.stori.account.web.module.domain.entity.SysSequence;

/**
 * 获取mysql系统时间、下一个序列号
 * @author hzzhouhongfei
 * @version $$ SysSequenceMapper, 2024/3/24 5:32 PM hzzhouhongfei $$
 */
@Repository
public interface SysSequenceMapper extends BaseMapper<SysSequence>
{
	/**
	 * 获取数据库当前时间
	 * @return 数据库时间
	 */
	Date getSysdate();

	/**
	 * 更新指定序列名的最大序列值
	 * @param sequenceName
	 * @return
	 */
	int updateSeqByIncrement(@Param("sequenceName") String sequenceName);

	/**
	 * 获取所有序列记录
	 * @return
	 */
	List<SysSequence> getAllSequenceList();

	/**
	 * 根据序列名查询序列记录
	 * @param sequenceName
	 * @return
	 */
	SysSequence getSequenceByName(@Param("sequenceName") String sequenceName);
}
