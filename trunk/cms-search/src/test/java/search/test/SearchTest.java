package search.test;

import com.opencms.search.SearchService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-29
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class SearchTest {

    private static SearchService searchService;

    @BeforeClass
    public static void init(){
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"/META-INF/spring/bundle-context-core-db.xml",  "/META-INF/spring/bundle-context-search.xml",  "/META-INF/spring/bundle-context-engine.xml",  "/META-INF/spring/bundle-context-template.xml",  "/META-INF/spring/bundle-context-util.xml"});
        searchService = (SearchService)context.getBean("searchServiceImpl");
    }

    @Test
    public void testIndex(){
        searchService.index();
    }

    @Test
    public void testSearch(){
        searchService.search("这个", "content", 1, 100);
    }

}
