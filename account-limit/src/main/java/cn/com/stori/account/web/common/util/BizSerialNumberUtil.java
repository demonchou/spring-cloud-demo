package cn.com.stori.account.web.common.util;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

import cn.com.stori.account.web.module.domain.mapper.SysSequenceMapper;
import cn.com.stori.account.web.module.service.impl.SysSequenceServiceImpl;

/**
 * 业务序列号生成工具 日期+前缀+业务序列串，形如：2024032623AC00004654
 * @author hzzhouhongfei
 * @version $$ BizSerialNumberUtil, 2024/3/24 5:43 PM hzzhouhongfei $$
 */
public class BizSerialNumberUtil
{
	private static final SysSequenceMapper sysSequenceMapper;
	private static final SysSequenceServiceImpl sysSequenceService;
	private static final String BIZ_SERIAL_NUMBER_SEQ_FORMAT = "00000000";
	/**
	 * 最大值为999,999,999
	 */
	private static final String BIZ_SERIAL_LONG_NUMBER_SEQ_FORMAT = "000000000";
	private static final String STORI_ACC_SEQ_NAME = "STORI_ACC_SEQ";

	static
	{
		sysSequenceMapper = SpringContextUtil.getBean("sysSequenceMapper");
		sysSequenceService = SpringContextUtil.getBean("sysSequenceService");
	}

	/**
	 * 根据指定序列名及业务标志，生成相应业务流水号
	 * @param bizLabel
	 * @return
	 */
	public static String genBizSerialNumber(String bizLabel)
	{
		DecimalFormat format = new DecimalFormat(BIZ_SERIAL_NUMBER_SEQ_FORMAT);
		String formattedSeqStr = format.format(sysSequenceService.getNextSeq(STORI_ACC_SEQ_NAME));

		String formattedCurHourStr = DateUtil.formatDate(sysSequenceMapper.getSysdate(), DateUtil.FMT_DATE_YYYYMMDDHH);

		return formattedCurHourStr + StringUtils.trimToEmpty(bizLabel) + formattedSeqStr;
	}

	public static long getNextSeq()
	{
		return sysSequenceService.getNextSeq(STORI_ACC_SEQ_NAME);
	}

	/**
	 * 业务流水号，long型
	 *
	 * @return
	 */
	public static long genBizSerialNumber()
	{
		DecimalFormat format = new DecimalFormat(BIZ_SERIAL_LONG_NUMBER_SEQ_FORMAT);
		String formattedSeqStr = format.format(sysSequenceService.getNextSeq(STORI_ACC_SEQ_NAME));
		formattedSeqStr = formattedSeqStr.substring(formattedSeqStr.length() - 9);

		String formattedCurHourStr = DateUtil.formatDate(sysSequenceMapper.getSysdate(), DateUtil.FMT_DATE_YYYYMMDDHH);

		return Long.parseLong(formattedCurHourStr + formattedSeqStr);
	}
}
