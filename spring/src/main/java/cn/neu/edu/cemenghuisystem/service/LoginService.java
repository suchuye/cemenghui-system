package cn.neu.edu.cemenghuisystem.service;

import cn.neu.edu.cemenghuisystem.mapper.UserMapper;
import cn.neu.edu.cemenghuisystem.pojo.LoginResponse;
import cn.neu.edu.cemenghuisystem.pojo.User;
import cn.neu.edu.cemenghuisystem.utils.JWTUtil;
import cn.neu.edu.cemenghuisystem.utils.SHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private JWTUtil jwtUtil;

    public LoginResponse login(String username, String password) {
        User user=userMapper.getUserByUserName(username);
        if(user==null){
            log.atWarn().log("user not found");
            return new LoginResponse(404,"user not found","");
        }
        try {
            if(new SHA256Util().encrypt(password).equals(user.getPassword())){
                String token=jwtUtil.generateAccessToken(user);
                log.atInfo().log("token generated:"+username);
                return new LoginResponse(200,"success",token,user);
            }
            else {
                return new LoginResponse(401,"user password error","");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
