package com.opencms.search.impl;

import com.opencms.core.db.bean.ContentBean;
import com.opencms.search.SearchService;
import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private CompassTemplate compassTemplate;

    public List search(String key, String type) {
        Compass compass = compassTemplate.getCompass();
        if("content".equals(type)){
            CompassQuery query = compass.openSession().queryBuilder().queryString(key).toQuery().setAliases(ContentBean.class.getSimpleName());
            CompassHits hits = query.hits();

        }
        return null;
    }
}
