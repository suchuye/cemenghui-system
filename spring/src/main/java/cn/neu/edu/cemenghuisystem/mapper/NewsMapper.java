package cn.neu.edu.cemenghuisystem.mapper;

import cn.neu.edu.cemenghuisystem.pojo.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {

    //TODO

    @Insert("insert into public.NEWS (title, author, createTime, status, content, summary, image) values " +
            "(#{title}, #{author}, #{createTime}, #{status}, #{content}, #{summary}, #{image})")
    void add(News news);

    @Delete("delete from public.NEWS where id=#{id}")
    void delete(int id);

    @Update("<script>" +
            "update public.news " +
            "<set>" +
            "<if test='title != null and title != \"\"'>title=#{title},</if>" +
            "<if test='author != null and author != \"\"'>author=#{author},</if>" +
            "<if test='status != 0'>status=#{status},</if>" +
            "<if test='content != null and content != \"\"'>content=#{content},</if>" +
            "<if test='summary != null and summary != \"\"'>summary=#{summary},</if>" +
            "<if test='image != null and image != \"\"'>image=#{image},</if>" +
            "</set>" +
            "where id=#{id}" +
            "</script>")
    void update(News news);

    @Select("select * from public.NEWS")
    @Results(id="newsMap", value = {
            @Result(property = "createTime",column = "create_time")

    })
    List<News> findAll();

    @Select("select * from public.NEWS where id=#{id}")
    @ResultMap("newsMap")
    News find(int id);

    @Select("<script>" +
            "SELECT * from public.NEWS" +
            "<where>" +
            "<if test='id != 0'> and id = #{id}</if>" +
            "<if test='title != null and title != \"\"'> and title = #{title}</if>" +
            "<if test='author != null and author != \"\"'> and author = #{author}</if>" +
            "<if test='createTime != null and createTime != \"\"'> and createTime = #{createTime}</if>" +
            "<if test='status != 0'> and status = #{status}</if>" +
            "<if test='content != null and content != \"\"'> and content = #{content}</if>" +
            "<if test='summary != null and summary != \"\"'> and summary = #{summary}</if>" +
            "<if test='image != null and image != \"\"'> and image = #{image}</if>" +
            "<if test='keyword != null and keyword != \"\"'> " +
            "and (title like concat('%',#{keyword},'%') " +
            "or author like concat('%',#{keyword},'%') " +
            "or content like concat('%',#{keyword},'%') " +
            "or summary like concat('%',#{keyword},'%'))" +
            "</if>" +
            "</where>" +
            "</script>")
    @ResultMap("newsMap")
    List<News> conditionFindAll(News news);
}
