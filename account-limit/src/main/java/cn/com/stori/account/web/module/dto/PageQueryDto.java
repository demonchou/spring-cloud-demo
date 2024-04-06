package cn.com.stori.account.web.module.dto;

import lombok.Data;

/**
 * 分页查询请求基类
 * @author hzzhouhongfei
 * @version $$ Page, 2024/3/25 5:31 PM hzzhouhongfei $$
 */
@Data
public class PageQueryDto
{
	public static final Integer MAX_PAGE_SIZE = 500;
	public static final Integer MAX_TOTAL_ITEMS = 10000;
	private static final Integer DEFAULT_PAGE_SIZE = 10;
	private static final Integer DEFAULT_FIRST_PAGE = 1;

	private Integer pageSize;

	private Integer currentPage;

	private Integer beginRowNum;

	protected Integer getDefaultPageSize()
	{
		return DEFAULT_PAGE_SIZE;
	}
	public Integer getPageSize()
	{
		if (pageSize == null)
		{
			return getDefaultPageSize();
		}

		return pageSize;
	}

	/**
	 * 查询分页查询起始行数
	 *
	 * @return 分页查询起始行数
	 */
	public int getBeginRowNum()
	{
		if (beginRowNum != null)
		{
			return beginRowNum;
		}

		int currentPageNo = this.getCurrentPage();

		if (currentPageNo == 1)
		{
			return 0;
		}

		currentPageNo--;

		int pgSize = this.getPageSize();

		return pgSize * currentPageNo;
	}
}
