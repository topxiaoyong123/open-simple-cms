package com.opencms.core.db.dao.impl;

import com.opencms.core.db.dao.ContentDao;
import com.opencms.core.db.bean.ContentBean;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 20:44:03
 * To change this template use File | Settings | File Templates.
 */
@Repository("contentDao")
public class ContentDaoImpl extends BaseDaoImpl<ContentBean> implements ContentDao {
}
