<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add answer</title>
</head>
<body>
<form action="/admin/edit-answer/${answer.answerId}" method="post">
	<label for="title">Text</label>
    <input type="text" name="title" id="title" placeholder="Answer title" value="${answer.answerTitle}">
    <#if titleError??>
    	<span>${titleError}</span>
    </#if>
    <label for="isCorrect">is Correct</label>
    <input type="checkbox" name="isCorrect" id="isCorrect" <#if answer.correct>checked</#if> />
    <#if isCorrectError??>
    	<span>${isCorrectError}</span>
    </#if>
    <input type="submit" value="Update answer">
</form>
</body>
</html