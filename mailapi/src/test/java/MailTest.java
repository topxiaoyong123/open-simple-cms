import com.hundsun.fund.website.mail.mailapi.MailBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ljing
 * Date: 2010-8-2
 * Time: 8:42:46
 * To change this template use File | Settings | File Templates.
 */
public class MailTest {

    public static MailBuilder mailBuilder;

    static ApplicationContext ctx = new ClassPathXmlApplicationContext("mailContext.xml");

    @Before
    public void setUp() {
        mailBuilder = (MailBuilder) ctx.getBean("mailBuilder");
    }

    @Test
    public void testMail(){
        mailBuilder.addFile("C:\\Documents and Settings\\ljing\\桌面\\04219~1.JPG");
        mailBuilder.buildMail(new String[]{"lij0511@163.com"}, "奶奶的", "我靠啊啊啊啊啊");
    }
}
