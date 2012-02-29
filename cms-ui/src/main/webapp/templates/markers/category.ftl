<#include "core/category.ftl">

<#function getCategoryMenu categoryId = getId()>
    <#if categoryMenu??>
        <#return categoryMenu>
    <#else>
        <#assign categoryMenu = engineUtil.getCategoryMenu(categoryId)>
        <#return categoryMenu>
    </#if>
</#function>

<#function getContents categoryId = getId() page = getPage()>
    <#if pageContents??>
        <#return pageContents>
    <#else>
        <#assign pageContents = engineUtil.getContents(categoryId, page.firstResult, page.pageSize)>
        <#return pageContents>
    </#if>
</#function>

<#function getCategoryURL category = getCategory() page = 1 pageSize = 10>
    <#return engineUtil.getMapper().getCategoryURL(category, page, pageSize)>
</#function>