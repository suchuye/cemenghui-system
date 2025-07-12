package cn.neu.edu.cemenghuisystem.pojo;

import lombok.Data;

@Data
public class BasicResponse {
    private int code;
    private String message;

    public BasicResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
