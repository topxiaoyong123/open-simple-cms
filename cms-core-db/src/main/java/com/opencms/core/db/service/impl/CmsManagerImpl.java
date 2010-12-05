package com.opencms.core.db.service.impl;

import com.opencms.core.db.service.CmsManager;
import com.opencms.core.db.service.UserService;
import com.opencms.core.db.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-5
 * Time: 8:25:22
 * To change this template use File | Settings | File Templates.
 */
@Service("cmsManager")
public class CmsManagerImpl implements CmsManager {

    @Autowired
    private UserService userService;

    @Autowired
    private ContentService contentService;

    public UserService getUserService() {
        return userService;
    }

    public ContentService getContentService() {
        return contentService;
    }
}
