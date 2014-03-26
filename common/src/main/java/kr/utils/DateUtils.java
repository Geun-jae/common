package kr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 일수 가감산
	 * 기본포맷 yyyyMMdd -> yyyy/MM/dd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str		날짜
	 * @param amount	가감일수
	 * @return
	 */
	public static String addDays(String str, int amount) {
		return addDays(str, "yyyyMMdd", amount, "yyyy/MM/dd");
	}
	/**
	 * 일수 가감산
	 * 기본포맷 yyyyMMdd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str		날짜
	 * @param amount	가감일수
	 * @param endformat	반환포맷
	 * @return
	 */
	public static String addDays(String str, int amount, String endformat) {
		return addDays(str, "yyyyMMdd", amount, endformat);
	}
	/**
	 * 일수 가감산
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str		날짜
	 * @param orformat	기본포맷
	 * @param amount	가감일수
	 * @param endformat	반환포맷
	 * @return
	 */
	public static String addDays(String str, String orformat, int amount, String endformat) {
		String result = "";
		try {
			if (ObjectUtils.isEmpty(str)) {
				return "";
			}
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(orformat);
			Date date = format.parse(str);
			cal.setTime(date);
			cal.add(Calendar.DATE, amount);
			date = cal.getTime();
			format = new SimpleDateFormat(endformat);
			result = format.format(date);
		} catch(Exception ex) {
			ex.printStackTrace();
			result = "";
		}
		return result;
	}
	/**
	 * 월 가감산
	 * 기본포맷 yyyyMMdd -> yyyy/MM/dd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str		날짜
	 * @param amount	가감월
	 * @return
	 */
	public static String addMonths(String str, int amount) {
		return addMonths(str, "yyyyMMdd", amount, "yyyy/MM/dd");
	}
	/**
	 * 월 가감산
	 * 기본포맷 yyyyMMdd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str			날짜
	 * @param amount		가감월
	 * @param endformat		반환포맷
	 * @return
	 */
	public static String addMonths(String str, int amount, String endformat) {
		return addMonths(str, "yyyyMMdd", amount, endformat);
	}
	/**
	 * 월 가감산
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str			날짜
	 * @param orformat		기본포맷
	 * @param amount		가감월
	 * @param endformat		반환포맷
	 * @return
	 */
	public static String addMonths(String str, String orformat, int amount, String endformat) {
		String result = "";
		try {
			if (ObjectUtils.isEmpty(str)) {
				return "";
			}
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(orformat);
			cal.setTime(format.parse(str));
			cal.add(Calendar.MONTH, amount);
			format = new SimpleDateFormat(endformat);
			result = format.format(cal.getTime());
		} catch(Exception ex) {
			ex.printStackTrace();
			result = "";
		}
		return result;
	}
	/**
	 * 달의 1일
	 * 기본 포맷 yyyyMMdd -> yyyy/MM/dd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str	날짜
	 * @return
	 */
	public static String firstDay(String str) {
		return firstDay(str, "yyyyMMdd", "yyyy/MM/dd");
	}
	/**
	 * 달의 1일
	 * 기본 포맷 yyyyMMdd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str			날짜
	 * @param endformat		반환포맷
	 * @return
	 */
	public static String firstDay(String str, String endformat) {
		return firstDay(str, "yyyyMMdd", endformat);
	}
	/**
	 * 달의 1일
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str			날짜
	 * @param orformat		기본포맷
	 * @param endformat		반환포맷
	 * @return
	 */
	public static String firstDay(String str, String orformat, String endformat) {
		String result = "";
		try {
			if (ObjectUtils.isEmpty(str)) {
				return "";
			}
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(orformat);
			cal.setTime(format.parse(str));
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
			format = new SimpleDateFormat(endformat);
			result = format.format(cal.getTime());
		} catch(Exception ex) {
			ex.printStackTrace();
			result = "";
		}
		return result;
	}
	/**
	 * 마지막 일
	 * 기본 포맷 yyyyMMdd -> yyyy/MM/dd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str	날짜
	 * @return
	 */
	public static String lastDay(String str) {
		return lastDay(str, "yyyyMMdd", "yyyy/MM/dd");
	}
	/**
	 * 마지막 일
	 * 기본 포맷 yyyyMMdd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str			날짜
	 * @param endformat		반환포맷
	 * @return
	 */
	public static String lastDay(String str, String endformat) {
		return lastDay(str, "yyyyMMdd", endformat);
	}
	/**
	 * 마지막 일
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str			날짜
	 * @param orformat		기본포맷
	 * @param endformat		반환포맷
	 * @return
	 */
	public static String lastDay(String str, String orformat, String endformat) {
		String result = "";
		try {
			if (ObjectUtils.isEmpty(str)) {
				return "";
			}
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(orformat);
			cal.setTime(format.parse(str));
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DATE));
			format = new SimpleDateFormat(endformat);
			result = format.format(cal.getTime());
		} catch(Exception ex) {
			ex.printStackTrace();
			result = "";
		}
		return result;
	}
	/**
	 * 날짜 포맷 변환
	 * 기본 포맷 yyyyMMdd -> yyyy/MM/dd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str	날짜
	 * @return
	 */
	public static String changeDateFormat(String str) {
		return changeDateFormat(str , "yyyyMMdd" , "yyyy/MM/dd");
	}
	/**
	 * 날짜 포맷 변환
	 * 기본 포맷 yyyyMMdd
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str			날짜
	 * @param endformat		반환포맷
	 * @return
	 */
	public static String changeDateFormat(String str, String endformat) {
		return changeDateFormat(str , "yyyyMMdd" , endformat);
	}
	/**
	 * 날짜 포맷 변환
	 * (yyyy: 년도 , MM: 월 , dd : 일)
	 * @param str			날짜
	 * @param orformat		기본포맷
	 * @param endformat		반환포맷
	 * @return
	 */
	public static String changeDateFormat(String str, String orformat, String endformat) {
		String result = null;
		try {
			if (ObjectUtils.isEmpty(str)) {
				return "";
			}
			SimpleDateFormat format = new SimpleDateFormat(orformat);
			Date date = format.parse(str);
			format = new SimpleDateFormat(endformat);
			result = format.format(date);
		} catch (ParseException ex) {
			ex.printStackTrace();
			result = "";
		}
		return result;
	}
}