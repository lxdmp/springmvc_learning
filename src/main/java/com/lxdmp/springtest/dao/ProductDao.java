package com.lxdmp.springtest.dao;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import com.lxdmp.springtest.entity.Product;

public interface ProductDao
{
	@Select("select * from PRODUCTS")
	@Results({
		@Result(id=true, property="productId", column="ID"), 
		@Result(property="name", column="NAME"), 
		@Result(property="description", column="DESCRIPTION"), 
		@Result(property="unitPrice", column="UNIT_PRICE"), 
		@Result(property="manufacturer", column="MANUFACTURER"), 
		@Result(property="category", column="CATEGORY"), 
		@Result(property="condition", column="CONDITION"), 
		@Result(property="unitsInStock", column="UNITS_IN_STOCK"), 
		@Result(property="unitsInOrder", column="UNITS_IN_ORDER"), 
		@Result(property="discontinued", column="DISCONTINUED")
	})
	List<Product> getAllProducts();

	@Select("select count(0) from PRODUCTS")
	int getAllProductsNum();

	@Select("select * from PRODUCTS where CATEGORY = #{category}")
	@Results({
		@Result(id=true, property="productId", column="ID"), 
		@Result(property="name", column="NAME"), 
		@Result(property="description", column="DESCRIPTION"), 
		@Result(property="unitPrice", column="UNIT_PRICE"), 
		@Result(property="manufacturer", column="MANUFACTURER"), 
		@Result(property="category", column="CATEGORY"), 
		@Result(property="condition", column="CONDITION"), 
		@Result(property="unitsInStock", column="UNITS_IN_STOCK"), 
		@Result(property="unitsInOrder", column="UNITS_IN_ORDER"), 
		@Result(property="discontinued", column="DISCONTINUED")
	})
	List<Product> getProductsByCategory(String category);

	@SelectProvider(type=ProductSqlProvider.class, method="getProductsByPage")
	@Results({
		@Result(id=true, property="productId", column="ID"), 
		@Result(property="name", column="NAME"), 
		@Result(property="description", column="DESCRIPTION"), 
		@Result(property="unitPrice", column="UNIT_PRICE"), 
		@Result(property="manufacturer", column="MANUFACTURER"), 
		@Result(property="category", column="CATEGORY"), 
		@Result(property="condition", column="CONDITION"), 
		@Result(property="unitsInStock", column="UNITS_IN_STOCK"), 
		@Result(property="unitsInOrder", column="UNITS_IN_ORDER"), 
		@Result(property="discontinued", column="DISCONTINUED")
	})
	List<Product> getProductsByPage(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

	@Select("select * from PRODUCTS where ID = #{productId}")
	@Results({
		@Result(id=true, property="productId", column="ID"), 
		@Result(property="name", column="NAME"), 
		@Result(property="description", column="DESCRIPTION"), 
		@Result(property="unitPrice", column="UNIT_PRICE"), 
		@Result(property="manufacturer", column="MANUFACTURER"), 
		@Result(property="category", column="CATEGORY"), 
		@Result(property="condition", column="CONDITION"), 
		@Result(property="unitsInStock", column="UNITS_IN_STOCK"), 
		@Result(property="unitsInOrder", column="UNITS_IN_ORDER"), 
		@Result(property="discontinued", column="DISCONTINUED")
	})
	Product getProductById(String productId);

	@Update("update PRODUCTS set UNITS_IN_STOCK = #{unitsInStock} WHERE ID = #{id}")
	void updateStock(@Param("id") String productId, @Param("unitsInStock") long noOfUnits);

	@InsertProvider(type=ProductSqlProvider.class, method="addProduct")
	void addProduct(Product product);
}

