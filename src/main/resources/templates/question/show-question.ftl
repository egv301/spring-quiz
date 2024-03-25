<#import "/parts/common.ftl" as c>
<@c.page "Show question">
<h1>Question info</h1>
	Question title:
	<div>${question.title}</div>
	Question points:
	<div>${question.points}</div>
	<div><a href="/admin/add-answer/${question.id}">Add answer</a></div>
	<div><a href="/admin/answer-list/${question.id}">Question's answers</a></div>
	<div><a href="/admin/add-question/${question.subject}">Add question</a></div>
	<div><a href="/admin/show-subject/${question.subject}">Back</a></div>
</@c.page>