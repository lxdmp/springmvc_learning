package com.lxdmp.springtest.dao;
 
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import com.lxdmp.springtest.entity.Cart;
import com.lxdmp.springtest.dto.CartDto;
 
public interface PersonDao
{
	@Insert(value="insert into t_person(name, nick) values (#{name}, #{nick})")
	void add(Person person);

	@Delete(value="delete from t_person where id=#{id}")
	void delete(long id);
	
	@Update(value="update t_person set name=#{name}, nick=#{nick} where id=#{id}")
	void update(Person person);
	
	@Select(value="select * from t_person where id=#{id}")
	Person select(long id);
}

