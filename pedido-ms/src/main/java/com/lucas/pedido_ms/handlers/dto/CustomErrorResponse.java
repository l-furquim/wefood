package com.lucas.pedido_ms.handlers.dto;

public class CustomErrorResponse {
    private int status;
    private String error;
    private String url;

    public CustomErrorResponse() {
    }

    public CustomErrorResponse(int status, String error, String url) {
        this.status = status;
        this.error = error;
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String code) {
        this.url = code;
    }
}
