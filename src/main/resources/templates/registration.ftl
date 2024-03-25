<#import "/parts/common.ftl" as c>
<@c.page "Registration">
	<h1>Registration</h1>
	<form action="/registration" method="post">
	<#if usernameError??>
    	<span>${usernameError}</span>
    </#if>
	<#if passwordDontMatch??>
    	<span>${passwordDontMatch}</span>
    </#if>
	<#if usernameExists??>
    	<span>${usernameExists}</span>
    </#if>
	<div>Username</div>
	<div><input type="text" name="username" placeholder="Username"></div>
	<div>Password</div>
	<div><input type="password" name="password" placeholder="Password"></div>
	<div>Password Confirm</div>
	<div><input type="password" name="passwordConfirm" placeholder="Confirm Password"></div>
	<input type="submit" value="Register"/>
	</form>
	<div>To main page</div>
</@c.page>