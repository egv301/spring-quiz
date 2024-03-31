<#import "/parts/common.ftl" as c>
<@c.page "Login">
<h1>Login</h1>
	<form action="/login" method="post">
	<div>Username</div>
	<div><input type="text" name="username" placeholder="Username"></div>
	<div>Password</div>
	<div><input type="password" name="password" placeholder="Password"></div>
	<input type="submit" value="Login"/>
</form>
<div style="margin-top:30px;"><a href="/registration">Register</a></div>
</@c.page>