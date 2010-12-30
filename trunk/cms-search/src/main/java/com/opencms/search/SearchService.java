package com.opencms.search;

import com.opencms.util.common.page.PageBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-29
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public interface SearchService {

    public PageBean search(String key, String type, int page, int pageSize);

    public void index();

}
