	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title> Easyui+spring+springmvc+hibernate+控制模型+后台</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/login.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	
	<h1>Easyui+spring+springmvc+hibernate+控制模型+后台</h1>
	<div class="login-form">
		<div class="close"></div>
		<div class="head-info">
			<label class="lbl-1"> </label> <label class="lbl-2"> </label> <label
				class="lbl-3"> </label>
		</div>
		<div class="clear"></div>
		<div class="avtar">
			<img src="${pageContext.request.contextPath }/resources/img/avtar.png" />
		</div>
		<form action="${pageContext.request.contextPath }/login" method="post">
			<input type="text" class="text" name="username" value="Username"
				onFocus="this.value = '';"
				onBlur="if (this.value == '') {this.value = 'Username';}">
			<div class="key">
				<input type="password" value="Password" name="password" onFocus="this.value = '';"
					onBlur="if (this.value == '') {this.value = 'Password';}">
			</div>
			
			<div class="signin">
				<input type="submit" value="Login" id="login">
			</div>
		</form>
		
	</div>
<script type="text/javascript">
		
</script>

</body>
</html>