<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>addUser</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<div align="center">
		添加信息<br /> <br />
		<form method="post" action="add">
			<table border=1 style="margin-top: 20px;margin-bottom: 20px;">
				<tr>
					<td>用户名：</td>
					<td><input type="text" name="username" /></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td>年龄：</td>
					<td><input type="text" name="age" /></td>
				</tr>
				<tr>
					<td>体重：</td>
					<td><input type="text" name="weight" /></td>
				</tr>
				<tr>
					<td>性别：</td>
					<td><input type="text" name="gender" /></td>
				</tr>

			</table>
	</div>

	<div align="center" style="margin-top: 20px">
		<input type="submit" value="提交"> <a href="./login">返回</a>
	</div>
	</form>

	<div align="center" style="margin-top: 20px">
		<a href="./logout">退出登录</a>
	</div>
</body>
</html>