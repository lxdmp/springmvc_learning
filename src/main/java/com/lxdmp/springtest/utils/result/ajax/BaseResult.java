package com.lxdmp.springtest.utils.result.ajax;

public abstract class BaseResult
{
	private String result;

	public BaseResult(String result)
	{
		this.result = result;
	}

	public String getResult(){return this.result;}
	public void setResult(String result){this.result=result;}
}

