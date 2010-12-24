<#include "common/category.ftl">
<@category.title/>

<#assign categoryMenu = category.getCategoryMenu()/>
<#if categoryMenu??>
    <#list categoryMenu.children as menu>
    <#if menu.isCurrent()>
        ${menu.item.title}
    <#else>
        <a href="${menu.item.url}" target="_blank">${menu.item.title}</a>
    </#if>
    </#list>
</#if>

<#assign pageContents = category.getContents()!/>
<#list pageContents as content>
    ${content.title}
</#list>