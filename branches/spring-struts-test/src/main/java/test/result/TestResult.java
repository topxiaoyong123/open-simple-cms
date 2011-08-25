package test.result;

import com.opensymphony.xwork2.ActionInvocation;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-8-18
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
public class TestResult extends StrutsResultSupport {

    @Override
    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {

        Object action = invocation.getAction();
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.write("hhhhhhhhhhhhhhhhhhhh");
        writer.flush();
    }
}
