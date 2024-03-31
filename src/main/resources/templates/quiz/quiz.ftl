<#import "/parts/common.ftl" as c>
<@c.page "Quiz">
	<div class="container">
		<h2>${quizDetail.subjectTitle}</h2>
		<#if quizDetail.questionAnswers?has_content>
			<div class="quiz" data-subjectid='${quizDetail.subjectId}'>
				<#list quizDetail.questionAnswers as question>
					<div style="margin-bottom:30px;">
						<div style="margin-bottom:10px;">${question.questionTitle}</div>
						<table>
							<#list question.answers as answer>
								<tr>
									<td><label for="${answer.answerId}">${answer.answerTitle}</label></td>
									<td><input type="radio" id="${answer.answerId}" data-questionid='${question.questionId}' name="${question.questionId}" value="${answer.answerId}"></td>
								</tr>
							</#list>
						</table> 
					</div>
				</#list>
				<div><a href="/show-results/${quizDetail.subjectId}">Finish</a></div>
			</div>	
		</#if>  
	</div>
</@c.page>