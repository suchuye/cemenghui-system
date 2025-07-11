package cn.neu.edu.cemenghuisystem.Service;

import cn.neu.edu.cemenghuisystem.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseServiceMap {

    @Autowired
    private CourseService courseService;

    @Test
    public void test() {
       courseService.getCourseListByAll(1,1,"sort","desc") ;
    }
}
