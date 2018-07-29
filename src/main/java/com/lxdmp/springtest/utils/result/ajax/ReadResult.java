package com.lxdmp.springtest.utils.result.ajax;

public class ReadResult<T> extends BaseResult
{
	private String info;
	private T data;

	public ReadResult(String result, T data)
	{
		this(result, "", data);
	}

	public ReadResult(String result, String info)
	{
		this(result, info, null);
	}

	public ReadResult(String result, String info, T data)
	{
		super(result);
		this.info = info;
		this.data = data;
	}

	public String getInfo(){return this.info;}
	public void setInfo(String info){this.info=info;}

	public T getData(){return this.data;}
	public void setData(T data){this.data=data;}
}

