package com.example.hr.CMH;

import com.example.hr.CMH.News;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NewsDao {

    @Insert("insert into news (title, author, createTime, status, content, summary, image) values " +
            "(#{title}, #{author}, #{createTime}, #{status}, #{content}, #{summary}, #{image})")
    void add(News news);

    @Delete("delete from news where id=#{id}")
    void delete(int id);

    @Update("<script>" +
        "update news " +
        "<set>" +
        "<if test='title != null and title != \"\"'>title=#{title},</if>" +
        "<if test='author != null and author != \"\"'>author=#{author},</if>" +
        "<if test='createTime != null and createTime != \"\"'>createTime=#{createTime},</if>" +
        "<if test='status != 0'>status=#{status},</if>" +
        "<if test='content != null and content != \"\"'>content=#{content},</if>" +
        "<if test='summary != null and summary != \"\"'>summary=#{summary},</if>" +
        "<if test='image != null and image != \"\"'>image=#{image},</if>" +
        "</set>" +
        "where id=#{id}" +
        "</script>")
    void update(News news);

    @Select("select * from news")
    List<News> findAll();

    @Select("select * from news where id=#{id}")
    News find(int id);

    @Select("<script>" +
            "SELECT * from news " +
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
    List<News> conditionFindAll(News news);
}