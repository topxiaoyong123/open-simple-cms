<#macro title>
    ${categoryModel.title?if_exists}
</#macro>
<#function getTitle>
    <#return categoryModel.title?if_exists>
</#function>

<#macro name>
    ${categoryModel.name?if_exists}
</#macro>
<#function getName>
    <#return categoryModel.name?if_exists>
</#function>

<#macro id>
    ${categoryModel.id?if_exists}
</#macro>
<#function getId>
    <#return categoryModel.id?if_exists>
</#function>

<#function getPage>
    <#return categoryModel.page?if_exists>
</#function>