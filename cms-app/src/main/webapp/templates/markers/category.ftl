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
        <#assign pageContents = engineUtil.getContents(categoryId, page.firstResult!, page.pagesize!)>
        <#return pageContents>
    </#if>
</#function>