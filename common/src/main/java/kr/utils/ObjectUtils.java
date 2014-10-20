package kr.utils;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class ObjectUtils {
	/**
	 * Null 체크
	 * @param obj	해당 Object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if(obj instanceof String) {
			return obj == null || "".equals(obj.toString().trim());
		} else if(obj instanceof List) {
			return obj == null || ((List)obj).isEmpty();
		} else if(obj instanceof Map) {
			return obj == null || ((Map)obj).isEmpty();
		} else if(obj instanceof Object[]) {
			return obj == null || Array.getLength(obj)==0;
		} else {
			return obj == null;
		}
	}

	/**
	 * Null 값이면 치환 문자열로 변경
	 * @param obj	체크 Object
	 * @param def	치환 Object
	 * @return
	 */
	public static Object nvl(Object obj, Object def) {
		return !isEmpty(obj)?obj:def;
	}
}