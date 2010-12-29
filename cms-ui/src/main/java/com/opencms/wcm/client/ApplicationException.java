package com.opencms.wcm.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.SerializableException;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-5
 * Time: 8:35:38
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationException extends SerializableException implements IsSerializable {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String msg) {
        super(msg);
    }

    public Throwable getCause() {
        return super.getCause();
    }

    public String getMessage() {
        return super.getMessage();
    }

    public Throwable initCause(Throwable cause) {
        return super.initCause(cause);
    }
    
}
