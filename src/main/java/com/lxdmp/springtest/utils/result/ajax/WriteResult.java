package com.lxdmp.springtest.utils.result.ajax;

public class WriteResult extends BaseResult
{
	private String info;

	public WriteResult(String result)
	{
		this(result, "");
	}

	public WriteResult(String result, String info)
	{
		super(result);
		this.info = info;
	}

	public String getInfo(){return this.info;}
	public void setInfo(String info){this.info=info;}
}

