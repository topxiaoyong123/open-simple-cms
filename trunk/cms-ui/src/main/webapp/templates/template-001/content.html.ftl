<#include "common/content.ftl">
<@content.title/>

<#assign c = content.getContentById()>
<#if c??>
    ${c.title}
    ${c.contentDetail.content}
</#if>