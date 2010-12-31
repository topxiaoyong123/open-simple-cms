package com.opencms.search;

import com.opencms.core.db.bean.ContentBean;
import org.compass.gps.CompassGps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-30
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class CompassIndexBuilder {

    private static Logger logger = LoggerFactory.getLogger(CompassIndexBuilder.class);

    @Resource
	private CompassGps compassGps;

    public void index() {
        try{
            logger.debug("-------indexing start...");
		    compassGps.index(ContentBean.class);
            logger.debug("-------indexing end...");
        } catch (Exception e){
            e.printStackTrace();
        }
	}
}
