<#if pageBean??>
    ${pageBean.currentPage}
    <#if pageBean.records??>
        <#list pageBean.records as item>
            ${item.title} <br/>
            ${item.content}<br/>
        </#list>
    </#if>
</#if>