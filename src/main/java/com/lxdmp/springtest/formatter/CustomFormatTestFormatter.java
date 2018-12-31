package com.lxdmp.springtest.formatter;

import org.springframework.format.Formatter;
import com.lxdmp.springtest.entity.CustomFormatTestObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomFormatTestFormatter implements Formatter<CustomFormatTestObj>
{
	private static final Logger logger = LoggerFactory.getLogger(CustomFormatTestFormatter.class);
	public static final String dummy = "_dummy";
	private final String internal_sep = "_";
	private final String external_sep = ";";

	public String print(CustomFormatTestObj object, java.util.Locale locale)
	{
		StringBuilder builder = new StringBuilder();
		String[] l = object.getId().split(this.internal_sep);
		for(int i=0; i<l.length; ++i)
		{
			if(i>0)
				builder.append(this.external_sep);
			builder.append(l[i]);
		}
		return builder.substring(0, builder.length());
	}

	public CustomFormatTestObj parse(String text, java.util.Locale locale)
	{
		if(text.equals(this.dummy))
			text = "";

		String[] l = text.split(this.external_sep);
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<l.length; ++i)
		{
			if(i>0)
				builder.append(this.internal_sep);
			builder.append(l[i]);
		}
		CustomFormatTestObj obj = new CustomFormatTestObj();
		obj.setId(builder.substring(0, builder.length()));
		return obj;
	}
}

