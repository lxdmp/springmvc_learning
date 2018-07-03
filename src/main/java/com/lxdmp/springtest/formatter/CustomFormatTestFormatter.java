package com.lxdmp.springtest.formatter;

import org.springframework.format.Formatter;
import com.lxdmp.springtest.domain.CustomFormatTestObj;

public class CustomFormatTestFormatter implements Formatter<CustomFormatTestObj>
{
	private final String internal_sep = "_";
	private final String external_sep = ";";

	public String print(CustomFormatTestObj object, java.util.Locale locale)
	{
		StringBuilder builder = new StringBuilder();
		String[] l = object.getId().split(this.internal_sep);
		for(String item : l)
			builder.append(item).append(this.external_sep);
		return builder.substring(0, builder.length()-1);
	}

	public CustomFormatTestObj parse(String text, java.util.Locale locale)
	{
		CustomFormatTestObj obj = new CustomFormatTestObj();
		String[] l = text.split(this.external_sep);
		StringBuilder builder = new StringBuilder();
		for(String item : l)
			builder.append(item).append(this.internal_sep);
		obj.setId(builder.substring(0, builder.length()-1));
		return obj;
	}
}

