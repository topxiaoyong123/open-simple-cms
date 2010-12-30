package com.opencms.search.impl;

import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.field.ContentField;
import com.opencms.engine.model.Content;
import com.opencms.search.CompassIndexBuilder;
import com.opencms.search.SearchService;
import com.opencms.util.CmsUtils;
import com.opencms.util.common.page.PageBean;
import org.compass.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-29
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SearchServiceImpl implements SearchService {

    private static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Resource
    private CompassTemplate compassTemplate;

    @Resource
    private CmsUtils cmsUtils;

    @Resource
    private CompassIndexBuilder compassIndexBuilder;

    public PageBean search(String key, String type, int page, int pageSize) {
        logger.debug("--------searching--------");
        logger.debug("--------key:{}", key);
        logger.debug("--------type:{}", type);
        Compass compass = compassTemplate.getCompass();
        CompassSession compassSession = compass.openSession();
        CompassQueryBuilder compassQueryBuilder = compassSession.queryBuilder();
        if("content".equals(type)){
            List<Content> records = new ArrayList<Content>();
            CompassQuery query = compassQueryBuilder.bool().addMust(compassQueryBuilder.term("state", ContentField._STATE_PUBLISHED)).addMust(compassQueryBuilder.queryString(key).toQuery()).toQuery().setAliases(ContentBean.class.getSimpleName());
            query.addSort("top", CompassQuery.SortPropertyType.STRING);
            CompassHits hits = query.hits();
            int total = hits.length();

            PageBean pageBean = new PageBean(pageSize, page, total);
            logger.debug("--------page:currentPage[{}],pageSize[{}],totalCount[{}]", new Integer[]{pageBean.getCurrentPage(), pageBean.getPageSize(), pageBean.getTotalCount()});
            for(int i = pageBean.getFirstResult(); i < pageBean.getLastResult(); i ++){
                CompassHit hit = hits.hit(i);
                String c = hits.highlighter(i).fragmentsWithSeparator("content");//.replaceAll("<[^>]*>", "");
                String title = hits.highlighter(i).fragment("title");
                Content content = (Content)cmsUtils.getBeanMapperHelper().simpleMap((ContentBean)hit.getData(), Content.class);
                content.setContent(c);
                if(title != null){
                    content.setTitle(title);
                }
                records.add(content);
            }
            pageBean.setRecords(records);
            return pageBean;
        }
        return null;
    }

    public void index() {
        compassIndexBuilder.index();
    }
}
