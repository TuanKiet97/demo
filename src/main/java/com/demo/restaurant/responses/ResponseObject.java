package com.demo.restaurant.responses;

import lombok.Data;

@Data
public class ResponseObject {
    private int status;
    private String message;
    private Object data;
    public ResponseObject(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
