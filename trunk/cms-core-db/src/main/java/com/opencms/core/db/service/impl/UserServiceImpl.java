package com.opencms.core.db.service.impl;

import com.opencms.core.db.service.UserService;
import com.opencms.core.db.dao.UserDao;
import com.opencms.core.db.bean.UserBean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 20:02:01
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public boolean addUser(UserBean user){
        if(getUserByUsername(user.getUsername()) != null){
            return false;
        }
        userDao.persist(user);
        return true;   
    }

    public UserBean getUserById(Long id) {
        return userDao.get(UserBean.class, id);
    }

    public UserBean getUserByUsername(String username){
        return userDao.get(UserBean.class, "username", username);
    }

}
