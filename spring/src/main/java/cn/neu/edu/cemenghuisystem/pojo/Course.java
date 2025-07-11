package cn.neu.edu.cemenghuisystem.pojo;

import lombok.Data;

@Data
public class Course {
    private Integer id;
    private String name;
    private String coverUrl;
    private String description;
    private String videoUrl;
    private Integer sortOrder;
    private String author;
    private String auditStatus;
    private String createdTime;//datetime格式

}