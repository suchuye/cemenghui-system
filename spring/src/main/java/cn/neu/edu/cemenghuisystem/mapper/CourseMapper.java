package cn.neu.edu.cemenghuisystem.mapper;

import cn.neu.edu.cemenghuisystem.pojo.Course;
import cn.neu.edu.cemenghuisystem.sqlProvider.CourseSQLProvide;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Results(id="courseResultMap",value = {
            @Result(property = "coverUrl",column = "cover_url"),
            @Result(property = "videoUrl",column = "video_url"),
            @Result(property = "sortOrder",column = "sort_order"),
            @Result(property = "auditStatus",column = "audit_status"),
            @Result(property = "createdTime",column = "create_time")
    })

    @Select("select * from public.COURSE order by create_time DESC limit #{pageSize} offset #{start}")
    public List<Course> selectAllCoursesByPageOrderByTimeDesc(int start, int pageSize);

    @Select("select * from public.COURSE order by create_time ASC limit #{pageSize} offset #{start}")
    @ResultMap("courseResultMap")
    public  List<Course> selectAllCoursesByPageOrderByTimeASC(int start, int pageSize);

    @Select("select * from public.COURSE order by sort_order DESC limit #{pageSize} offset #{start}")
    @ResultMap("courseResultMap")
    public  List<Course> selectAllCoursesByPageOrderBySortDESC(int start, int pageSize);

    @Select("select * from public.COURSE order by sort_order ASC limit #{pageSize} offset #{start}")
    @ResultMap("courseResultMap")
    public  List<Course> selectAllCoursesByPageOrderBySortASC(int start, int pageSize);

    @Select("select * from public.COURSE where id=#{id}")
    @ResultMap("courseResultMap")
    public Course selectById(int id);


    @SelectProvider(type = CourseSQLProvide.class, method = "selectCoursesByPage")
    @ResultMap("courseResultMap")
    public List<Course> selectCoursesByPage(
            @Param("page") Integer page,
            @Param("pageSize") Integer pageSize,
            @Param("sort") String sort,
            @Param("order") String order,
            @Param("name") String name,
            @Param("sortOrder") Integer sortOrder,
            @Param("author") String author
    );


    @Insert("INSERT INTO public.COURSE (\n" +
            "        name, \n" +
            "        cover_url, \n" +
            "        description, \n" +
            "        sort_order, \n" +
            "        video_url, \n" +
            "        author, \n" +
            "        audit_status, \n" +
            "        create_time\n" +
            "    )\n" +
            "    VALUES (\n" +
            "        #{name}, \n" +
            "        #{coverUrl}, \n" +
            "        #{description}, \n" +
            "        #{sortOrder}, \n" +
            "        #{videoUrl}, \n" +
            "        #{author}, \n" +
            "        #{auditStatus}, \n" +
            "        #{createdTime, jdbcType=TIMESTAMP}\n" +
            "    )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertCourse(Course course);

    @Update("UPDATE public.COURSE \n" +
            "SET name = #{name}, \n" +
            "    cover_url = #{coverUrl}, \n" +
            "    description = #{description}, \n" +
            "    sort_order = #{sortOrder}, \n" +
            "    video_url = #{videoUrl}, \n" +
            "    author = #{author}, \n" +
            "    audit_status = #{auditStatus}, \n" +
            "    create_time = #{createdTime, jdbcType=TIMESTAMP} \n" +
            "WHERE id = #{id}")
    public void updateCourse(Course course);

    @Delete("DELETE FROM COURSE\n" +
            "        WHERE id = #{courseId}")
    public void deleteCourseById(int courseId);
}
