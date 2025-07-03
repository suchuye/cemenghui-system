package cn.neu.edu.cemenghuisystem.utils;

import cn.neu.edu.cemenghuisystem.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
public class JWTUtilTest {
    @Autowired
    private Environment env;

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void test() {
        User user = new User();
        user.setId(5);
        user.setUserName("su");
        String token=jwtUtil.generateAccessToken(user);
        System.out.println(token);
        System.out.println(jwtUtil.getClaimsFromToken(token));

    }
}
