package com.lxdmp.springtest.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import com.lxdmp.springtest.validator.ProductId;

/*
 * 通过框架与json/xml间自动转换,实体类需用注释@XmlRootElement修饰,
 * 对无需参与转化的字段,用@XmlTransient修饰其get方法,用@JsonIgnore修饰其字段成员.
 */

@XmlRootElement(name="product")
public class Product implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Pattern(regexp="P[1-9]+", message="{Pattern.Product.productId.validation}")
	//@ProductId(message="{ProductId.Product.productId.validation}")
	@ProductId
	private String productId;

	@Size(min=4, max=50, message="{Size.Product.name.validation}")
	private String name;

	@Min(value=0, message="{Min.Product.unitPrice.validation}")
	@Digits(integer=8, fraction=2, message="{Digits.Product.unitPrice.validation}")
	@NotNull(message="{NotNull.Product.unitPrice.validation}")
	private BigDecimal unitPrice;

	private String description;
	private String manufacturer;
	private String category;

	@Min(value=0, message="{Min.Product.unitsInStock.validation}")
	private long unitsInStock;

	private long unitsInOrder;
	private boolean discontinued;
	private String condition;
	private MultipartFile productImage;

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

	public MultipartFile getProductImage(){return this.productImage;}
	public void setProductImage(MultipartFile productImage){this.productImage = productImage;}

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

