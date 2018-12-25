package com.lxdmp.springtest.entity.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import com.lxdmp.springtest.entity.Product;
import com.lxdmp.springtest.entity.repository.ProductRepository;

@Repository("mysqlProductRepo")
public class MysqlProductRepository extends InMemoryProductRepository
{
	@Override
	public void addProduct(Product product)
	{
		// condition为mysql的关键字,用``修饰.
		product.setUnitsInOrder(0);
		product.setDiscontinued(false);

		String SQL = "INSERT INTO PRODUCTS (" + 
			"ID, NAME, DESCRIPTION, UNIT_PRICE, MANUFACTURER, CATEGORY, `CONDITION`, UNITS_IN_STOCK, UNITS_IN_ORDER, DISCONTINUED" +
			") VALUES (" + 
			":id, :name, :desc, :price, :manufacturer, :category, :condition, :inStock, :inOrder, :discontinued)";
		Map<String, Object> params = new HashMap<>();
		params.put("id", product.getProductId());
		params.put("name", product.getName());
		params.put("desc", product.getDescription());
		params.put("price", product.getUnitPrice());
		params.put("manufacturer", product.getManufacturer());
		params.put("category", product.getCategory());
		params.put("condition", product.getCondition());
		params.put("inStock", product.getUnitsInStock());
		params.put("inOrder", product.getUnitsInOrder());
		params.put("discontinued", product.getDiscontinued());
		jdbcTemplate.update(SQL, params);
	}
}

