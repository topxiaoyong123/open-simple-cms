package com.opencms.core.db.dao.impl;

import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 14:45:46
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDaoImpl extends BaseDaoImpl<UserBean> implements UserDao {
}
