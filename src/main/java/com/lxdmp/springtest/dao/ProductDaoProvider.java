package com.lxdmp.springtest.dao;

import org.apache.ibatis.annotations.Param;
import com.lxdmp.springtest.entity.Product;

public class ProductDaoProvider
{
	public String getProductsByPage(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize)
	{
		return String.format("select * from PRODUCTS limit %d,%d", 
			(pageNo-1)*pageSize, pageSize
		);
	}

	public String addProduct(Product product)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into PRODUCTS (");
		buffer.append("`ID`, `NAME`, `DESCRIPTION`, `UNIT_PRICE`, `MANUFACTURER`, `CATEGORY`, `CONDITION`, `UNITS_IN_STOCK`, `UNITS_IN_ORDER`, `DISCONTINUED`");
		buffer.append(") VALUES (");
		buffer.append(String.format("\"%s\"", product.getProductId()));
		buffer.append(", ");
		buffer.append(String.format("\"%s\"", product.getName()));
		buffer.append(", ");
		buffer.append(String.format("\"%s\"", product.getDescription()));
		buffer.append(", ");
		buffer.append(product.getUnitPrice());
		buffer.append(", ");
		buffer.append(String.format("\"%s\"", product.getManufacturer()));
		buffer.append(", ");
		buffer.append(String.format("\"%s\"", product.getCategory()));
		buffer.append(", ");
		buffer.append(String.format("\"%s\"", product.getCondition()));
		buffer.append(", ");
		buffer.append(product.getUnitsInStock());
		buffer.append(", ");
		buffer.append(product.getUnitsInOrder());
		buffer.append(", ");
		buffer.append(product.getDiscontinued());
		buffer.append(")");
		return buffer.toString();
	}
}

