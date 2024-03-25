<#import "/parts/common.ftl" as c>
<@c.page "Edit question">
<form action="/admin/edit-question/${question.id}" method="POST">
	<input type="hidden" name="id" value="${question.id}" />
	<label for="title">Text</label>
    <input type="text" name="title" id="title" placeholder="Question title" value="${question.title}">
    <label for="points">Points</label>
    <input type="number" name="points" id="points" placeholder="Points for question" value="${question.points}" />
    <label for="isMultichoice">Multichoice</label>
    <input type="submit" value="Update question">
</form>
</@c.page>