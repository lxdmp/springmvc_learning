package com.lxdmp.springtest.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.LinkedList;

public class Customer implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String name;
	private String address;
	private List<String> noOfOrdersMade;

	public Customer()
	{
		super();
		this.noOfOrdersMade = new LinkedList<String>();
	}
	
	public String getCustomerId(){return this.customerId;}
	public void setCustomerId(String customerId){this.customerId = customerId;}

	public String getName(){return this.name;}
	public void setName(String name){this.name=name;}

	public String getAddress(){return this.address;}
	public void setAddress(String address){this.address = address;}

	public List<String> getNoOfOrdersMade(){return this.noOfOrdersMade;}
	public void setNoOfOrdersMade(List<String> noOfOrdersMade){this.noOfOrdersMade = noOfOrdersMade;}

	public void addNoOfOrderMade(String noOfOrderMade)
	{
		this.noOfOrdersMade.add(noOfOrderMade);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime*result+((customerId==null)?0:customerId.hashCode());
		return result;
	}
}

