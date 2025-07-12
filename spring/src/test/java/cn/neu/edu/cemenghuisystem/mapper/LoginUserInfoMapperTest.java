package cn.neu.edu.cemenghuisystem.mapper;

import cn.neu.edu.cemenghuisystem.pojo.LoginUserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class LoginUserInfoMapperTest {

    @Autowired
    LoginUserInfoMapper loginUserInfoMapper;

    @Test
    public void selectUserInfo() {
        System.out.println(loginUserInfoMapper.getUserInfoById(5));
    }

    @Test
    public void selectPassWordById() {
        System.out.println(loginUserInfoMapper.getPasswordByUserId(5));
    }

    @Test
    public void selectByAccountUserInfo() {
        System.out.println(loginUserInfoMapper.getUserInfoByAccount("su"));
    }

    @Test
    public void selectByAccount() {
        System.out.println(loginUserInfoMapper.getUserInfoByAccount("su"));
    }

    @Test
    public void UpdateUserInfo() {
        LoginUserInfo loginUserInfo =loginUserInfoMapper.getUserInfoByAccount("su");
        loginUserInfo.setEmail("su@gmail.com");
        loginUserInfoMapper.updateUserInfo(loginUserInfo);
        System.out.println(loginUserInfoMapper.getUserInfoByAccount("su"));
    }

    @Test
    public void insertUserInfo() {
        LoginUserInfo loginUserInfo =new LoginUserInfo();
        loginUserInfo.setAccount("he");
        loginUserInfo.setCompanyName("hello");
        loginUserInfo.setCompanyContact("world");
        loginUserInfoMapper.insertUserInfo(loginUserInfo,"111");
        System.out.println(loginUserInfoMapper.getUserInfoByAccount("he"));
    }
}

