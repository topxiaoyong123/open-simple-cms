package com.opencms.core.db.service.impl;

import com.opencms.core.db.service.ContentService;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.dao.ContentDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 20:58:51
 * To change this template use File | Settings | File Templates.
 */
@Service("contentService")
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    public boolean addContent(ContentBean content) {
        return contentDao.persist(content);
    }
}
