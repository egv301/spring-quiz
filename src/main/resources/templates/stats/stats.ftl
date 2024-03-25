<#import "/parts/common.ftl" as c>
<@c.page "Edit subject">	
<div class="container">
	<#if quizCount?has_content>
		<table>
            <thead>
	            <tr>
	                <th>Title</th>
	                <th>Times Passed</th>
	            </tr>
            </thead>
            <tbody>
	            <#list quizCount as item>
	            	<tr>
	                	<td><a href="/admin/stats/${item.subjectId}">${item.subjectTitle}</a></td>
	                	<td>${item.count}</td>
	                </tr>
	            </#list>
            </tbody>
        </table>
		<div style="margin-top:15px;"><a style="color:green" href="/admin/subject-list">To main page</div>
	</#if>
</div>
</@c.page>