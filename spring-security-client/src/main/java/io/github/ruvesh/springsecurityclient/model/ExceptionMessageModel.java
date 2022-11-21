package io.github.ruvesh.springsecurityclient.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionMessageModel {
	
	private HttpStatus status;
	private int code;
	private String message;
	private String detailMessage;
	private String path;

}
