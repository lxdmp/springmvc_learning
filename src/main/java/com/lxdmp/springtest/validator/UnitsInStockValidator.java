package com.lxdmp.springtest.validator;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.lxdmp.springtest.entity.Product;
import org.apache.log4j.Logger;

@Component
public class UnitsInStockValidator implements Validator
{
	private static final Logger logger = Logger.getLogger(UnitsInStockValidator.class);

	public boolean supports(Class<?> clazz)
	{
		return Product.class.isAssignableFrom(clazz);
		//return Product.class.equals(clazz);
	}

	public void validate(Object target, Errors errors)
	{
		final double unit_price_threshold = 1000.0;
		final int units_in_stock_threshold = 99;

		Product product = (Product)target;
		
		if(product.getDescription().equals(product.getManufacturer()))
		/*
		if( product.getUnitPrice()!= null && 
			new BigDecimal(unit_price_threshold).compareTo(product.getUnitPrice())<=0 &&
			product.getUnitsInStock()>units_in_stock_threshold )
		*/
		{
			errors.rejectValue(/*"unitsInStock"*/"description",
				"com.lxdmp.springtest.validator.UnitsInStockValidator.message", 
				new String[]{""+unit_price_threshold, ""+units_in_stock_threshold}, 
				this.getClass()+" validate failed"
			);
		}
	}
}

