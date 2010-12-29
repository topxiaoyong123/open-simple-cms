package search.test;

import com.opencms.core.db.bean.ContentBean;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.compass.core.*;
import org.compass.gps.CompassGps;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-29
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class SearchTest {

    private static CompassGps compassGps;

    private static CompassTemplate compassTemplate;

    @BeforeClass
    public static void init(){
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"/META-INF/spring/bundle-context-core-db.xml",  "/META-INF/spring/bundle-context-search.xml"});
        compassGps = (CompassGps)context.getBean("compassGps");
        compassTemplate = (CompassTemplate)context.getBean("compassTemplate");
    }

    @Test
    public void testIndex(){
        compassGps.index();
    }

    @Test
    public void testSearch(){
        String key = "我要搜百度啊";
        Analyzer analyzer = new StandardAnalyzer();
        Reader r = new StringReader(key);
        TokenStream ts = (TokenStream) analyzer.tokenStream("", r);
        Token t;
        try {
            while ((t = ts.next(new Token())) != null) {
                System.out.println(t.term());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(key);
        Compass compass = compassTemplate.getCompass();
        CompassSession compassSession = compass.openSession();
        CompassHits hits = compassSession.queryBuilder().queryString(key).toQuery().setAliases(
                ContentBean.class.getSimpleName()).hits();
        for(int i = 0; i < hits.length(); i ++){
            Object hit = hits.data(i);
            ContentBean contentBean = (ContentBean)hit;
            System.out.println(contentBean.getContentDetail().getContent());
        }

    }

}
