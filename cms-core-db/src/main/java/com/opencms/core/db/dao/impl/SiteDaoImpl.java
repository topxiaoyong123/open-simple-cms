package com.opencms.core.db.dao.impl;

import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.dao.SiteDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-8
 * Time: 13:06:37
 * To change this template use File | Settings | File Templates.
 */
@Repository("siteDao")
@Transactional(readOnly = true)
public class SiteDaoImpl extends BaseDaoImpl<SiteBean> implements SiteDao {
}
