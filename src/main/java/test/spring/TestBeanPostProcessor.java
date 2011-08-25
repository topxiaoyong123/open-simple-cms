package test.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-8-25
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public class TestBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        if(beanName.equals("testService")) {
            Object proxy = null;
            proxy = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler(){

                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("in testpostprocessor " + method.getName());
                    return method.invoke(bean, args);
                }
            });
            return proxy;
        }
        return bean;
    }
}
