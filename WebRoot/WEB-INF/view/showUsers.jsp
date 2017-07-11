<%@page import="com.buaa.po.UserInfo"%>
<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.Math.*"%>
<%@ page import="java.io.*"%>
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

<title>My JSP 'show.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<div align="center">
		<strong>分页显示数据库记录</strong>
	</div>
	<br>
	<hr>
	<table width="800" border="1" align="center">
		<tr>
			<th>姓名</th>
			<th>年龄</th>
			<th>体重</th>
			<th>性别</th>
			<th colspan="2">操作</th>
		</tr>

		<%
			List<UserInfo> userInfos = (List<UserInfo>) request.getAttribute("rows");
			Iterator iterator = userInfos.iterator();
			for (; iterator.hasNext();) {
			UserInfo userInfo = (UserInfo)iterator.next();
		%>
		<tr>
			<td><%=userInfo.getUsername()%></td>
			<td><%=userInfo.getAge()%></td>
			<td><%=userInfo.getWeight()%>kg</td>
			<td><%=userInfo.getSex()%></td>
			<td width="80px"><a
				href="modifyUser?id=<%=userInfo.getId() + ""%>">修改用户</a></td>
			<td width="80px"><a
				href="deleteUser?id=<%=userInfo.getId()+ ""%>"> 删除用户</a></td>
		</tr>
		<%
			}
		%>
	</table>
	<div align="center" style="margin-top: 10px">第${currentPage}页,共${total}页</div>
	<div align="center" style="margin-top: 20px">
		<%
			if (Integer
					.parseInt(request.getAttribute("currentPage").toString()) > 1) {
		%>
		<a href="./showUsers?page=${currentPage-1}" style="margin-right: 10px">上一页</a>
		<%
			}
		%>
		<%
			for (int i = 1; i <= Integer.parseInt(request.getAttribute("total")
					.toString()); i++) {
		%>
		<a href="./showUsers?page=<%=i%>"
			<%if (i == Integer.parseInt(request.getAttribute("currentPage")
						.toString())) {%>
			style="color: red" <%}%>><%=i%> </a>
		<%
			}
		%>
		
		<%
			if (Integer
					.parseInt(request.getAttribute("currentPage").toString()) < Integer
					.parseInt(request.getAttribute("total").toString())) {
		%>
		<a href="./showUsers?page=${currentPage+1}" style="margin-left: 10px">下一页</a>
		
		<%
			}
		%>
	</div>
	<div align="center" style="margin-top: 20px">
		<a href="./login" style="margin-right: 15px">返回</a><a href="./logout">退出登录</a>
	</div>
</body>
</html>
