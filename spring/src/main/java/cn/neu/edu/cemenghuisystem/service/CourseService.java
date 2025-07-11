package cn.neu.edu.cemenghuisystem.service;

import cn.neu.edu.cemenghuisystem.interfaces.CourseSelect;
import cn.neu.edu.cemenghuisystem.mapper.CourseMapper;
import cn.neu.edu.cemenghuisystem.pojo.Course;
import cn.neu.edu.cemenghuisystem.pojo.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    private  final Map<String,CourseSelect> courseSelectMap;

    public CourseService(CourseByTimeDESC courseByTimeDESC,CourseByTimeASC courseByTimeASC,CourseBySortDESC courseBySortDESC,CourseBySortASC courseBySortASC) {
        this.courseSelectMap = new HashMap<>();
        courseSelectMap.put("timedesc",courseByTimeDESC);
        courseSelectMap.put("timeasc",courseByTimeASC);
        courseSelectMap.put("sortdesc",courseBySortDESC);
        courseSelectMap.put("sortasc",courseBySortASC);
    }

    @Transactional(readOnly = true)
    public DataResponse getCourseListByAll(int page, int pageSize, String sort, String order) {
        try {
            CourseSelect select = courseSelectMap.get(sort + order);
            if (select == null) {
                select = courseSelectMap.get("sortdesc");
            }
            List<Course> courseList = select.getCourseList(page, pageSize);
            if (courseList == null) {
                return new DataResponse(404, "未找到课程", null);
            }
            return new DataResponse(200, "success", courseList);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new DataResponse(500, e.getMessage(), null);
        }
    }

    @Transactional(readOnly = true)
    public DataResponse getCourseById(int id) {
        Course course=courseMapper.selectById(id);
        if (course == null) {
            return new DataResponse(0,"cannot find course",null);
        }
        return new DataResponse(200, "success", course);
    }

    public DataResponse addCourse(Course course) {
          try{
              course.setAuditStatus("待审核");
              courseMapper.insertCourse(course);
              return new DataResponse(200,"课程创建成功",course);
          }catch (Exception e){
              log.error(e.getMessage(),e);
              return new DataResponse(500, e.getMessage(), null);
          }
    }

    public DataResponse updateCourse(int id,Course course) {
        try{
            Course course1=courseMapper.selectById(id);
            if (course1 == null) {
                return new DataResponse(0, "cannot find course",null);
            }
            else {
                copyCourse(course,course1);
                courseMapper.updateCourse(course);
                return new DataResponse(200,"success",course);
            }
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
            return new DataResponse(500, e.getMessage(), null);
        }
    }

    public DataResponse deleteCourse(int id) {
        try{
            courseMapper.deleteCourseById(id);
            return new DataResponse(200,"课程删除成功",null);
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
            return new DataResponse(500, e.getMessage(), null);
        }
    }

    public DataResponse searchCourse(){
        return null;
    }

    public DataResponse getPendingCourseList(int page, int pageSize, String sort, String order) {
return null;
    }

    public DataResponse auditCourse(int id,Course course) {
        try{
            Course course1=courseMapper.selectById(id);
            if (course1 == null) {
                return new DataResponse(0, "cannot find course",null);
            }
            else {
                copyCourse(course,course1);
                courseMapper.updateCourse(course);
                return new DataResponse(200,"审核操作成功",course);
            }
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
            return new DataResponse(500, e.getMessage(), null);
        }
    }

    private void copyCourse(Course source, Course target) {
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getCoverUrl() != null) {
            target.setCoverUrl(source.getCoverUrl());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        if (source.getVideoUrl() != null) {
            target.setVideoUrl(source.getVideoUrl());
        }
        if (source.getSortOrder() != null) {
            target.setSortOrder(source.getSortOrder());
        }
        if (source.getAuthor() != null) {
            target.setAuthor(source.getAuthor());
        }
        if (source.getAuditStatus() != null) {
            target.setAuditStatus(source.getAuditStatus());
        }
        if (source.getCreatedTime() != null) {
            target.setCreatedTime(source.getCreatedTime());
        }
    }
}

@Service
class CourseByTimeDESC implements CourseSelect {

    @Autowired
    public CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList(int pageNo, int pageSize) {
        return courseMapper.selectAllCoursesByPageOrderByTimeDesc((pageNo-1)*pageSize,pageSize);
    }
}

@Service
class CourseByTimeASC implements CourseSelect {

    @Autowired
    public CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList(int pageNo, int pageSize) {
        return courseMapper.selectAllCoursesByPageOrderByTimeASC((pageNo-1)*pageSize,pageSize);
    }
}

@Service
class CourseBySortDESC implements CourseSelect {

    @Autowired
    public CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList(int pageNo, int pageSize) {
        return courseMapper.selectAllCoursesByPageOrderBySortDESC((pageNo-1)*pageSize,pageSize);
    }
}
@Service
class CourseBySortASC implements CourseSelect {

    @Autowired
    public CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList(int pageNo, int pageSize) {
        return courseMapper.selectAllCoursesByPageOrderBySortASC((pageNo-1)*pageSize,pageSize);
    }
}