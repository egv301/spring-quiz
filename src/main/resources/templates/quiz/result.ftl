<#import "/parts/common.ftl" as c>
<@c.page "Results">	
	<div class="container">
		<div class="subject-title">${quizResult.subjectTitle}</div>
		<div>Score is <span style="color:red">${quizResult.quizScore}</span></div>
		<a href="/">To Main Page</a>
	</div>
</@c.page>	