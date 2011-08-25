package test.action;

import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import test.service.ITestService;
import test.spring.TestBean;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-8-18
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@ParentPackage("test")
@Namespace("/test")
@Results({@Result(name = "success", type = "testResult")})
public class TestAction extends BaseAction implements ModelDriven {

    @Resource
    private ITestService testService;

    @Resource
    private TestBean testBean;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String index() {
        name = "lijing1";
        System.out.println(testService.hello(name));
        return SUCCESS;
    }

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String show() {
        name = "lijing2";
        System.out.println(id);
        System.out.println(testService.hello1(name));
        return SUCCESS;
    }

    public String edit() {
        name = "lijing3";
        System.out.println(id);
        System.out.println(testService.hello1(name));
        return SUCCESS;
    }

    public Object getModel() {
        return new ArrayList();
    }
}
