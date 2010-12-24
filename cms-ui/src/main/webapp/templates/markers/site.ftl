<#include "core/site.ftl">

<#function getSiteMenu siteId = getId()>
    <#if siteMenu??>
        <#return siteMenu>
    <#else>
        <#assign siteMenu = engineUtil.getSiteMenu(siteId)>
        <#return siteMenu>
    </#if>
</#function>
