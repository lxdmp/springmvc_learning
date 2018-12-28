package com.lxdmp.springtest.dao;

import org.apache.ibatis.annotations.Param;
import com.lxdmp.springtest.entity.Product;

public class ProductSqlProvider
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
		buffer.append("ID, NAME, DESCRIPTION, UNIT_PRICE, MANUFACTURER, CATEGORY, CONDITION, UNITS_IN_STOCK, UNITS_IN_ORDER, DISCONTINUED");
		buffer.append(") VALUES (");
		buffer.append(product.getProductId());
		buffer.append(", ");
		buffer.append(product.getName());
		buffer.append(", ");
		buffer.append(product.getDescription());
		buffer.append(", ");
		buffer.append(product.getUnitPrice());
		buffer.append(", ");
		buffer.append(product.getManufacturer());
		buffer.append(", ");
		buffer.append(product.getCategory());
		buffer.append(", ");
		buffer.append(product.getCondition());
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

