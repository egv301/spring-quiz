<#import "/parts/common.ftl" as c>
<@c.page "Quiz Answers Detail">
	<div class="container">
			<h2>${quizDetail.subjectTitle}</h2>
		    <#if quizDetail.questionAnswers?has_content>
		    	<#list quizDetail.questionAnswers as question>
						<div style="margin-bottom:30px;">
							<div style="margin-bottom:10px;">${question.questionTitle}</div>
							<table>
								<#list question.answers as answer>
                                    <#assign bgColor>
                                        <#if answer.answerStatus == "CORRECT">
                                            green
                                        <#elseif answer.answerStatus == "INCORRECT">
                                            red
                                        </#if>
                                    </#assign>
                                    <div style="background-color: ${bgColor};">${answer.answerTitle}</div>
								</#list>
							</table> 
						</div>
					</#list>
			    </#if>  
            <div><a href="/profile">Back</div>
		</div>
</@c.page>
<#--  <h1>Hello</h1>  -->