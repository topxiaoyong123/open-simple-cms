package test.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-8-24
 * Time: 下午1:41
 * To change this template use File | Settings | File Templates.
 */
public class TestSpringNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("test", new TestDefinitionParser());
    }

    protected class TestDefinitionParser implements BeanDefinitionParser {

        public BeanDefinition parse(Element element, ParserContext parserContext) {
            String className = element.getAttribute("class");
            try {
                Class c = Class.forName(className);
                BeanDefinition definition = new RootBeanDefinition(c);
                parserContext.getRegistry().registerBeanDefinition("test.spring.TestBean", definition);
                registerTestBeanPostProcessorIfNeccessory(parserContext.getRegistry());
                return definition;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                 return null;
            }

        }

        private void registerTestBeanPostProcessorIfNeccessory(BeanDefinitionRegistry registry) {
            RootBeanDefinition definition = new RootBeanDefinition(TestBeanPostProcessor.class);
            definition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(TestBeanPostProcessor.class.getName(), definition);
        }
    }
}
