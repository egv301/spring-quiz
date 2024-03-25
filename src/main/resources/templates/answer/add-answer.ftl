<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add answer</title>
</head>
<body>
<form action="/admin/add-answer/${question_id}/" method="post">
	<label for="title">Text</label>
    <input type="text" name="title" id="title" placeholder="Answer title">
    <label for="isCorrect">is Correct</label>
    <input type="checkbox" name="isCorrect" id="isCorrect">
    <input type="submit" value="Add answer">	
</form>
</body>
</html>