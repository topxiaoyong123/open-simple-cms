<#import "../../markers/category.ftl" as category>

<#macro paging>
    <#assign page = category.getPage()>
    <#if page.currentPage lte 1>首页 上一页
    <#else><a href="${category.getCategoryURL(null,1, page.pageSize)}">首页</a> <a href="${category.getCategoryURL(null,page.currentPage - 1, page.pageSize)}">上一页</a>
    </#if>
    <#if page.currentPage gte page.totalPage>下一页 尾页
    <#else><a href="${category.getCategoryURL(null,page.currentPage + 1, page.pageSize)}">下一页</a> <a href="${category.getCategoryURL(null,page.totalPage, page.pageSize)}">尾页</a>
    </#if>
</#macro>