package com.opencms.core.db.dao.impl;

import com.opencms.core.db.bean.RoleBean;
import com.opencms.core.db.dao.RoleDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-5
 * Time: 18:51:05
 * To change this template use File | Settings | File Templates.
 */
@Repository("roleDao")
@Transactional(rollbackFor = Exception.class)
public class RoleDaoImpl extends BaseDaoImpl<RoleBean> implements RoleDao {
}
