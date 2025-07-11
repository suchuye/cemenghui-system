package cn.neu.edu.cemenghuisystem.sqlProvider;

import java.util.Map;

public class CourseSQLProvide {
    public String selectCoursesByPage(Map<String, Object> params) {
        Integer page = (Integer) params.get("page");
        Integer pageSize = (Integer) params.get("pageSize");
        int start = (page != null && page > 0 ? page - 1 : 0) * (pageSize != null && pageSize > 0 ? pageSize : 10);
        pageSize = pageSize != null && pageSize > 0 ? pageSize : 10;

        // 处理排序参数
        String sort = (String) params.get("sort");
        String order = (String) params.get("order");

        // 排序字段白名单校验
        if (!"sortOrder".equals(sort) && !"createdTime".equals(sort)) {
            sort = "sortOrder"; // 默认排序字段
        }

        // 映射Java属性名到数据库字段名
        String sortField = "sortOrder".equals(sort) ? "sort_order" : "create_time";

        // 排序方向白名单校验
        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)) {
            order = "desc"; // 默认排序方向
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM public.COURSE WHERE 1=1");

        // 动态添加查询条件
        String name = (String) params.get("name");
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND name LIKE CONCAT('%', #{name}, '%')");
        }

        Integer sortOrder = (Integer) params.get("sortOrder");
        if (sortOrder != null && sortOrder >= 1 && sortOrder <= 5) {
            sql.append(" AND sort_order = #{sortOrder}");
        }

        String author = (String) params.get("author");
        if (author != null && !author.trim().isEmpty()) {
            sql.append(" AND author LIKE CONCAT('%', #{author}, '%')");
        }

        // 添加排序和分页
        sql.append(" ORDER BY ").append(sortField).append(" ").append(order.toUpperCase());
        sql.append(" LIMIT ").append(pageSize).append(" OFFSET ").append(start);

        return sql.toString();
    }
}
