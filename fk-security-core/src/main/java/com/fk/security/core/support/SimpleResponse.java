package com.fk.security.core.support;

/**
 * @author fankun
 * @date 2018/3/9 18:21
 */
public class SimpleResponse {

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public SimpleResponse() {
    }
}
