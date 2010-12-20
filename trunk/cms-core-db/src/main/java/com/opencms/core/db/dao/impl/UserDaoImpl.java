package com.opencms.core.db.dao.impl;

import com.opencms.core.db.dao.UserDao;
import com.opencms.core.db.bean.UserBean;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 14:45:46
 * To change this template use File | Settings | File Templates.
 */
@Repository("userDao")
@Transactional(rollbackFor = Exception.class)
public class UserDaoImpl extends BaseDaoImpl<UserBean> implements UserDao {
}
