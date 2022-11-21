package io.github.ruvesh.springsecurityclient.util;

import javax.servlet.http.HttpServletRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtil {
	
	public static String prepareApplicationUrlFromHttpRequest(final HttpServletRequest request) {
		return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				
	}

}
