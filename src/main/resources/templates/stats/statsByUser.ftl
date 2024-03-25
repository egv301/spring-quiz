<#import "/parts/common.ftl" as c>
<@c.page "Stats by user">	
<div class="container">
	<table>
		<thead>
			<tr>
				<th>Subject</th>
				<th>Score</th>
			</tr>
		</thead>
		<tbody>
			<#list quizStats as item>
				<tr>
					<td>${item.subjectTitle}</td>
					<td>${item.quizScore}</td>
				</tr>
			</#list>
		</tbody>
    </table>
    <div><a href="/admin/stats">To main page</a></div> 
</div>
</@c.page>