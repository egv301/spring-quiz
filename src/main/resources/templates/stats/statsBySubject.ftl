<#import "/parts/common.ftl" as c>
<@c.page "Subject stats">	
<div class="container">
	<table>
		<thead>
			<tr>
				<th>Username</th>
				<th>Score</th>
			</tr>
		</thead>
		<tbody>
			<#list quizStats as item>
				<tr>
					<td><a href="/admin/stats/user/${item.userId}">${item.userName}</></td>
					<td>${item.score}</td>
				</tr>
			</#list>
		</tbody>
    </table>
</div>
</@c.page>