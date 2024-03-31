<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add answer</title>
</head>
<body>
<form action="/admin/edit-answer/${answer.answerId}" method="post">
	<label for="answerTitle">Text</label>
    <input type="text" name="answerTitle" id="answerTitle" placeholder="Answer title" value="${answer.answerTitle}">
    <#if titleError??>
    	<span>${titleError}</span>
    </#if>
    <input type="submit" value="Update answer">
</form>
</body>
</html