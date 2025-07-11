package cn.neu.edu.cemenghuisystem.interfaces;

import cn.neu.edu.cemenghuisystem.pojo.Course;

import java.util.List;

public interface CourseSelect {
    public List<Course> getCourseList(int pageNo, int pageSize);
}
