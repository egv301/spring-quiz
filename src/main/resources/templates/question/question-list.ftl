<#import "/parts/common.ftl" as c>
<@c.page "Question list">
<h2>Questions - ${question_details.subjectTitle}</h2>
    <table>
        <thead>
        <tr>
            <th>Title</th>
            <th>Points</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
            <#list question_details.questionList as question>
                <tr>
                    <td>${question.title}</td>
                    <td>${question.points}</td>
                    <td><a href="/admin/edit-question/${question.id}">Edit</a></td>
                    <td>
                    	<form method="POST" action="/admin/remove-question/${question.id}">
                			<input type="submit" value="remove">
                		</form>
                	</td>
                </tr>
            </#list>
        </tbody>
    </table>
    <div><a href="/admin/add-question/${question_details.subjectId}">Add question</a></div>
</@c.page>