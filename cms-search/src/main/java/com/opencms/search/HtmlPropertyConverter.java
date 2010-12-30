package com.opencms.search;

import org.compass.core.Property;
import org.compass.core.converter.ConversionException;
import org.compass.core.converter.basic.AbstractBasicConverter;
import org.compass.core.mapping.ResourcePropertyMapping;
import org.compass.core.marshall.MarshallingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-30
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public class HtmlPropertyConverter extends AbstractBasicConverter {

    private static Logger logger = LoggerFactory.getLogger(HtmlPropertyConverter.class);

    /**
     *  搜索的时候被调用
     */
    @Override
    protected Object doFromString(String str, ResourcePropertyMapping resourcePropertyMapping, MarshallingContext context) throws ConversionException {
        logger.debug("----------calling doFromString...");
        return str;
    }

    /**
     * 创建索引的时候被调用，此时将文本中的HTML标签过滤
     */
    @Override
    protected Property createProperty(String value,
            ResourcePropertyMapping resourcePropertyMapping,
            MarshallingContext context) {
        logger.debug("----------calling createProperty...");
        if(value != null){
            value = value.replaceAll("<[^>]*>", "");
        }
        return super.createProperty(value, resourcePropertyMapping, context);
    }
}
