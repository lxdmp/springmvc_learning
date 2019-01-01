package com.lxdmp.springtest.entity.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.lxdmp.springtest.entity.Cart;
import com.lxdmp.springtest.entity.CartItem;
import com.lxdmp.springtest.entity.Product;
import com.lxdmp.springtest.entity.repository.CartRepository;
import com.lxdmp.springtest.dto.CartDto;
import com.lxdmp.springtest.dto.CartItemDto;
import com.lxdmp.springtest.service.ProductService;

@Repository("hsqlCartRepo")
public class InMemoryCartRepository extends BaseRepository implements CartRepository
{
	@Autowired
	protected ProductService productService;

	@Override
	public void create(CartDto cartDto) // 创建购物车
	{
		String INSERT_CART_SQL = "INSERT INTO CART(ID) VALUES (:id)";
		Map<String, Object> cartParams = new HashMap<String, Object>();
		cartParams.put("id", cartDto.getId());
		jdbcTemplate.update(INSERT_CART_SQL, cartParams);
	}

	@Override
	public Cart read(String cartId) // 获取购物车
	{
		String SQL = "SELECT * FROM CART WHERE ID = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", cartId);
		CartMapper cartMapper = new CartMapper(jdbcTemplate, productService);
		try {
			return jdbcTemplate.queryForObject(SQL, params, cartMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void delete(String cartId) // 删除购物车
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cartId", cartId);

		{
			String SQL_DELETE_CART_ITEM = "DELETE FROM CART_ITEM WHERE CART_ID=:cartId";
			jdbcTemplate.update(SQL_DELETE_CART_ITEM, params);
		}

		{
			String SQL_DELETE_CART = "DELETE FROM CART WHERE ID = :cartId";
			jdbcTemplate.update(SQL_DELETE_CART, params);
		}
	}

	@Override
	public void deleteItem(String cartId, String productId) // 删除(某个)购物车项
	{
		String SQL_DELETE_CART_ITEM = "delete from CART_ITEM where CART_ID:=cartId and PRODUCT_ID:=productId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cartId", cartId);
		params.put("productId", productId);
		jdbcTemplate.update(SQL_DELETE_CART_ITEM, params);
	}
	
	@Override
	public void updateItem(String cartId, String productId, int updatedNum) // 购物车中加入某种货物
	{
		String SQL = 
			"INSERT INTO CART_ITEM (PRODUCT_ID, CART_ID, QUANTITY) VALUES " + 
			"(:productId, :cartId, :quantity)";
		Map<String, Object> cartItemsParams = new HashMap<String, Object>();
		cartItemsParams.put("quantity", updatedNum);
		cartItemsParams.put("productId", productId);
		cartItemsParams.put("cartId", cartId);
		jdbcTemplate.update(SQL, cartItemsParams);
	}

	@Override
	public void modifyItem(String cartId, String productId, int modifiedNum) // 购物车中调整某种货物
	{
		String SQL = "UPDATE CART_ITEM SET QUANTITY = :quantity WHERE CART_ID = :cartId AND PRODUCT_ID = :productId";
		Map<String, Object> cartItemsParams = new HashMap<String, Object>();
		cartItemsParams.put("quantity", modifiedNum);
		cartItemsParams.put("productId", productId);
		cartItemsParams.put("cartId", cartId);
		jdbcTemplate.update(SQL, cartItemsParams);
	}

	@Override
	public void removeItem(String cartId, String productId) // 购物车中删除某项货物
	{
		String SQL_DELETE_CART_ITEM = "DELETE FROM CART_ITEM WHERE CART_ID=:cartId AND PRODUCT_ID=:productId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cartId", cartId);
		params.put("productId", productId);
		jdbcTemplate.update(SQL_DELETE_CART_ITEM, params);
	}

	protected final class CartMapper implements RowMapper<Cart>
	{
		private CartItemMapper cartItemMapper;
		private NamedParameterJdbcTemplate jdbcTemplate;

		public CartMapper(
			NamedParameterJdbcTemplate jdbcTemplate, 
			ProductService productService) 
		{
			this.jdbcTemplate = jdbcTemplate;
			cartItemMapper = new CartItemMapper(productService);
		}

		@Override
		public Cart mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String cartId = rs.getString("ID");
			Cart cart = new Cart(cartId);
			String SQL = String.format("SELECT * FROM CART_ITEM WHERE CART_ID = '%s'", cartId);
			List<CartItem> cartItems = jdbcTemplate.query(SQL, this.cartItemMapper);
			cart.setCartItems(cartItems);
			return cart;
		}
	}

	protected final class CartItemMapper implements RowMapper<CartItem>
	{
		private ProductService productService;
		public CartItemMapper(ProductService productService)
		{
			this.productService = productService;
		}

		@Override
		public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			CartItem cartItem = new CartItem(rs.getInt("ID"));
			cartItem.setProduct(productService.getProductById(rs.getString("PRODUCT_ID")));
			cartItem.setQuantity(rs.getInt("QUANTITY"));
			return cartItem;
		}
	}
}

