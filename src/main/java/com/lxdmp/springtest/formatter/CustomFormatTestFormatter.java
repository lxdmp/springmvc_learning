package com.lxdmp.springtest.formatter;

import org.springframework.format.Formatter;
import com.lxdmp.springtest.domain.CustomFormatTestObj;

public class CustomFormatTestFormatter implements Formatter<CustomFormatTestObj>
{
	public String print(CustomFormatTestObj object, java.util.Locale locale)
	{
		return object.getId()+"_abc";
	}

	public CustomFormatTestObj parse(String text, java.util.Locale locale)
	{
		CustomFormatTestObj obj = new CustomFormatTestObj();
		obj.setId(text+"_abc");
		return obj;
	}
}

