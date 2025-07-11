package cn.neu.edu.cemenghuisystem.service;

import cn.neu.edu.cemenghuisystem.mapper.CourseMapper;
import cn.neu.edu.cemenghuisystem.pojo.Course;
import cn.neu.edu.cemenghuisystem.pojo.DataResponse;
import cn.neu.edu.cemenghuisystem.pojo.InfoList;
import cn.neu.edu.cemenghuisystem.pojo.Pagination;
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


    @Transactional(readOnly = true)
    public DataResponse getCourseListByAll(int page, int pageSize, String sort, String order) {
        try {

            List<Course> courseList = courseMapper.selectCoursesByPage(page,pageSize,sort,order,null,null,null,null);
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
            course.setAuditStatus("待审核");
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

    public DataResponse searchCourse(int page, int pageSize, String sort, String order,String name,int sortOrder,String author) {
        try{
           List<Course> list=courseMapper.selectCoursesByPage(page,pageSize,sort,order,name,sortOrder,author,null);
           int total=courseMapper.getNumByPage(sort,order,name,sortOrder,author,null).size();
            Pagination pagination=new Pagination(total,page,pageSize);
           InfoList infoList=new InfoList(pagination,list);
           return new DataResponse(200,"success",infoList);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new DataResponse(500, e.getMessage(), null);
        }
    }

    public DataResponse getPendingCourseList(int page, int pageSize, String sort, String order) {
        try{
            List<Course> list=courseMapper.selectCoursesByPage(page,pageSize,sort,order,null,null,null,"待审核");
            return new DataResponse(200,"success",list);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new DataResponse(500, e.getMessage(), null);
        }
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
