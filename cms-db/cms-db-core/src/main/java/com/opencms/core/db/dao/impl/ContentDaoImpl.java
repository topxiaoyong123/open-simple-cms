package com.opencms.core.db.dao.impl;

import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.dao.ContentDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 20:44:03
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional(readOnly = true)
public class ContentDaoImpl extends BaseDaoImpl<ContentBean> implements ContentDao {
}
