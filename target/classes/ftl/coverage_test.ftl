<?xml version="1.0" encoding="UTF-8"?>
<testsuites>
<#list casenames as name> 
    <#assign lineNumberss = lineNumbers[name_index]>
    <#assign covNumberss = covNumbers[name_index]>
    <testsuite casename="${name}" browser="${browser}">
        <#list lineNumberss as lineNumber>
           <#assign covNumber =covNumberss[lineNumber_index]> 
           <line lineNumber="${lineNumber}" covNumber="${covNumber}"></line>
        </#list>
    </testsuite>   
</#list>
</testsuites>