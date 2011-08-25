package test.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.util.ServletContextAware;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletConfigAware;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-8-22
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaseAction extends ActionSupport implements ServletContextAware {

    protected ServletContext servletContext;

    public void outMessage(String msg) {
        ServerContext wctx = ServerContextFactory.get(this.servletContext);
        ScriptSession scriptSessions = null;
        Collection<ScriptSession> sessions = wctx.getAllScriptSessions();

        ScriptBuffer script = new ScriptBuffer();
        script.appendScript("showMessage(")
                .appendData(msg)
                .appendScript(");");

        for (ScriptSession session : sessions) {
            session.addScript(script);
        }
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

