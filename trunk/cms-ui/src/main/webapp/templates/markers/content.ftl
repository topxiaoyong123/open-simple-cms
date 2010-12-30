<#include "core/content.ftl">

<#function getContentById id = getId()>
    <#return engineUtil.getCmsManager().getContentService().getContentById(id)>
</#function>
