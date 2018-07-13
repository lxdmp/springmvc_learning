/*
 * 分页实现
 *
 * 实例化时,需要指定"总条数","一页的条数","第几页(从1开始)".
 */
package com.lxdmp.springtest.utils;
 
import java.io.Serializable;
import java.util.List;
 
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
 
public class Paginator<T> implements Serializable
{
	private final static long serialVersionUID = 1L;
	private final static int DEFAULT_NAVIGATOR_SIZE = 5;
	
    private int currentPage = 1; //当前页,从1开始
    private int pageSize = 3; //每页显示数量
    private int totalCount = 10; //总条数
    private boolean havaNextPage; // 是否有上一页
    private boolean havePrevPage; // 是否有下一页
    private int navigatorSize; // 前后显示的页数链接数量
    private List<T> items; // 存放查询结果用的list
    
    public Paginator()
	{
    }
    
    public Paginator(int totalCount, int pageSize, int currentPage)
	{
        this(totalCount, pageSize, currentPage, DEFAULT_NAVIGATOR_SIZE);
    }
 
    public Paginator(int totalCount, int pageSize, int currentPage, int navigatorSize)
	{
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.navigatorSize = navigatorSize;
    }
    
	// 页数(只读不可设)
    public int getPageCount()
	{
        int pageCount = 0;
        if(pageSize>0)
		{
            pageCount = totalCount/pageSize;
            if(totalCount%pageSize!=0)
                ++pageCount;
        }
        return pageCount;
    }
 
	// 当前页
    public int getCurrentPage()
	{
		int pageCount = this.getPageCount();
        currentPage = currentPage<pageCount ? currentPage:pageCount;
        currentPage = currentPage<1?1:currentPage;
        return currentPage;
    }

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}
 
	// 获取每页数量
    public int getPageSize()
	{
        return pageSize;
    }

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
 
	// 总条数
    public int getTotalCount()
	{
        return totalCount;
    }

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}
 
	// 获取是否有下一页
    public boolean isHaveNextPage()
	{
        this.havaNextPage = ((getPageCount() > 1) && (getPageCount() > getCurrentPage()));
		return this.havaNextPage;
    }
 
	// 获取是否有上一页
    public boolean isHavePrevPage()
	{
        this.havePrevPage = ((getPageCount() > 1) && (currentPage > 1));
        return this.havePrevPage;
    }
 
	// 显示的页面(链接)范围
    private int getNavigatorIndex(boolean isBegin)
	{
        int beginNavigatorIndex = getCurrentPage() - navigatorSize / 2;
        int endNavigatorIndex = getCurrentPage() + navigatorSize / 2;
        beginNavigatorIndex = beginNavigatorIndex<1?1:beginNavigatorIndex;
        endNavigatorIndex = endNavigatorIndex<getPageCount()?endNavigatorIndex:getPageCount();

        while( (endNavigatorIndex - beginNavigatorIndex) < navigatorSize &&
               (beginNavigatorIndex != 1 || endNavigatorIndex != getPageCount()) )
		{
            if(beginNavigatorIndex>1)
				--beginNavigatorIndex;
            else if(endNavigatorIndex < getPageCount())
                ++endNavigatorIndex;
        }
 
        if(isBegin)
            return beginNavigatorIndex;
        else
			return endNavigatorIndex;
    }
 
    public int getBeginNavigatorIndex()
	{
        return getNavigatorIndex(true);
    }
 
    public int getEndNavigatorIndex()
	{
        return getNavigatorIndex(false);
    }
 
	// 存放的内容
    public List<T> getItems()
	{
        return items;
    }
 
    public void setItems(List<T> items)
	{
        this.items = items;
    }
 
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

