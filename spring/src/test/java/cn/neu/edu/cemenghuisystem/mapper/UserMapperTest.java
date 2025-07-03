package cn.neu.edu.cemenghuisystem.mapper;

import cn.neu.edu.cemenghuisystem.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void selectUser() {
        System.out.println(userMapper.getAllUsers());
    }

    @Test
    public void selectUserById() {
        System.out.println(userMapper.getUserById(5));
    }
}
