<#import "/parts/common.ftl" as c>
<@c.page "Profile page">
	<div class="container">
		<div class="profile-username">${user.username}</div>
		<#if user.quizResults?has_content>
			<div class="result-header">
				<div>Quiz name</div>
				<div>Score</div>
			</div>
			<#list user.quizResults as quiz>
				<div class="quiz-item">
					<div>
						<div class="quiz-subject">${quiz.subject.title}</div>
						<div><a href="/show-quiz-answers/${quiz.subject.id}">Show details</a></div>
					</div>
					
					<div class="quiz-score">${quiz.result}</div>
				</div>
			</#list>
		<#else>
			No quizes yet
		</#if>
		<div><a href="/">To Main page</a></div>
	</div>
</@c.page>