package com.example.hr.CMH;

import lombok.Data;

@Data
public class News {
    private int id;
    private String title;
    private String author;
    private String createTime;
    private int status;
    private String content;
    private String summary;
    private String image;

    private int page;
    private int pageSize;
    private String keyword;
}
