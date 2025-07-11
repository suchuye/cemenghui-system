package cn.neu.edu.cemenghuisystem.mapper;

import cn.neu.edu.cemenghuisystem.pojo.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CourseMapperTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void getCourse() {
        System.out.println(courseMapper.selectAllCoursesByPageOrderByTimeDesc(1,1));
    }
    @Test
    public void getCourseAsc() {
        System.out.println(courseMapper.selectAllCoursesByPageOrderByTimeASC(1,2));
    }

    @Test
    public void insertCourse() {
Course course = createCourse();
courseMapper.insertCourse(course);
        System.out.println(courseMapper.selectById(course.getId()));
    }

    @Test
    public void updateCourse() {
        Course course =courseMapper.selectById(2);
        course.setAuthor("test");
        courseMapper.updateCourse(course);
        System.out.println(courseMapper.selectById(2));
    }

    @Test
    public void deleteCourse() {
        courseMapper.deleteCourseById(2);
        System.out.println(courseMapper.selectById(2));
    }

    private Course createCourse() {
        Course course = new Course();
        course.setId(1);
        course.setName("Java编程入门");
        course.setCoverUrl("https://example.com/covers/java_basics.jpg");
        course.setDescription("这是一门全面介绍Java编程语言基础的课程，适合编程初学者学习。");
        course.setVideoUrl("https://example.com/videos/java_basics.mp4");
        course.setSortOrder(10);
        course.setAuthor("张三");
        course.setAuditStatus("已通过");
        course.setCreatedTime("2023-01-15 09:30:00");
        return course;
    }
}
