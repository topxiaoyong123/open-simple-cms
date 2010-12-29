package com.opencms.wcm.server;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.*;
import com.opencms.util.ContextThreadLocal;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 11:28:04
 * To change this template use File | Settings | File Templates.
 */
public class MyGWTServer extends RemoteServiceServlet {

    private WebApplicationContext springContext;

    @Override
    public void init(ServletConfig Config) throws ServletException {
        super.init(Config);
        springContext = (WebApplicationContext) Config.getServletContext().getAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        if (springContext == null) {
            throw new RuntimeException("Check Your Web.Xml Setting, No Spring Context Configured");
        }

    }

    @Override
    protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL,
                                                           String strongName) {
        return super.doGetSerializationPolicy((HttpServletRequest) ContextThreadLocal.getRequest(), moduleBaseURL, strongName);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ContextThreadLocal.setRequest(req);
            ContextThreadLocal.setResponse(resp);

            String requestURI = req.getRequestURI();
            String beanname = requestURI.substring(requestURI.lastIndexOf("/") + 1);
            RemoteService service = (RemoteService) springContext.getBean(beanname);

            String requestPayload = readPayloadAsUtf8(req);

            // Let subclasses see the serialized request.
            //
            onBeforeRequestDeserialized(requestPayload);

            // Invoke the core dispatching logic, which returns the serialized
            // result.
            //
            String responsePayload = processCall(service, requestPayload);

            // Let subclasses see the serialized response.
            //
            onAfterResponseSerialized(responsePayload);

            // Write the response.
            //
            writeResponse(req, resp, responsePayload);
        } catch (Throwable e) {
            e.printStackTrace();
            // Give a subclass a chance to either handle the exception or
            // rethrow it
            //
            doUnexpectedFailure(e);
        } finally {
            ContextThreadLocal.setRequest(null);
            ContextThreadLocal.setResponse(null);
        }
    }

    /**
     * rewrite processCall
     *
     * @param bean
     * @param payload
     * @return
     * @throws com.google.gwt.user.client.rpc.SerializationException
     */
    public String processCall(RemoteService bean, String payload) throws SerializationException {
        try {
            RPCRequest rpcRequest = RPC.decodeRequest(payload, bean.getClass(), this);
            return RPC.invokeAndEncodeResponse(bean, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest
                    .getSerializationPolicy());
        } catch (IncompatibleRemoteServiceException ex) {
            getServletContext().log("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
            return RPC.encodeResponseForFailure(null, ex);
        }
    }

    private String readPayloadAsUtf8(HttpServletRequest request) throws IOException, ServletException {
        return RPCServletUtils.readContentAsUtf8(request, true);
    }

    private void writeResponse(HttpServletRequest request, HttpServletResponse response, String responsePayload)
            throws IOException {
        boolean gzipEncode = RPCServletUtils.acceptsGzipEncoding(request)
                && shouldCompressResponse(request, response, responsePayload);
        RPCServletUtils.writeResponse(getServletContext(), response, responsePayload, gzipEncode);
    }
}
