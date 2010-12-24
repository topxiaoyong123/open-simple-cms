<#include "common/site.ftl">
<@site.title/> <@site.name/>

<#assign siteMenu = site.getSiteMenu()/>

<#if siteMenu??>
    <#list siteMenu.children as menu>
        <a href="${menu.item.url}" target="_blank">${menu.item.title}</a>
    </#list>
</#if>

<#assign siteMenu = site.getSiteMenu()/>

<#if siteMenu??>
    <#list siteMenu.children as menu>
        <a href="${menu.item.url}" target="_blank">${menu.item.title}</a>
    </#list>
</#if>