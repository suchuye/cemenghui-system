package cn.neu.edu.cemenghuisystem.pojo;

import lombok.Data;

@Data
public class Pagination {
    private int total;
    private int page;
    private int pageSize;
    private int totalPages;

    public Pagination(int total, int page, int pageSize) {
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = (total + pageSize - 1) / pageSize;
    }
}
