package com.lxdmp.springtest.domain;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import com.lxdmp.springtest.domain.UserGroup;
import com.lxdmp.springtest.domain.UserPriviledge;

public final class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	private String userPasswd;
	private Set<UserGroup> userGroups; // 所属的用户组

	public User()
	{
		super();
	}

	public int getUserId(){return this.userId;}
	public void setUserId(int userId){this.userId=userId;}

	public String getUserName(){return this.userName;}
	public void setUserName(String userName){this.userName=userName;}

	public String getUserPasswd(){return this.userPasswd;}
	public void setUserPasswd(String userPasswd){this.userPasswd=userPasswd;}

	public Set<UserGroup> getUserGroups(){return this.userGroups;}
	public void setUserGroups(Set<UserGroup> groups){this.userGroups=groups;}

	public Set<UserPriviledge> getUserPriviledges()
	{
		Set<UserPriviledge> priviledges = new HashSet<UserPriviledge>();
		Iterator<UserGroup> userGroupIter = this.userGroups.iterator();
		while(userGroupIter.hasNext()){
			UserGroup userGroup = userGroupIter.next();
			priviledges.addAll(userGroup.getGroupPriviledges());
		}
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

