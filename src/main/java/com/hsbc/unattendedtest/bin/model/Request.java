package com.hsbc.unattendedtest.bin.model;


import org.springframework.data.annotation.Id;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Skeleton class that exposes some basic information that we want to store about a Request.
 *
 * Please add whatever you feel is missing.
 */
public class Request {

    @Id
    private long id;
    private String methodName;
    private String requestURI;
    private String content;

    public Request() {
    }

    public static Request buildFromHttpRequest(HttpServletRequest httpServletRequest) throws IOException {
        Request request = new Request();
        request.methodName = httpServletRequest.getMethod();
        request.requestURI = httpServletRequest.getRequestURI();
        try(BufferedReader bufferedReader = httpServletRequest.getReader()) {
            if(bufferedReader != null) {
                String line = null;
                request.content = "";
                while ((line = bufferedReader.readLine()) != null) {
                    request.content += line + System.getProperty("line.separator");
                }
            }
        }
        return request;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getContent() {
        return content;
    }
}
