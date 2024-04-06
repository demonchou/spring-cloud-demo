package cn.com.stori.account.web.module.dto;

import java.util.List;

import lombok.Data;

/**
 * 分页结果类
 * @author hzzhouhongfei
 * @version $$ PageDto, 2024/3/25 5:57 PM hzzhouhongfei $$
 */
@Data
public class PageDto<T>
{
	protected Integer pageNo = 1;
	protected Integer pageSize = 10;
	protected List<T> result;
	protected Integer totalCount = -1;

	public PageDto()
	{
	}

	public PageDto(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getStartNo()
	{
		return this.pageSize * (this.pageNo - 1);
	}

	public int getEndNo()
	{
		return this.pageSize * this.pageNo > this.totalCount ? this.totalCount - 1 : this.pageSize * this.pageNo - 1;
	}

	public int getPageNo()
	{
		return this.pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = Math.max(pageNo, 1);
	}

	public int getPageSize()
	{
		return this.pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = Math.max(pageSize, 1);
	}

	public PageDto<T> pageSize(int thePageSize)
	{
		this.setPageSize(thePageSize);
		return this;
	}

	public int getFirst()
	{
		return (this.pageNo - 1) * this.pageSize;
	}


	public List<T> getResult()
	{
		return this.result;
	}

	public void setResult(List<T> result)
	{
		this.result = result;
	}

	public long getTotalCount()
	{
		return (long) this.totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public long getTotalPages()
	{
		if (this.totalCount < 0)
		{
			return -1L;
		}
		else
		{
			long count = (long) (this.totalCount / this.pageSize);
			if (this.totalCount % this.pageSize > 0)
			{
				++count;
			}

			return count;
		}
	}

	public boolean isHasNext()
	{
		return (long) (this.pageNo + 1) <= this.getTotalPages();
	}

	public int getNextPage()
	{
		return this.isHasNext() ? this.pageNo + 1 : this.pageNo;
	}

	public boolean isHasPre()
	{
		return this.pageNo - 1 >= 1;
	}

	public int getPrePage()
	{
		return this.isHasPre() ? this.pageNo - 1 : this.pageNo;
	}
}
