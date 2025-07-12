package cn.neu.edu.cemenghuisystem.pojo;

import lombok.Data;

@Data
public class UserProfileResponse {
    private int code;
    private Object userInfo;

    public UserProfileResponse(int code, Object userInfo) {
        this.code = code;
        this.userInfo = userInfo;
    }
}
