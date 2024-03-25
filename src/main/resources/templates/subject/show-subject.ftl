<#import "/parts/common.ftl" as c>
<@c.page "Show subject">	
<h1>Subject detail</h1>
<div><a href="/admin/add-question/${subject.subject_id}">Add Question</a></div>
<div>${subject.subject_title}</div>


<#if subject.question_list?has_content>
	<h2>Questions</h2>
        <table>
            <thead>
            <tr>
                <th>Title</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <#list subject.question_list as question>
            	<tr>
                	<td>${question.title}</td>
                    <td><a href="/admin/answer-list/${question.id}">Show answers</a></td>
                	<td><a href="/admin/edit-question/${question.id}">Edit</a></td>
                	<td><a href="/admin/remove-question/${question.id}">Remove</a></td>
            	</tr>
            </#list>
            
            </tbody>
           
        </table>
<#else>
No questions
</#if>
<div><a href="/admin/subject-list">Back</a></div>
</@c.page>