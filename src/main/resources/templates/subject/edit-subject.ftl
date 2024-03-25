<#import "/parts/common.ftl" as c>
<@c.page "Edit subject">	
<form method="post" action="/admin/edit-subject/${subject.id}">
	<div>
	    <label id="title" for="title">Text</label>
	    <input type="text" name="title" id="title" placeholder="Subject title" value="${subject.title}" />
    </div>
    <div>
	    <label id="status" for="title">Subject open</label>
	    <input type="checkbox" name="isActive" id="status" <#if subject.isActive>checked</#if> />
    </div>
    <input type="submit" value="Edit subject">
</form>
</@c.page>