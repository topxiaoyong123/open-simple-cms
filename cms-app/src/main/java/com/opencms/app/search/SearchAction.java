package com.opencms.app.search;

import com.opencms.search.SearchService;
import com.opencms.util.common.Constants;
import com.opencms.util.common.page.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-31
 * Time: 上午9:04
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("prototype")
public class SearchAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(SearchAction.class);

    private String siteId;

    private String key;

    private String type;

    private int currentPage = 1;

    private int pageSize = PageBean.DEFAULT_SIZE;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Resource
    private SearchService searchService;

    private PageBean pageBean;

    private String template = Constants.DEFAULT_BASE_TEMPLATE_PATH;

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String search(){
        logger.debug("search key:{}", key);
        logger.debug("search type:{}", type);
        pageBean = searchService.search(key, type, currentPage, pageSize);
        return SUCCESS;
    }

    public void validateSearch(){
        if(key == null || "".equals(key.trim())){
            logger.warn("search key required");
            addFieldError("key", "searchAction.fieldError.required");
        }
    }

}
