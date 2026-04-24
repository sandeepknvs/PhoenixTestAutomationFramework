package com.api.constants;

public enum Platform {
	FST(3),
	FRONT_DESK(2);
	
	private int code;
	Platform(int code)
	{
		this.code = code;
	}
	
	public int getCode()
	{
		return code;
	}
}
