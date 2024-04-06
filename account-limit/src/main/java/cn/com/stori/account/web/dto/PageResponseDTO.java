package cn.com.stori.account.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 分页查询结果DTO
 * @author hzzhouhongfei
 * @version $$ PageResponseDTO, 2024/3/25 2:46 PM hzzhouhongfei $$
 */
@Data
public class PageResponseDTO <T extends Serializable> implements Serializable
{
	/** 默认每页记录数为20条 */
	private static int DEFAULT_PAGE_SIZE = 20;
	/** 默认页旁边显示页数 */
	private static int DEFAULT_NEAR_PAGE = 3;

	/** 每页的记录数 */
	private Long pageSize;
	/** 当前页旁边显示页数 */
	private Long nearPage;
	/** 当前页第一条数据在数据集中的索引位置,从0开始 */
	private Long start;
	/** 总记录数 */
	private Long totalSize;
	/** 当前页中存放的记录,类型一般为List */
	private List<T> list;

	/**
	 * 默认构造方法
	 * 只构造空页.
	 */
	public PageResponseDTO()
	{
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 通过参数构造分页对象
	 *
	 * @param start 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param pageSize 本页容量
	 * @param data 本页包含的数据
	 */
	public PageResponseDTO(long start, long totalSize, long pageSize, List<T> data)
	{
		this(start, totalSize, pageSize, DEFAULT_NEAR_PAGE, data);
	}

	/**
	 * 通过参数构造分页对象
	 *
	 * @param start 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param pageSize 本页容量
	 * @param nearPage 当前页面旁边显示的页数
	 * @param data 本页包含的数据
	 */
	public PageResponseDTO(long start, long totalSize, long pageSize, long nearPage, List<T> data)
	{
		this.pageSize = pageSize;
		this.start = start;
		this.totalSize = totalSize;
		this.nearPage = nearPage;
		this.list = data;
	}

	/**
	 * 取总页数
	 *
	 * @return 返回总页数
	 */
	public long getTotalPage()
	{
		if (totalSize % pageSize == 0)
		{
			return totalSize / pageSize;
		}
		else
		{
			return totalSize / pageSize + 1;
		}
	}

	/**
	 * 取当前页码
	 * 页码从1开始.
	 *
	 * @return 返回当前页码
	 */
	public long getQueryPageNo()
	{
		return start / pageSize + 1;
	}

	/**
	 * 该页是否有下一页
	 *
	 * @return 返回是否有下一页
	 */
	public boolean isHasNextPage()
	{
		return this.getQueryPageNo() < this.getTotalPage();
	}

	/**
	 * 该页是否有上一页
	 *
	 * @return 返回是否有上一页
	 */
	public boolean isHasPreviousPage()
	{
		return this.getQueryPageNo() > 1;
	}

	/**
	 * 是否存在数据
	 * @return
	 */
	public boolean hasData()
	{
		return null != this.getList() && this.getList().size() > 0;
	}
}
