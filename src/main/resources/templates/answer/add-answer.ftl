<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add answer</title>
</head>
<body>
<form action="/admin/add-answer/${question_id}/" method="post">
	<label for="title">Text</label>
    <input type="text" name="answerTitle" id="title" placeholder="Answer title">
    <input type="submit" value="Add answer">	
</form>
</body>
</html>