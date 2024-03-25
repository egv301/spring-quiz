<#import "/parts/common.ftl" as c>
<@c.page "Add question">
<h1>${subject.title}</h1>
<form action="/admin/add-question/${subject.id}" method="post">
	
    <label for="title">Text</label>
    <input type="text" name="title" id="title" placeholder="Question title">
    <#if titleError??>
    	<span>${titleError}</span>
    </#if>
    <label for="points">Points</label>
    <input type="number" name="points" id="points" value="0" placeholder="Poinst for question">
    <#if pointsError??>
    	<span>${pointsError}</span>
    </#if>
    <input type="submit" value="Add question">
</form>
</@c.page>