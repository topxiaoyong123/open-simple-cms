package com.opencms.app.interceptor;

import com.opencms.util.ContextThreadLocal;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-16
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
public class ThreadLocalInterceptor extends AbstractInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ThreadLocalInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        logger.debug("ThreadLocalInterceptor invocation begin");
        ContextThreadLocal.setRequest(ServletActionContext.getRequest());
        ContextThreadLocal.setResponse(ServletActionContext.getResponse());
        try {
            return invocation.invoke();
        }finally {
            logger.debug("ThreadLocalInterceptor invocation end");
            ContextThreadLocal.setRequest(null);
            ContextThreadLocal.setResponse(null);
        }
    }
}
