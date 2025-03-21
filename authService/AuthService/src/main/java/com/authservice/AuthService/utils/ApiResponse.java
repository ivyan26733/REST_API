//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.authservice.AuthService.utils;

public class ApiResponse<T> {
    private boolean isSuccess;
    private String message;
    private T data;

    private ApiResponse(){

    }

    public ApiResponse(String message, T data, boolean isSuccess) {
        this.message = message;
        this.data = data;
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }
}
