<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>Qunit List Page---   Project:${project}---</title>
<meta content="text/html; charset=UTF-8" http-equiv='Context-Type' />
    <script type="text/javascript" src="/resource/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="/resource/brtest.js"></script>
    <link href="/resource/list.css" type="text/css" rel="stylesheet"/>
</head>
<body>
    <div class="main">
        <div id="id_caselist" class="testlist">
      <#if filter?exists>
        <#list kisses as kiss>
        <#if kiss.name?contains(filter)>
	    <a title="${kiss.name}">${kiss.name}</a>
	    </#if>
        </#list>
        <#list noKisses as kiss>
        <#if kiss.name?contains(filter)>
	    <b class="nocase" title="${kiss.name}">${kiss.name}</b><br/>
	    </#if>
        </#list>
      <#else>
        <#list kisses as kiss>
	    <a title="${kiss.name}">${kiss.name}</a>
        </#list>
        <#list noKisses as kiss>
        <b class="nocase" title="${kiss.name}">${kiss.name}</b><br/>
        </#list>
      </#if>
        </div>
        <div class="runningarea"></div>
    </div>
</body>
</html>
