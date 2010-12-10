package com.opencms.core.db.service;

import com.opencms.core.db.bean.SiteBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-8
 * Time: 13:07:33
 * To change this template use File | Settings | File Templates.
 */
public interface SiteService {

    public boolean addSite(SiteBean site);

    public boolean updateSite(SiteBean site);

    public boolean addOrUpdateSite(SiteBean site);

    public List<SiteBean> getAllSites();

    public SiteBean getSiteById(String id);
}
