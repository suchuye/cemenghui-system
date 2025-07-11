package cn.neu.edu.cemenghuisystem.controller;

import cn.neu.edu.cemenghuisystem.pojo.Course;
import cn.neu.edu.cemenghuisystem.pojo.DataResponse;
import cn.neu.edu.cemenghuisystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    @ResponseBody
    public DataResponse getCourses(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false,defaultValue ="sort" ) String sort, @RequestParam(required = false,defaultValue = "desc") String order) {
     return courseService.getCourseListByAll(page, pageSize, sort, order);
    }

    @GetMapping("/details")
    @ResponseBody
    public DataResponse getCourseDetail(@RequestParam int id) {
        return courseService.getCourseById(id);
    }


    @PutMapping("/add")
    @ResponseBody
    public DataResponse addCourse(@RequestBody Course course) {
     return courseService.addCourse(course);
    }

    @PutMapping("/update")
    @ResponseBody
    public DataResponse updateCourse(@RequestParam int id,@RequestBody Course course) {
        return courseService.updateCourse(id,course);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public DataResponse deleteCourse(@RequestParam int id) {
        return courseService.deleteCourse(id);
    }

    @GetMapping("/search")
    @ResponseBody
    public DataResponse searchCourse() {
        //TODO
          return courseService.searchCourse();
    }

    @GetMapping("/pending")
    @ResponseBody
    public DataResponse getCoursePending(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false,defaultValue ="sort" ) String sort, @RequestParam(required = false,defaultValue = "desc") String order) {
        return null;
    }

    @PutMapping("/audit")
    @ResponseBody
    public DataResponse auditCourse(@RequestParam int id,@RequestBody Course course) {
      return courseService.auditCourse(id,course);
    }
}
