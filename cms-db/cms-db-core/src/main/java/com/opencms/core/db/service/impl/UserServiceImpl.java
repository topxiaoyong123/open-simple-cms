package com.opencms.core.db.service.impl;

import com.opencms.core.db.bean.RoleBean;
import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.dao.RoleDao;
import com.opencms.core.db.dao.UserDao;
import com.opencms.core.db.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 20:02:01
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(UserBean user){
        if(getUserByUsername(user.getUsername()) != null){
            return false;
        }
        userDao.persist(user);
        return true;   
    }

    @Transactional(readOnly = true)
    public UserBean getUserById(Long id) {
        return userDao.get(UserBean.class, id);
    }

    @Transactional(readOnly = true)
    public UserBean getUserByUsername(String username){
        return userDao.get(UserBean.class, "username", username);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserBean user) {
        userDao.merge(user);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(UserBean user) {
        userDao.delete(user);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addRoleForUser(Long id, RoleBean role) {
        UserBean user = getUserById(id);
        if(user != null){
            user.addRole(role);
            updateUser(user);
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addRolesForUser(Long id, Set<RoleBean> roles) {
        UserBean user = getUserById(id);
        if(user != null){
            user.setRoles(roles);
            updateUser(user);
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(RoleBean role){
        roleDao.persist(role);
        return true;
    }

    @Transactional(readOnly = true)
    public RoleBean getRoleById(Long id){
        return roleDao.get(RoleBean.class, id);    
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleBean role) {
        roleDao.merge(role);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
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
