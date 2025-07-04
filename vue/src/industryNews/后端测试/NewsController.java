package com.example.hr.CMH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hr.bean.News;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("news")
@Slf4j
public class NewsController {
    @Autowired
    NewsService service;

    @RequestMapping("/add")
    public void add(News object){
        service.add(object);
    }

    @RequestMapping("/delete")
    public void delete(int id){
        service.delete(id);
    }

    @RequestMapping("/update")
    public void update(News object){
        service.update(object);
    }

    @RequestMapping("/findAll")
    public List<News> findAll(){
        return service.findAll();
    }

    @RequestMapping("/findById")
    public News find(int id){
        return service.find(id);
    }

    @RequestMapping("/conditionFindAll")
    public List<News> conditionFindAll(News object) {
        return service.conditionFindAll(object);
    }

    @RequestMapping("/findPage")
    public PageInfo<News> findByConditionWithAllByPage(News object){
        // 使用分页助手
        PageHelper.startPage(object.getPage(), object.getPageSize());
        List<News> list = service.conditionFindAll(object);
        // 返回带有分页参数的列表数据
        PageInfo<News> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
