<#import "/parts/common.ftl" as c>
<@c.page "Quiz list">
<#if !loggedIn>
	<div><a href="/login">login</a></div>
	<div><a href="/registration">register</a></div>
<#else>
	<div><a href="/logout">Logout</a></div>
	<div><a href="/profile">My profile</a></div>
</#if>
<#if isAdmin>
	<div><a href="/admin/subject-list">Admin page</a></div>
</#if>
<#if subjectsList?has_content>
	<div class="container">
		<h2>Subjects</h2>
        <#list subjectsList as subject>
        	<div class="quiz-item">
	    		<div>${subject.subjectTitle}</div>
	        	<div>
					<#if !loggedIn>
						<div style="padding:10px;background:lightgreen;">Login to take quiz</div>
					<#elseif loggedIn && subject.canPass>
						<a class="start-link" href="/quiz-start/${subject.subjectId}">Start quiz</a>
					<#elseif loggedIn && !subject.canPass>
						<div style="padding:10px;background:lightgray;">Quiz already passed</div>
					</#if>
				</div>
	        </div>
	    </#list>
    </div>
<#else>
	No Subjects to show
</#if>
</@c.page>