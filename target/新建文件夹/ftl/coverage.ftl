<?xml version="1.0" encoding="UTF-8"?>
<testsuites>
<#list casenames as name> 
    <#assign totalLine = totalLines[name_index]>
    <#assign covLine = covLines[name_index]>
    <#assign coverage =coverages[name_index]>
    <#assign lineNumberss = lineNumbers[name_index]>
    <#assign covNumberss = covNumbers[name_index]>
    <testsuite casename="${name}" totalLine="${totalLine}" covLine="${covLine}" coverage="${coverage}" browser="${browsers}" >
        <#list lineNumberss as lineNumber>
           <#assign covNumber =covNumberss[lineNumber_index]> 
           <line lineNumber="${lineNumber}" covNumber="${covNumber}"></line>
        </#list>
    </testsuite>   
</#list>
</testsuites>