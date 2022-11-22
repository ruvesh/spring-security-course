package io.github.ruvesh.springsecurityclient.model;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestResponseModel {
	
	private HttpStatus status;
	private int code;
	private String message;
	private Object data;
	private List<Object> extras;
}
