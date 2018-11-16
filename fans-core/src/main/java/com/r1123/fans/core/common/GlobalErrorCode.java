package com.r1123.fans.core.common;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GlobalErrorCode {
	//
	SUCESS(200, "Success"),

	ACCESSDENIED(403, "AccessDenied"),
	//
	UNAUTHORIZED(401, "Unauthorized"),
	//
	NOT_FOUND(404, "Resource not found"),
	//
	INTERNAL_ERROR(500, "Server internal error"),
	/** 依赖的三方服务错误 */
	EXTERNAL_ERROR(1500, "Server external error"),
	//
	INVALID_ARGUMENT(11001, "Invalid argument"),
	//
	ILLEGAL_STATE(12001, "Illegal state"),
	//
	UNSUPPORTED_OPERATION(13001, "Unsupported operation"),
	//
	UNKNOWN(-1, "Unknown error");

	private static final Map<Integer, GlobalErrorCode> values = new HashMap<Integer, GlobalErrorCode>();

	static {
		for (GlobalErrorCode ec : GlobalErrorCode.values()) {
			values.put(ec.errorCode, ec);
		}
	}

	private int errorCode;
	private String error;

	private GlobalErrorCode(int errorCode, String error) {
		this.errorCode = errorCode;
		this.error = error;
	}

	public static GlobalErrorCode valueOf(int code) {
		GlobalErrorCode ec = values.get(code);
		if (ec != null)
			return ec;
		return UNKNOWN;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getError() {
		return error;
	}
}
