<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Answers - ${answersDetails.questionTitle}</title>
</head>
<body>
<#if answersDetails.answerList?has_content>
	<h2>Answers</h2>
        <table>
            <thead>
            <tr>
                <th>Title</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <#list answersDetails.answerList as answer>
            	<tr>
                	<td>${answer.answerTitle}</td>
                    <td>
                    <#if answer.correct>
                        <span style="background:green;color:white;";>correct</span>
                    <#else>
                        <form method="POST" action="/admin/set-correct-answer/${answer.answerId}">
                			<input type="submit" value="make correct">
                		</form>
                    </#if>
                    </td>
                	<td><a href="/admin/edit-answer/${answer.answerId}">Edit</a></td>
                	<td>
                		<form method="POST" action="/admin/remove-answer/${answer.answerId}">
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
<div><a href="/admin/add-answer/${answersDetails.questionId}">Add answer</div>
<div><a href="/admin/show-question/${answersDetails.questionId}">Back</div>
</body>
</html>