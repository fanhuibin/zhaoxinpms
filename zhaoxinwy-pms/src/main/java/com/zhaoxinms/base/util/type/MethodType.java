package com.zhaoxinms.base.util.type;

public enum MethodType {
    /**
     * GET请求
     */
    GET("GET"),
    /**
     * POST 请求
     */
    POST("POST"),
    /**
     * PUT 请求
     */
    PUT("PUT"),;
    private String method;

    MethodType(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
