package com.lxdmp.springtest.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import com.lxdmp.springtest.entity.UserGroup;
import com.lxdmp.springtest.entity.UserPriviledge;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement(name="user")
public final class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private Integer userId;

	private String userName;

	@JsonIgnore
	private String userPasswd;

	private List<UserGroup> userGroups = new LinkedList<UserGroup>(); // 所属的用户组

	public User()
	{
		super();
	}

	public String toString()
	{
		String s = "";
		s += String.format("name : %s, ", this.userName);
		s += String.format("password : %s, ", this.userPasswd);
		if(userGroups.isEmpty()){
			s += "no groups-joined.";
		}else{
			s += "groups-joined : ";
			Iterator<UserGroup> iter = userGroups.iterator();
			do{
				if(!iter.hasNext())
					break;
				s += String.valueOf(iter.next());
				while(iter.hasNext())
					s += ", "+String.valueOf(iter.next());
			}while(false);
			s += ".";
		}
		return s;
	}

	@XmlTransient
	public Integer getUserId(){return this.userId;}
	public void setUserId(Integer userId){this.userId=userId;}

	public String getUserName(){return this.userName;}
	public void setUserName(String userName){this.userName=userName;}

	@XmlTransient
	public String getUserPasswd(){return this.userPasswd;}
	public void setUserPasswd(String userPasswd){this.userPasswd=userPasswd;}

	public List<UserGroup> getUserGroups(){return this.userGroups;}
	public void setUserGroups(List<UserGroup> groups){this.userGroups=groups;}

	public List<UserPriviledge> getUserPriviledges()
	{
		List<UserPriviledge> priviledges = new LinkedList<UserPriviledge>();
		Set<UserPriviledge> buf = new HashSet<UserPriviledge>();
		Iterator<UserGroup> userGroupIter = this.userGroups.iterator();
		while(userGroupIter.hasNext()){
			UserGroup userGroup = userGroupIter.next();
			buf.addAll(userGroup.getGroupPriviledges());
		}
		priviledges.addAll(buf);
		return priviledges;
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
		User other = (User) obj;
		return (userId==other.userId);
	}

	@Override
	public int hashCode()
	{
		return userId%1024;
	}
}

