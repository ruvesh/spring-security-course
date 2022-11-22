package io.github.ruvesh.springsecurityclient.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtil {

	public static String prepareApplicationUrlFromHttpRequest(final HttpServletRequest request) {
		return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

	}

	public static Date calculateExpiryTime(int expiryTimeInMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(calendar.getTime().getTime());
	}

	public static String prepareRestEndpoint(String applicationBaseUrl, String resourceUrlEndpoint,
			Map<String, String> queryParameters) {

		StringBuilder queryStringBuilder = new StringBuilder("?");

		for (Entry<String, String> entry : queryParameters.entrySet()) {
			queryStringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}

		queryStringBuilder.deleteCharAt(queryStringBuilder.lastIndexOf("&"));
		return applicationBaseUrl + resourceUrlEndpoint + queryStringBuilder.toString();
	}

}
