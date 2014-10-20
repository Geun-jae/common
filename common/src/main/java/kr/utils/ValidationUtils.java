package kr.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
	/**
	 * 이메일 체크
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		boolean isEmail = false;
		try{
			if(ObjectUtils.isEmpty(email)) {
				throw new Exception();
			}
			Pattern pattern = Pattern.compile("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$");
			Matcher matcher = pattern.matcher(email);
			if (matcher.find()) {
				isEmail = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			isEmail = false;
		}
		return isEmail;
	}

	/**
	 * 전화번호 체크
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		boolean isPhone = false;
		try{
			if(ObjectUtils.isEmpty(phone)) {
				throw new Exception();
			}
			phone = NumberUtils.fmtDigit(phone);
			Pattern pattern = Pattern.compile("(^0[1-9][0-9]?)([1-9][0-9]{2,3})([0-9]{4})$");
			Matcher matcher = pattern.matcher(phone);
			if (matcher.find()) {
				isPhone = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			isPhone = false;
		}
		return isPhone;
	}

}