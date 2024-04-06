package cn.com.stori.account.web.module.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 * @author hzzhouhongfei
 * @version $$ OperationTypeEnum, 2024/3/25 12:14 PM hzzhouhongfei $$
 */
@Getter
@AllArgsConstructor
public enum OperationTypeEnum
{
	/**
	 * INIT:初始化
	 */
	INIT("INIT", "初始化"),
	/**
	 * ADD:增加
	 */
	ADD("ADD", "增加"),
	/**
	 * MINUS:减少
	 */
	MINUS("MINUS", "减少");

	/**
	 * 操作代码
	 */
	private String value;

	/**
	 * 描述
	 */
	private String desc;

	public static List<String> getOperationTypeList()
	{
		List<String> OperationTypeList = new ArrayList<>();
		for (OperationTypeEnum typeEnum : values())
		{
			OperationTypeList.add(typeEnum.value);
		}
		return OperationTypeList;
	}
}
