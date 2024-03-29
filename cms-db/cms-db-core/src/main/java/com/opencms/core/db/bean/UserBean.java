package com.opencms.core.db.bean;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 14:27:54
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "cms_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserBean extends BaseEntity {

    @Column(unique = true, nullable = false, length = 32)
    private String username;

    @Column(nullable = false, length = 32)
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "cms_usergroup")
    private Set<RoleBean> roles = new LinkedHashSet<RoleBean>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleBean> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBean> roles) {
        this.roles = roles;
    }

    public void addRole(RoleBean role){
        if(!roles.contains(role)){
            roles.add(role);
        }
    }

    public void removeRole(RoleBean role){
        roles.remove(role);
    }

    @Override
    public String toString() {
        return "id:" + getId() + ", username:" + username;
    }

}
