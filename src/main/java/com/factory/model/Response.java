package com.factory.model;

public class Response <T> {
    public Integer statusCode;
    public T body;

    public Response(Integer statusCode) {
        this.statusCode = statusCode;
        this.body = null;
    }

    public Response(Integer statusCode, T body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public T getBody() {
        return (T) this.body;
    }
}