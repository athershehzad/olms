package com.olms.config;

import java.io.InputStream;

public class ResourceResponse {

    static final String FOUROHFOUR = "Not found.";
    static final String FIVEHUNDRED = "Internal server error.";
    private final int httpStatus;
    private final InputStream response;

    ResourceResponse(int httpStatus, InputStream response) {
        this.httpStatus = httpStatus;
        this.response = response;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public InputStream getResponse() {
        return response;
    }

}
