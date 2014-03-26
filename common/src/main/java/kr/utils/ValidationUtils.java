package kr.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
	public static String checkEmail( String email ) {
		try{
			if(ObjectUtils.isEmpty(email)) {
				throw new Exception();
			}
			Pattern p =Pattern.compile("^\\.|^\\@");
			Matcher m =p.matcher(email);
			if (m.find()) {
				throw new Exception();
			}

			p =Pattern.compile("^www\\.");
			m =p.matcher(email);
			if (m.find()) {
				throw new Exception();
			}
			p = Pattern.compile("[^A-Za-z0-9\\.\\@_\\-~#]+");
			m = p.matcher(email);

			StringBuffer sb = new StringBuffer();
			boolean result = m.find();
			boolean deletedIllegalChars = false;
			while(result) {
				deletedIllegalChars = true;
				m.appendReplacement(sb, "");
				result = m.find();
			}
			m.appendTail(sb);
			email = sb.toString();
			if (deletedIllegalChars) {
				throw new Exception();
			}
		} catch(Exception e) {
			email = "";
		}
		return email;
	}
}