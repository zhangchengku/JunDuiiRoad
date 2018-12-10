package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;

/**
 * 标准返回值
 * 
 * @author lunizhu
 */
public class BaseResult implements Serializable
{
	public String status;// 状态信息 VARCHAR 正确：Y；错误：N
	public String error_code;// 错误代码
	public String error_msg;// 错误信息

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getError_code()
	{
		return error_code;
	}

	public void setError_code(String error_code)
	{
		this.error_code = error_code;
	}

	public String getError_msg()
	{
		return error_msg;
	}

	public void setError_msg(String error_msg)
	{
		this.error_msg = error_msg;
	}

}
