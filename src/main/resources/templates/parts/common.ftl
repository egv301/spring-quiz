<#macro page pageTitle>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <style>
    	body{
    		font-family:Arial;
    	}
    	.container {
    		width:80%;
    		margin:0 auto;
    		background:lightblue;
    		padding:20px;
    	}
    	
    	.result-header {
    		display:flex;
    		justify-content:space-between;
    		font-weight:bold;
    		margin:25px 0px;
    	}
    	.quiz-item {
    		display:flex;
    		justify-content:space-between;
    		padding:20px;
    		border:2px solid blue;
    	}
    	.quiz-subject{
    		margin-bottom:20px;
    	}
    	.subject-title{
    		margin-bottom:20px;
    	}
    	
    	.start-link{
    		padding:10px;
    		background:red;
    		text-decoration:none;
    		color:white;
    		font-weight:bold; 	
    	}
    	
    	.quiz-score{
    		color:red;
    		font-weight:bold;
    	}
    	
    	a {
    		text-decoration:none;
    	}
    </style>
</head>
<body>	
<#nested>
</body>
</html>
</#macro>