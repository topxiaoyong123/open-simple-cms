package com.opencms.core.db.service.impl;

import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.dao.SiteDao;
import com.opencms.core.db.service.SiteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-8
 * Time: 13:07:47
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional(readOnly = true)
public class SiteServiceImpl implements SiteService {

    @Resource
    private SiteDao siteDao;

    @Transactional(rollbackFor = Exception.class)
    public boolean addSite(SiteBean site){
        site.setCreationDate(new Date());
        return siteDao.persist(site);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateSite(SiteBean site) {
        return siteDao.merge(site);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addOrUpdateSite(SiteBean site) {
        if(site.getId() == null){
            return addSite(site);
        } else{
            return updateSite(site);
        }
    }

    public List<SiteBean> getAllSites() {
        return siteDao.getAll(SiteBean.class);
    }

    public SiteBean getSiteById(Long id) {
        return siteDao.get(SiteBean.class, id);
    }

    public SiteBean getSiteByName(String name) {
        return siteDao.get(SiteBean.class, "name", name);
    }
}
