package cn.neu.edu.cemenghuisystem.pojo;

import lombok.Data;

@Data
public class LoginResponse {
    private int code;
    private String msg;
    private String token;
    private LoginUserInfo userInfo;

    public LoginResponse(int code, String msg, String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    public LoginResponse(int code, String msg, String token, LoginUserInfo userInfo) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.userInfo = userInfo;
    }
}
