package top.tlinx.ticket_management.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import top.tlinx.ticket_management.pojo.User;

public interface UserMapper {
    @Select("select * from user where uid = #{id}")
    User selectByid(int id);
    @Select("select * from user where username = #{username}")
    User selectByname(String username);

    @Insert("insert into user values (#{uid}, #{username}, #{password}, #{limit}, #{email})")
    void insert(User user);
}
