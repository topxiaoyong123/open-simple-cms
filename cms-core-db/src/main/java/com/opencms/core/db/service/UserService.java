package com.opencms.core.db.service;

import com.opencms.core.db.bean.UserBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 16:36:16
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    public boolean addUser(UserBean user);

    public UserBean getUserById(Long id);

    public UserBean getUserByUsername(String username);

}
