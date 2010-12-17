<#include "core/site.ftl">
<#macro siteMenus>
    <#assign menu = siteModel.menu?if_exists>
    <#list menu.children as childMenu>
        <a href="${childMenu.item.url}">${childMenu.item.title}</a> <br/>
        <#list childMenu.children as cMenu>
            <a href="${cMenu.item.url}">${cMenu.item.title}</a>
        </#list>
    </#list>
</#macro>