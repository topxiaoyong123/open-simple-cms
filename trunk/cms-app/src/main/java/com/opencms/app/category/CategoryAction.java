package com.opencms.app.category;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.field.ContentField;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opencms.engine.model.CategoryModel;
import com.opencms.util.common.page.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 13:28:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("prototype")
public class CategoryAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(CategoryAction.class);

    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    private String names;
    
    public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	private int page = 1;

    private int pageSize = PageBean.DEFAULT_SIZE;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Resource
    private Engine<CategoryModel> categoryEngineImpl;

    @Resource
    private CmsManager cmsManager;

    @Transactional(readOnly = true)
    public String view(){
        logger.debug("category-nams:[{}] page:[{}] pagesize:[{}]", new Object[]{names, page, pageSize});
        try{
        	String[] namearray = names.split("/");
        	if(namearray.length < 2) {
        		return "404";
        	}
            CategoryBean categoryBean = cmsManager.getCategoryService().getCategoryByNames(namearray);
            if(categoryBean != null){
            	long totalCount = cmsManager.getContentService().getCountByCategoryId(categoryBean.getId(), ContentField._STATE_PUBLISHED);
            	CategoryModel category = new CategoryModel(categoryBean, page, pageSize, totalCount);
                html = categoryEngineImpl.engine(category);
            } else {
                return "404";
            }
        } catch (Exception e){
            logger.error("engine category error : ", e);
            return ERROR;
        }
        return SUCCESS;
    }

}
