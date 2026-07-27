package com.example.task_scheduler_server.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "success", "errorCode", "data", "message", "details" })
public class CommonResponse<T> {

    private boolean success = true;
    private T data;
    private int errorCode;
    private String message;
    private List<String> details;

    public CommonResponse(T data) {
        this.data = data;
    }

    public CommonResponse(int errorCode, String message, List<String> details) {
        this.success = false;
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }
}
