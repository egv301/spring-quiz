<#import "/parts/common.ftl" as c>
<@c.page "Quiz">
	<div class="container">
			<h2>${quizDetail.subjectTitle}</h2>
		    <#if quizDetail.questionAnswers?has_content>
		    	<form action="/submit-quiz/${quizDetail.subjectId}" method="post">
					<#list quizDetail.questionAnswers as question>
						<div style="margin-bottom:30px;">
							<div style="margin-bottom:10px;">${question.questionTitle}</div>
							<table>
								<#list question.answers as answer>
									<tr>
										<td><label for="${answer.answerId}">${answer.answerTitle}</label></td>
										<td><input type="checkbox" id="${answer.answerId}" name="${question.questionId}" value="${answer.answerId}"></td>
									</tr>
								</#list>
							</table> 
						</div>
					</#list>
					<input type="submit" value="Submit"/>
				</form>	
			</#if>  
		</div>
</@c.page>