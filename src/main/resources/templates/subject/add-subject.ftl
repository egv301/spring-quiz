<#import "/parts/common.ftl" as c>
<@c.page "Add subject">	
<h1>Add new subject</h1>
<form action="/admin/add-subject" method="post">
    <label for="title">Text</label>
    <input type="text" name="title" id="title" placeholder="Subject title">
    <#if titleError??>
    	<span>${titleError}</span>
    </#if>
    <input type="submit" value="Add subject">
</form>
</@c.page>	