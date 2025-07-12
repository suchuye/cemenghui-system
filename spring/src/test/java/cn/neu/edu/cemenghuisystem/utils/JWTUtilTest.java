package cn.neu.edu.cemenghuisystem.utils;

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
    }
}
