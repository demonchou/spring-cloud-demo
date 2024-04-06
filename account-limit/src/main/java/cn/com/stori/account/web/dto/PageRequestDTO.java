package cn.com.stori.account.web.dto;

import lombok.Data;

/**
 * 分页查询请求DTO
 * @author hzzhouhongfei
 * @version $$ PageRequestDTO, 2024/3/25 2:45 PM hzzhouhongfei $$
 */
@Data
public class PageRequestDTO
{
	private static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 当前页，从1开始
	 */
	private Integer queryPageNo;

	/**
	 * 每页查询条数
	 */
	private Integer pageSize;

	public Integer getQueryPageNo()
	{
		if (queryPageNo == null || queryPageNo <= 0)
		{
			queryPageNo = 1;
		}
		return queryPageNo;
	}

	public void setQueryPageNo(Integer queryPageNo)
	{
		this.queryPageNo = queryPageNo;
	}

	public Integer getPageSize()
	{
		if (pageSize == null || pageSize <= 0)
		{
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	/**
	 * 获取本页数据在数据库中的起始位置
	 *
	 * @return 返回起始位置
	 */
	public int getStartIndex()
	{
		return (this.getQueryPageNo() - 1) * this.getPageSize();
	}
}
