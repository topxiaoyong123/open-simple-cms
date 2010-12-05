package com.opencms.core.db.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.bean.RoleBean;
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
        user.setUsername("lijing");
        user.setPassword("111111");
//        Assert.assertEquals(true, userService.addUser(user));
        System.out.println(userService.addUser(user));
        System.out.println(user);
    }

    @Test
    public void testFindUser(){
//        Assert.assertEquals("lijing1", userService.getUserById(1L).getUsername());
        System.out.println(userService.getUserById(1L).getUsername());
    }

    @Test
    public void testFindUserByUsername(){
//        Assert.assertEquals("lijing", userService.getUserByUsername("lijing").getUsername());
        System.out.println(userService.getUserByUsername("lijing"));
    }

    @Test
    public void testAddRole(){
        UserBean u = userService.getUserByUsername("lijing");
        if(u != null){
            RoleBean r = new RoleBean();                                //userService.getRoleById(1L);
            r.setRolename("role");
            r.setDescription("role");
            userService.addRole(r);
            userService.addRoleForUser(u.getId(), r);
//            u.setPassword("123456");
//            userService.updateUser(u);
        }
    }

    @Test
    public void testDeleteRole(){
        RoleBean role = userService.getRoleById(2L);
        userService.deleteRole(role);
    }

    @Test
    public void testDeleteUser(){
        UserBean u = userService.getUserById(2L);
        userService.deleteUser(u);
    }
}
