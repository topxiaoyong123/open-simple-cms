package com.opencms.wcm.server;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 11:31:56
 * To change this template use File | Settings | File Templates.
 */
public class ContextThreadLocal implements Serializable {

    private final static ThreadLocal<HttpServletRequest> servletRequest = new ThreadLocal<HttpServletRequest>();

    private final static ThreadLocal<HttpServletResponse> servletResponse = new ThreadLocal<HttpServletResponse>();

    public static HttpServletRequest getRequest(){
        return servletRequest.get();
    }

    public static void setRequest(HttpServletRequest request){
        servletRequest.set(request);
    }

    public static HttpServletResponse getResponse(){
        return servletResponse.get();
    }

    public static void setResponse(HttpServletResponse response){
        servletResponse.set(response);
    }

}