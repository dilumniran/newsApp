package com.example.a247news.object;

import com.example.a247news.service.RequestType;

public class ApiMessage {

    private RequestType type;
    private Object object;

    public ApiMessage(RequestType type, Object object) {
        this.type = type;
        this.object = object;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
