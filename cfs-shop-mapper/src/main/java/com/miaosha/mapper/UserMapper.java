package com.miaosha.mapper;


import com.miaosha.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
	
	@Select("select * from user where id = #{id}")
	public User getById(@Param("id") int id);

	@Insert("insert into user(id, name)values(#{id}, #{name})")
	public int insert(User user);
	
}
