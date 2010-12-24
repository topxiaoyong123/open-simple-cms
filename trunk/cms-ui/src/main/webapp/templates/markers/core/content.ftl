<#macro title>
    ${contentModel.title?if_exists}
</#macro>
<#function getTitle>
    <#return contentModel.title?if_exists>
</#function>

<#macro name>
    ${contentModel.name?if_exists}
</#macro>
<#function getName>
    <#return contentModel.name?if_exists>
</#function>

<#macro id>
    ${contentModel.id?if_exists}
</#macro>
<#function getId>
    <#return contentModel.id?if_exists>
</#function>
