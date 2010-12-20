package com.opencms.core.db.service.impl;

import com.opencms.core.db.service.SiteService;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.dao.SiteDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@Transactional(rollbackFor = Exception.class)
public class SiteServiceImpl implements SiteService {

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    public boolean addSite(SiteBean site){
        site.setCreationDate(new Date());
        return siteDao.persist(site);
    }

    public boolean updateSite(SiteBean site) {
        return siteDao.merge(site);
    }

    public boolean addOrUpdateSite(SiteBean site) {
        if(site.getId() == null){
            return addSite(site);
        } else{
            return updateSite(site);
        }
    }

    @Transactional(readOnly = true)
    public List<SiteBean> getAllSites() {
        return siteDao.getAll(SiteBean.class);
    }

    @Transactional(readOnly = true)
    public SiteBean getSiteById(String id) {
        return siteDao.get(SiteBean.class, id);
    }

    @Transactional(readOnly = true)
    public SiteBean getSiteByName(String name) {
        return siteDao.get(SiteBean.class, "name", name);
    }
}
