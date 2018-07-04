package com.lxdmp.springtest.formatter;

import org.apache.log4j.Logger;
import org.springframework.format.Formatter;
import com.lxdmp.springtest.domain.CustomFormatTestObj;

public class CustomFormatTestFormatter implements Formatter<CustomFormatTestObj>
{
	public static final String dummy = "_dummy";
	private static final Logger logger = Logger.getLogger(CustomFormatTestFormatter.class);
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

		CustomFormatTestObj obj = new CustomFormatTestObj();
		String[] l = text.split(this.external_sep);
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<l.length; ++i)
		{
			if(i>0)
				builder.append(this.internal_sep);
			builder.append(l[i]);
		}
		obj.setId(builder.substring(0, builder.length()));
		return obj;
	}
}

