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
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.mapping.FetchType;
import com.lxdmp.springtest.entity.Cart;
import com.lxdmp.springtest.entity.CartItem;
import com.lxdmp.springtest.dto.CartDto;
import com.lxdmp.springtest.dao.ProductDao;

public interface CartDao
{
	@Insert("insert into CART(ID) values (#{id})")
	void create(CartDto cartDto); // 创建购物车

	@Select("select * from CART where ID = #{cartId}")
	@Results({
		@Result(id=true, property="id", column="ID"), 
		@Result(property="cartItems", column="ID", 
			many=@Many(select="CartDao.readItems", fetchType=FetchType.LAZY)
		)
	})
	Cart read(String cartId); // 获取购物车

	@Select("select * from CART_ITEM where CART_ID = #{cartId}")
	@Results({
		@Result(id=true, property="id", column="ID"), 
		@Result(property="quantity", column="QUANTITY"), 
		@Result(property="product", column="PRODUCT_ID", 
			one=@One(select="ProductDao.getProductById", fetchType=FetchType.LAZY)
		)
	})
	List<CartItem> readItems(String cartId); // 获取购物车项

	@Delete("delete from CART where ID = #{cartId}")
	void delete(String cartId); // 删除购物车

	@Delete("delete from CART_ITEM where CART_ID = #{cartId}")
	void deleteItems(String cartId); // 删除(所有)购物车项

	@Delete("delete from CART_ITEM where CART_ID=#{cartId} and PRODUCT_ID=#{productId}")
	void deleteItem(String cartId, String productId); // 删除(某个)购物车项

	@Insert("insert into CART_ITEM(PRODUCT_ID, CART_ID, QUANTITY) VALUES (#{productId}, #{cartId}, #{updatedNum})")
	void updateItem(
		@Param("cartId") String cartId, 
		@Param("productId") String productId, 
		@Param("updatedNum") int updatedNum
	); // 购物车中设置某种货物

	@Update("update CART_ITEM set QUANTITY = #{modifiedNum} WHERE CART_ID = #{cartId} AND PRODUCT_ID = #{productId}")
	void modifyItem(
		@Param("cartId") String cartId, 
		@Param("productId") String productId, 
		@Param("modifiedNum") int modifiedNum
	); // 购物车中调整某种货物

	@Delete("delete FROM CART_ITEM WHERE CART_ID=#{cartId} AND PRODUCT_ID=#{productId}")
	void removeItem(
		@Param("cartId") String cartId, 
		@Param("productId") String productId
	); // 购物车中删除某种货物
}

