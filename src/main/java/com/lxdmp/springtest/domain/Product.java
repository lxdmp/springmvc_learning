package com.lxdmp.springtest.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String productId;
	private String name;
	private BigDecimal unitPrice;
	private String description;
	private String manufacturer;
	private String category;
	private long unitsInStock;
	private long unitsInOrder;
	private boolean discontinued;
	private String condition;

	public Product()
	{
		super();
	}
	
	public Product(String productId, String name, BigDecimal unitPrice)
	{
		this.productId = productId;
		this.name = name;
		this.unitPrice = unitPrice;
	}

	public String getProductId(){return this.productId;}
	public void setProductId(String productId){this.productId = productId;}

	public String getName(){return this.name;}
	public void setName(String name){this.name=name;}

	public BigDecimal getUnitPrice(){return this.unitPrice;}
	public void setUnitPrice(BigDecimal unitPrice){this.unitPrice = unitPrice;}

	public String getDescription(){return this.description;}
	public void setDescription(String description){this.description=description;}

	public String getManufacturer(){return this.manufacturer;}
	public void setManufacturer(String manufacturer){this.manufacturer = manufacturer;}

	public String getCategory(){return this.category;}
	public void setCategory(String category){this.category = category;}

	public long getUnitsInStock(){return this.unitsInStock;}
	public void setUnitsInStock(long unitsInStock){this.unitsInStock = unitsInStock;}

	public long getUnitsInOrder(){return this.unitsInOrder;}
	public void setUnitsInOrder(long unitsInOrder){this.unitsInOrder = unitsInOrder;}

	public boolean getDiscontinued(){return this.discontinued;}
	public void setDiscontinued(boolean discontinued){this.discontinued = discontinued;}

	public String getCondition(){return this.condition;}
	public void setCondition(String condition){this.condition = condition;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime*result+((productId==null)?0:productId.hashCode());
		return result;
	}
}

