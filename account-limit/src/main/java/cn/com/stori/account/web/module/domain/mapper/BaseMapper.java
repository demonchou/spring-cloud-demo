package cn.com.stori.account.web.module.domain.mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper基类
 * @author hzzhouhongfei
 * @version $$ BaseMapper, 2024/3/21 9:27 PM hzzhouhongfei $$
 */
public interface BaseMapper<T>
{
	/**
	 * 插入对象到数据库
	 *
	 * @param obj
	 * @return
	 */
	int insert(T obj);

	/**
	 * 根据id查表
	 *
	 * @param id
	 * @return
	 */
	T getById(int id);

	/**
	 * 更新行数据
	 *
	 * @param obj
	 * @return
	 */
	int update(T obj);

	/**
	 * 根据id删除数据
	 *
	 * @param id
	 * @return
	 */
	int delete(int id);

	/**
	 * 查询对象
	 *
	 * @param obj
	 * @return
	 */
	List<T> query(T obj);

	/**
	 * 通过查询条件，查询对象列表
	 *
	 * @param queryParam
	 * @return
	 */
	List<T> selectByMap(Map<String, Object> queryParam);
}
