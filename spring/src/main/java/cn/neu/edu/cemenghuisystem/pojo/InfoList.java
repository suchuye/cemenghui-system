package cn.neu.edu.cemenghuisystem.pojo;

import lombok.Data;

@Data
public class InfoList {
    private Pagination pagination;
    private Object list;

    public InfoList(Pagination pagination, Object list) {
        this.pagination = pagination;
        this.list = list;
    }
}
