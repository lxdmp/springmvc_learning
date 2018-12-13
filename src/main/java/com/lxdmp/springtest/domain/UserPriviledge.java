package com.lxdmp.springtest.domain;

import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement(name="priviledge")
public final class UserPriviledge implements Serializable, GrantedAuthority
{
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private int priviledgeId;

	private String priviledgeName;

	public UserPriviledge()
	{
		super();
	}

	public String toString()
	{
		return priviledgeName;
	}

	@XmlTransient
	public int getPriviledgeId(){return this.priviledgeId;}
	public void setPriviledgeId(int priviledgeId){this.priviledgeId=priviledgeId;}

	public String getPriviledgeName(){return this.priviledgeName;}
	public void setPriviledgeName(String priviledgeName){this.priviledgeName=priviledgeName;}

	@Override
	public String getAuthority()
	{
		return this.priviledgeName;
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
		UserPriviledge other = (UserPriviledge) obj;
		return (priviledgeId==other.priviledgeId);
	}

	@Override
	public int hashCode()
	{
		return priviledgeId%1024;
	}
}
