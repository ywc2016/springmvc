<%@page import="com.buaa.po.UserInfo"%>
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

<title>修改</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<%
		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
	%>
	<div align="center">
		<font color="blue">修改用户信息：</font>

		<form action="save" method="post">
			<table border=1 style="margin-top: 20px;margin-bottom: 20px;">

				<tr>
					<td>姓名：</td>
					<td><input type="text" value="${userInfo.username}"
						name="username" /></td>
				</tr>
				<tr>
					<td>年龄：</td>
					<td><input type="text" value="${userInfo.age}" name="age" /></td>
				</tr>
				<tr>
					<td>体重：</td>
					<td><input type="text" value="${userInfo.weight}"
						name="weight" /></>
				</tr>
				<tr>
					<td>性别：</td>
					<td><input type="text" value="${userInfo.sex}" name="sex" /></td>
				</tr>
			</table>
	</div>

	<div align="center" style="margin-top: 20px">
		<input type="submit" value="保存"> <a href="./showUsers">返回</a>
	</div>
	<input type="hidden" value="${userInfo.id}" name="id">
	</form>
	
	<div align="center" style="margin-top: 20px">
		<a href="./logout">退出登录</a>
	</div>
</body>
</html>