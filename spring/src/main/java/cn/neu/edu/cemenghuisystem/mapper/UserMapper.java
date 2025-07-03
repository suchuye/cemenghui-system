package cn.neu.edu.cemenghuisystem.mapper;

import cn.neu.edu.cemenghuisystem.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM public.USER")
    @Results({
            @Result(property = "userName",column = "user_name")
    })
    List<User> getAllUsers();

    @Select("SELECT * FROM public.USER WHERE id=#{id}")
    @Results({
            @Result(property = "userName",column = "user_name")
    })
    User getUserById(Integer id);
}
