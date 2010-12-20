<#macro title>
    ${siteModel.title?if_exists}
</#macro>
<#function getTitle>
    <#return siteModel.title?if_exists>
</#function>

<#macro name>
    ${siteModel.name?if_exists}
</#macro>
<#function getName>
    <#return siteModel.name?if_exists>
</#function>

<#macro id>
    ${siteModel.id?if_exists}
</#macro>
<#function getId>
    <#return siteModel.id?if_exists>
</#function>