package cn.neu.edu.cemenghuisystem.service;

import cn.neu.edu.cemenghuisystem.mapper.NewsMapper;
import cn.neu.edu.cemenghuisystem.pojo.News;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NewsService {

    //TODO

    @Resource
    NewsMapper newsMapper;

    public void add(News object){
        try{
            newsMapper.add(object);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void delete(int id){
        newsMapper.delete(id);
    }

    public void update(News object){
        newsMapper.update(object);
    }

    public List<News> findAll(){
        return newsMapper.findAll();
    }

    public News find(int id){
        return newsMapper.find(id);
    }

    public List<News> conditionFindAll(News object) {
        return newsMapper.conditionFindAll(object);
    }
}
