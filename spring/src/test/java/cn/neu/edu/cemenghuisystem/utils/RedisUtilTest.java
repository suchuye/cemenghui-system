package cn.neu.edu.cemenghuisystem.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void testSetAndGet() {
        String key = "testKey";
        String value = "testValue";

        assertTrue(redisUtil.set(key, value));
        assertEquals(value, redisUtil.get(key));
    }

    @Test
    void testSetWithExpire() throws InterruptedException {
        String key = "expireKey";
        String value = "expireValue";

        assertTrue(redisUtil.set(key, value, 1, TimeUnit.SECONDS));
        assertEquals(value, redisUtil.get(key));

        Thread.sleep(1100);
        assertNull(redisUtil.get(key));
    }

    @Test
    void testDelete() {
        String key = "deleteKey";
        String value = "deleteValue";

        redisUtil.set(key, value);
        assertTrue(redisUtil.del(key));
        assertNull(redisUtil.get(key));
    }
}