package test.service;

import org.springframework.stereotype.Service;
import test.annotations.TestAnnotation;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-8-18
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 */
@Service
@TestAnnotation
public class TestService implements ITestService {

    public String hello(String name) {
        System.out.println("hello:" + name);
        return "hello:" + name;
    }

    @TestAnnotation(false)
    public String hello1(String name) {
        System.out.println("hello:" + name);
        return "hello:" + name;
    }

}
