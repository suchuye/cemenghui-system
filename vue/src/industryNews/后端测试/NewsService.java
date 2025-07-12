package com.example.hr.CMH;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.example.hr.CMH.News;
import com.example.hr.CMH.NewsDao;

@Service
public class NewsService {
    @Resource
    NewsDao dao;

    public void add(News object){
        dao.add(object);
    }

    public void delete(int id){
        dao.delete(id);
    }

    public void update(News object){
        dao.update(object);
    }

    public List<News> findAll(){
        return dao.findAll();
    }

    public News find(int id){
        return dao.find(id);
    }

    public List<News> conditionFindAll(News object) {
        return dao.conditionFindAll(object);
    }
}