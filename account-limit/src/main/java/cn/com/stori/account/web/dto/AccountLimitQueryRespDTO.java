package cn.com.stori.account.web.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 额度查询结果返回
 * @author hzzhouhongfei
 * @version $$ AccountLimitQueryReqDTO, 2024/3/25 3:31 PM hzzhouhongfei $$
 */
@Data
public class AccountLimitQueryRespDTO extends PageResponseDTO<AccountLimitDTO> implements Serializable
{
	private static final long serialVersionUID = -3453804995535048535L;

	public AccountLimitQueryRespDTO()
	{
	}

	public AccountLimitQueryRespDTO(long start, long totalSize, long pageSize, List<AccountLimitDTO> data)
	{
		super(start, totalSize, pageSize, data);
	}

}
