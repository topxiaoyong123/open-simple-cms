package test.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import test.annotations.TestAnnotation;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-8-18
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class TestAspect {

    @Before("execution(* test.service.*.*(..))")
    public void test(JoinPoint jpt) {
        try {
            MethodSignature signature = (MethodSignature)jpt.getSignature();
            Method method = jpt.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
            TestAnnotation ta = method.getAnnotation(TestAnnotation.class);
            if(ta != null) {
                System.out.println(ta.value());
                System.out.println("TestAnnotation in method");
            } else {
                ta = jpt.getTarget().getClass().getAnnotation(TestAnnotation.class);
                if(ta != null) {
                    System.out.println(ta.value());
                    System.out.println("TestAnnotation in class");
                }
            }
            System.out.println(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
