package com.opencms.core.db.service.impl;

import com.opencms.core.db.service.UserService;
import com.opencms.core.db.dao.UserDao;
import com.opencms.core.db.dao.RoleDao;
import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.bean.RoleBean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

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

    @Autowired
    private RoleDao roleDao;

    public boolean addUser(UserBean user){
        if(getUserByUsername(user.getUsername()) != null){
            return false;
        }
        userDao.persist(user);
        return true;   
    }

    public UserBean getUserById(String id) {
        return userDao.get(UserBean.class, id);
    }

    public UserBean getUserByUsername(String username){
        return userDao.get(UserBean.class, "username", username);
    }

    public boolean updateUser(UserBean user) {
        userDao.merge(user);
        return true;
    }

    public boolean deleteUser(UserBean user) {
        userDao.delete(user);
        return true;
    }

    public boolean addRoleForUser(String id, RoleBean role) {
        UserBean user = getUserById(id);
        if(user != null){
            user.addRole(role);
            updateUser(user);
            return true;
        }
        return false;
    }

    public boolean addRolesForUser(String id, Set<RoleBean> roles) {
        UserBean user = getUserById(id);
        if(user != null){
            user.setRoles(roles);
            updateUser(user);
            return true;
        }
        return false;
    }

    public boolean addRole(RoleBean role){
        roleDao.persist(role);
        return true;
    }

    public RoleBean getRoleById(String id){
        return roleDao.get(RoleBean.class, id);    
    }

    public boolean updateRole(RoleBean role) {
        roleDao.merge(role);
        return true;
    }

    public boolean deleteRole(RoleBean role) {
        RoleBean r = getRoleById(role.getId());
        Iterator<UserBean> it = r.getUsers().iterator();
        while(it.hasNext()){
            UserBean u = it.next();
            u.removeRole(r);
            r.getUsers().remove(u);
        }
        updateRole(r);
        roleDao.delete(r);
        return true;
    }

}
