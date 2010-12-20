package com.opencms.core.db.dao.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.dao.CategoryDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-10
 * Time: 10:10:31
 * To change this template use File | Settings | File Templates.
 */
@Repository("categoryDao")
@Transactional(rollbackFor = Exception.class)
public class CategoryDaoImpl extends BaseDaoImpl<CategoryBean> implements CategoryDao {
}
