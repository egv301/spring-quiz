<#import "/parts/common.ftl" as c>
<@c.page "Subject list">
<#if subjects?has_content>
	<h2>Subjects</h2>
        <table>
            <thead>
            <tr>
                <th>Title</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <#list subjects as subject>
            	<tr>
                	<td>${subject.title}</td>
                	<td><a href="/admin/show-subject/${subject.id}">Show questions</a></td>
                	<td><a href="/admin/edit-subject/${subject.id}">Edit</a></td>
                	<td>
                		<form method="POST" action="/admin/remove-subject/${subject.id}">
                			<input type="submit" value="remove">
                		</form>
                	</td>
            	</tr>
            </#list>
            
            </tbody>
        </table>
<#else>
No questions
</#if>
 <div><a href="/admin/add-subject">Add new subject</a></div>
 <div><a href="/admin/stats">Stats</a></div>

</@c.page>