package com.opencms.core.db.service.impl;

import com.opencms.core.db.service.SiteService;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.dao.SiteDao;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-8
 * Time: 13:07:47
 * To change this template use File | Settings | File Templates.
 */
@Service("siteService")
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteDao siteDao;

    public boolean addSite(SiteBean site){
        site.setCreationDate(new Date());
        return siteDao.persist(site);
    }

    public boolean updateSite(SiteBean site) {
        return siteDao.merge(site);
    }

    public List<SiteBean> getAllSites() {
        return siteDao.getAll(SiteBean.class);
    }

    public SiteBean getSiteById(String id) {
        return siteDao.get(SiteBean.class, id);
    }
}