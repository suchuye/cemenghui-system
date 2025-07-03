package cn.neu.edu.cemenghuisystem.service;

import cn.neu.edu.cemenghuisystem.mapper.UserMapper;
import cn.neu.edu.cemenghuisystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
}
