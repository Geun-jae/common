package kr.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	public static String getClientIp(HttpServletRequest request) {
		String clientIP = request.getHeader("x-forwarded-for");
		if (ObjectUtils.isEmpty(clientIP)) {
			clientIP = request.getRemoteAddr();
		}
		return clientIP;
	}
	public static String param(HttpServletRequest request, String name) {
		return param(request, name, "");
	}
	public static String param(HttpServletRequest request, String name, String def) {
		return ObjectUtils.nvl(request.getParameter(name), def).toString();
	}
}