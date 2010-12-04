package com.opencms.core.db.test;

import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.opencms.core.db.dao.UserDao;
import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.service.UserService;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 15:36:41
 * To change this template use File | Settings | File Templates.
 */
public class UserTest {

    private static UserService userService;

    @BeforeClass
    public static void init(){
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"/META-INF/spring/bundle-context-core-db.xml"});
        userService = (UserService)context.getBean("userService");
    }

    @Test
    public void testAddUser(){
        UserBean user = new UserBean();
//        user.setId(10L);
        user.setUsername("李静");
        user.setPassword("111111");
        Assert.assertEquals(true, userService.addUser(user));
        System.out.println("id:" + user.getId());
    }

    @Test
    public void testFindUser(){
        Assert.assertEquals("lijing", userService.getUserById(1L).getUsername());
    }

    @Test
    public void testFindUserByUsername(){
        Assert.assertEquals("lijing", userService.getUserByUsername("李静").getUsername());
    }
}
