package cn.com.stori.account.web.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author hzzhouhongfei
 * @version $$ DateUtil, 2024/3/23 7:34 AM hzzhouhongfei $$
 */
public class DateUtil
{
	public static final String FMT_DATE_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static final String FMT_DATE_YYYYMMDDHH = "yyyyMMddHH";
	public static final String FMT_DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String LONG_TERM_TIMESTAMP = "9999-12-31 23:59:59";

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Timestamp getCurrentTime()
	{
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取默认的长期timestamp
	 */
	public static Timestamp getDefaultLongTermTimestamp()
	{
		return formatToTimestamp(LONG_TERM_TIMESTAMP, FMT_DATE_YYYYMMDD_HHMMSS);
	}

	/**
	 * 将字符串格式化为日期对象
	 *
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Timestamp formatToTimestamp(String dateStr, String formatStr)
	{
		try
		{
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			return new Timestamp(format.parse(dateStr).getTime());
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	/**
	 * 按自定义日期格式格式化日期
	 *
	 * @param target
	 * @param format
	 * @return 格式化后的日期字符串，如果传入的日期对象为NULL，返回空字符串
	 */
	public static String formatDate(Date target, String format)
	{
		if (target == null)
		{
			return "";
		}
		return new SimpleDateFormat(format).format(target);
	}
}
