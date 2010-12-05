package com.opencms.core.db.service;

import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.bean.RoleBean;

import java.util.List;
import java.util.Set;

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

    public boolean updateUser(UserBean user);

    public boolean deleteUser(UserBean user);

    public boolean addRoleForUser(Long id, RoleBean role);

    public boolean addRolesForUser(Long id, Set<RoleBean> roles);

    public boolean addRole(RoleBean role);

    public RoleBean getRoleById(Long id);

    public boolean updateRole(RoleBean role);

    public boolean deleteRole(RoleBean role);
}
