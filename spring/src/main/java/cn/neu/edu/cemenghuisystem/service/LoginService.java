package cn.neu.edu.cemenghuisystem.service;

import cn.neu.edu.cemenghuisystem.mapper.LoginUserInfoMapper;
import cn.neu.edu.cemenghuisystem.pojo.BasicResponse;
import cn.neu.edu.cemenghuisystem.pojo.LoginResponse;
import cn.neu.edu.cemenghuisystem.pojo.LoginUserInfo;
import cn.neu.edu.cemenghuisystem.pojo.UserProfileResponse;
import cn.neu.edu.cemenghuisystem.utils.JWTUtil;
import cn.neu.edu.cemenghuisystem.utils.SHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Slf4j
public class LoginService {



    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private LoginUserInfoMapper  loginUserInfoMapper;

    public LoginResponse login(String account, String password) {
        try{
            LoginUserInfo loginUserInfo = loginUserInfoMapper.getUserInfoByAccount(account);
            System.out.println(loginUserInfo);
            if(loginUserInfo == null){
                log.atWarn().log("user not found");
              return new LoginResponse(404,"user not found","");
            }
            if(new SHA256Util().encrypt(password).equals(loginUserInfoMapper.getPasswordByUserId(loginUserInfo.getId()))) {
                String token=jwtUtil.generateAccessToken(loginUserInfo);
                log.atInfo().log("token generated:"+account);
                loginUserInfo.setLastLoginAt(String.valueOf(new Timestamp(System.currentTimeMillis())));
                loginUserInfoMapper.updateUserInfo(loginUserInfo);
                return new LoginResponse(200,"success",token,loginUserInfo);
            }
            return new LoginResponse(1001,"密码错误","");
        }catch (Exception e){
log.atWarn().log(e.getMessage());
return new LoginResponse(401,"user not authorized","");
        }
    }

    public BasicResponse register(String account, String password,String companyName,String companyContact) {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setAccount(account);
        loginUserInfo.setCompanyName(companyName);
        loginUserInfo.setCompanyContact(companyContact);
        System.out.println(loginUserInfoMapper.insertUserInfo(loginUserInfo, password));
        return new BasicResponse(0,"success");
    }

    public UserProfileResponse getUserProfile(Authentication authentication) {
        int userId = Integer.parseInt(authentication.getName());
        LoginUserInfo loginUserInfo=loginUserInfoMapper.getUserInfoById(userId);
        if(loginUserInfo == null){
            return new UserProfileResponse(1001,null);
        }
        return new UserProfileResponse(0,loginUserInfo);
    }
}
