package cn.neu.edu.cemenghuisystem.sqlProvider;

import java.util.Map;

public class CourseCountSQLProvider {
    public String selectCoursesByPage(Map<String, Object> params) {

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

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM public.COURSE WHERE 1=1");

        // 动态添加查询条件
        String name = (String) params.get("name");
        if (name != null && !name.trim().isEmpty()&&!name.equals("null")) {
            sql.append(" AND name LIKE CONCAT('%', #{name}, '%')");
        }

        Integer sortOrder = (Integer) params.get("sortOrder");
        if (sortOrder != null && sortOrder >= 1 && sortOrder <= 5) {
            sql.append(" AND sort_order = #{sortOrder}");
        }

        String author = (String) params.get("author");
        if (author != null && !author.trim().isEmpty()&&!author.equals("null")) {
            sql.append(" AND author LIKE CONCAT('%', #{author}, '%')");
        }


        String auditStatus = (String) params.get("auditStatus");
        if (auditStatus != null && !auditStatus.trim().isEmpty()&&!auditStatus.equals("null")) {
            sql.append(" AND audit_status = #{auditStatus}");
        }

        // 添加排序和分页
        System.out.println(sql.toString());
        return sql.toString();
    }
}
