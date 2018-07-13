package com.lxdmp.springtest.utils.tag;
 
import java.io.IOException;
 
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
 
import org.apache.commons.lang.StringUtils;

public class PaginatorTag extends SimpleTagSupport
{
	private String href; // url
	private String cparam; //当前页
	private int curr;
	private String sparam; //每页条数
	private int size;
	private int total; //总页数
	private int beginNavigatorIndex = -1; // 可选设置
	private int endNavigatorIndex = -1;
	
	@Override
	public void doTag() throws JspException, IOException
	{
		super.doTag();
		JspWriter out = getJspContext().getOut();
		
		if(StringUtils.isEmpty(cparam))
			cparam = "currentPage";
		if(StringUtils.isEmpty(sparam))
			sparam = "pageSize";
		
		if(!href.endsWith("?") && !href.endsWith("&"))
		{
			if(href.indexOf("?") == -1)
				href = href + "?";
			else
				href = href + "&";
		}
		
		if(curr <= 0)
			curr = 1;
		else if(curr > total)
			curr = total;

		out.append("<span>");

		// 首页
		if(curr==1)
			out.append("首页");
		else
			href(out, href, 1, "首页");

		// 上一页
		out.append(" | ");
		if(curr==1)
			out.append("上一页");
		else
			href(out, href, curr-1, "上一页");

		// navigator部分
		if( beginNavigatorIndex>0 && 
			endNavigatorIndex>0 && 
			beginNavigatorIndex<=endNavigatorIndex )
		{
			if(1<beginNavigatorIndex)
			{
				out.append(" | ");
				out.append(" ... ");
			}

			for(int index=beginNavigatorIndex; index<=endNavigatorIndex; ++index)
			{
				out.append(" | ");
				if(index==curr)
					out.append(String.valueOf(index));
				else
					href(out, href, index, String.valueOf(index));
			}

			if(endNavigatorIndex<total)
			{
				out.append(" | ");
				out.append(" ... ");
			}
		}

		// 下一页
		out.append(" | ");
		if(curr==total)
			out.append("下一页");
		else
			href(out, href, curr+1, "下一页");

		// 末页
		out.append(" | ");
		if(curr==total)
			out.append("末页");
		else
			href(out, href, total, "末页");

		out.append("</span>");

		out.append("<span>第");
		out.append(curr + "/" + total);
		out.append("页</span>");
	}
	
	private void href(JspWriter out, String href, int curr, String title) throws IOException
	{
		out.append("<a href=\"")
			.append(href)
			.append(cparam).append("=").append(String.valueOf(curr))
			.append("&")
			.append(sparam).append("=").append(String.valueOf(size))
			.append("\">")
			.append(title).append("</a>");
	}
 
	// 当前页
	public int getCurr()
	{
		return curr;
	}
 
	public void setCurr(int curr)
	{
		this.curr = curr;
	}
 
	// 总页数
	public int getTotal()
	{
		return total;
	}
 
	public void setTotal(int total)
	{
		this.total = total;
	}
 
	// 链接
	public String getHref()
	{
		return href;
	}
 
	public void setHref(String href)
	{
		this.href = href;
	}
 
	// 当前页(字串)
	public String getCparam()
	{
		return cparam;
	}
 
	public void setCparam(String cparam)
	{
		this.cparam = cparam;
	}
 
	// 每页条数(字串)
	public String getSparam()
	{
		return sparam;
	}
 
	public void setSparam(String sparam)
	{
		this.sparam = sparam;
	}
 
	// 每页条数
	public int getSize()
	{
		return size;
	}
 
	public void setSize(int size)
	{
		this.size = size;
	}

	// navigator
	public int getBeginNavigatorIndex()
	{
		return beginNavigatorIndex;
	}

	public void setBeginNavigatorIndex(int beginNavigatorIndex)
	{
		this.beginNavigatorIndex = beginNavigatorIndex;
	}

	public int getEndNavigatorIndex()
	{
		return endNavigatorIndex;
	}

	public void setEndNavigatorIndex(int endNavigatorIndex)
	{
		this.endNavigatorIndex = endNavigatorIndex;
	}
}

