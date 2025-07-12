package cn.neu.edu.cemenghuisystem.pojo;

import lombok.Data;

import java.util.List;

@Data
public class LoginUserInfo {
    private Integer id; // 用户ID
    private String account; // 账号
    private String userType; // （如'admin'/'user'）
    private String role;
    private List<String> permissions; // 权限标识数组
    private String nickname; // 昵称
    private String avatar; // 头像
    private Integer status; // 用户状态，0=启用，1=禁用，系统默认0
    private String companyName; // 企业名称，必填
    private String companyContact; // 企业联系方式（手机号），必填
    private String createdAt;
    private String lastLoginAt;
    private String phoneNumber;
    private String email;
}
