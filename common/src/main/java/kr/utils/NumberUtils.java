package kr.utils;

import java.text.DecimalFormat;

public class NumberUtils {
	/**
	 * String -> 천단위 Comma 타입을 반환
	 * @param str			기본
	 * @param def			null일 경우 반환할 값
	 * @param ignoreZero	0을 무시할 경우 true, 아니면 false
	 * @return
	 */
	public static String fmtComma(String str, String def, boolean ignoreZero) {
		String result = null;
		try {
			result = str.replaceAll("[^0-9\\-\\.]", "");
			if (ObjectUtils.isEmpty(result)) {
				result = null;
			} else {
				if (result.indexOf(".") >= 0) {
					double value = Double.parseDouble(result);
					if (ignoreZero && value == 0) {
						result = null;
					} else {
						DecimalFormat format = new DecimalFormat("###,##0.00");
						result = format.format(value);
					}
				} else {
					long value = Long.parseLong(result);
					if (ignoreZero && value == 0) {
						result = null;
					} else {
						DecimalFormat format = new DecimalFormat("###,###");
						result = format.format(value);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result = null;
		}
		result = ObjectUtils.nvl(result, def).toString();
		return result;
	}

	/**
	 * String 문자열을 숫자형으로 변환후 다시 String 으로 반환
	 * @param str
	 * @return
	 */
	public static String toNumberString(String str) {
		String result = "0";
		try {
			result = String.valueOf(Math.round(toStringNumber(str)));
			if (result.equals("")) {
				result = "0";
			}
		} catch(Exception ex) {
			result = "0";
		}
		return result;
	}

	/**
	 * String => Double 변환(정규식으로 숫자타입이 아닌건 모두 삭제)
	 * @param str
	 * @return
	 */
	public static double toStringNumber(String str) {
		double result = 0;
		try {
			result = Double.parseDouble(str.replaceAll("[^0-9\\-\\.]", ""));
		} catch(Exception ex) {
			result = 0;
		}
		return result;
	}

	public static String fmtDigit(String str) {
		return ObjectUtils.isEmpty(str)?"":str.replaceAll("[^0-9]", "");
	}
}