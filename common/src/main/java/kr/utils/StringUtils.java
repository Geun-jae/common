package kr.utils;

import java.lang.Character.UnicodeBlock;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;

public class StringUtils {

	/**
	 * String Object 가 Null이면 치환
	 * @param str	체크 문자
	 * @param def	치환 문자
	 * @return
	 */
	public static String nvl(String str, String def) {
		return ObjectUtils.nvl(str, def).toString();
	}

	/**
	 * Html tag중 특수기호 치환
	 * @param str	문자
	 * @return
	 */
	public static String toHtmlTagCharacter(String str) {
		if (ObjectUtils.isEmpty(str)) {
			return "";
		}
		return str.replaceAll("<", "&lt")
				.replaceAll(">", "&gt")
				.replaceAll("'", "&#39;")
				.replaceAll("\"", "&quot;")
				.replaceAll("%", "&#37;")
				.replaceAll("*", "&#42;")
				.replaceAll(".", "&#46;")
				.replaceAll(",", "&#44;")
				.replaceAll("?", "&#63;")
				.replaceAll("@", "&#64;")
				.replaceAll("\\", "&#92;")
				.replaceAll("^", "&#94;")
				.replaceAll(
						"<(script|javascript|jscript|style|textarea|onclick|onload|onmouseover|onmouseout|onkeydown|onkeyup).*?</\1>|</?[a-zA-Z_]+.*?>",
						"")
						.replaceAll("script", "x-script")
						.replaceAll("[\"\']", "&quot;")
						.replaceAll("'", "''")
						.replaceAll("&", "&amp;")
						.replaceAll("[\"\']", "&quot;")
						.replaceAll("'", "''");
	}

	/**
	 * escape 함수
	 * @param src	문자
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
				tmp.append(j);
			} else if (j < 256) {
				tmp.append("%");
				if (j < 16) {
					tmp.append("0");
				}
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * unescape 함수
	 * @param src	문자
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;

		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * 이미지 파일 여부
	 * @param filename	파일명
	 * @return
	 */
	public static boolean isImageFile(String filename) {
		boolean result = false;
		try {
			StringBuffer name = new StringBuffer(filename);
			filename = name.reverse().toString();
			String ext = filename.substring(0, filename.indexOf("."));
			ext = new StringBuffer(ext).reverse().toString().toUpperCase();
			if (ext.equals("JPG") || ext.equals("JPEG") || ext.equals("PNG") || ext.equals("GIF") || ext.equals("BMP")) {
				result = true;
			}
		} catch(Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 한글 자르기
	 * @param str	문자열
	 * @param cut	자를려고 하는 자리수까지
	 * @return
	 */
	public static String getSubstring(String str, int cut){
		StringCharacterIterator iter = null;
		int type = 0;
		String result = null;
		try{
			//문자를 8859_1 타입에서 자르고, UTF-8로 인코딩 함
			iter = new StringCharacterIterator(new String(str.substring(0, cut).getBytes("8859_1"), "UTF-8"));
			type = Character.getType(iter.last());
			if(type == Character.OTHER_SYMBOL) {
				cut --;
			}
			//재검사
			iter.setText(str.substring(0, cut));
			type = Character.getType(iter.last());
			if(type == Character.OTHER_SYMBOL) {
				cut += 2;
			}
			//문자를 다시 잘라 리턴
			result = str.substring(0, cut) + " ...";
		} catch(Exception e){
			result = str;
		}
		return result;
	}
	/**
	 * 한글 여부
	 * @param str
	 * @return
	 */
	public static boolean isHangul(String str) {
		try {
			if (ObjectUtils.isEmpty(str)) {
				throw new Exception();
			}
			for(int i = 0 ; i < str.length() ; i++) {
				char ch = str.charAt(i);
				Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
				if(UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||
						UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) ||
						UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)) {
					return true;
				}
			}
		} catch(Exception e) {
			return false;
		}
		return false;
	}
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

	/**
	 * Exception 시 클래스 명과 함수명을 반환
	 * @param obj	호출된 object
	 * @param ex	예외
	 * @return
	 */
	public static String getClassName(Object obj, Throwable ex) {
		StringBuilder str = null;
		try {
			str = new StringBuilder();
			str.append("Class : ");
			str.append(obj.getClass().getName());
			str.append("\nFn : ");
			str.append(ex.getStackTrace()[0].getMethodName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}
}