package com.lxdmp.springtest.domain;

import java.io.Serializable;
import java.util.Set;
import com.lxdmp.springtest.domain.UserPriviledge;

public final class UserGroup implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int groupId;
	private String groupName;
	private Set<UserPriviledge> groupPriviledges; // 该用户组具有的权限

	public UserGroup()
	{
		super();
	}

	public int getGroupId(){return this.groupId;}
	public void setGroupId(int groupId){this.groupId=groupId;}

	public String getGroupName(){return this.groupName;}
	public void setGroupName(String groupName){this.groupName=groupName;}

	public Set<UserPriviledge> getGroupPriviledges(){return this.groupPriviledges;}
	public void setGroupPriviledges(Set<UserPriviledge> groupPriviledges){this.groupPriviledges=groupPriviledges;}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroup other = (UserGroup) obj;
		return (groupId==other.groupId);
	}

	@Override
	public int hashCode()
	{
		return groupId%1024;
	}
}

