<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
	<body>
		<form action="demo3" method="post">
			<input type="text" name="username">
			<input type="text" name="age">
			<input type="submit" name="提交">
		</form>
		
		<hr/>
		<form action="demo4" method="post">
			<input type="text" name="name2">
			<input type="text" name="age2">
			<input type="submit" name="提交">
		</form>
		
		<hr/>
		<form action="demo7" method="post">
			<input type="text" name="name">
			<input type="text" name="age">
			<input type="checkbox" name="hover" value="唱歌">
			<input type="checkbox" name="hover" value="跳舞">
			<input type="checkbox" name="hover" value="下棋">
			<input type="submit" name="提交">
		</form>
		
		<hr/>
		<form action="demo8" method="post">
			<input type="text" name="peo.username">
			<input type="text" name="peo.age">
			<input type="submit" name="提交">
		</form>
		
		<hr/>
		<form action="demo9" method="post">
			<input type="text" name="peo[0].username">
			<input type="text" name="peo[0].age">
			<input type="text" name="peo[1].username">
			<input type="text" name="peo[1].age">
			<input type="submit" name="提交">
		</form>
		
		<hr/>
		<a href="demo10?name=a_name&age=12">跳转</a>
		<a href="demo11/121/a_name1">跳转</a>
		<hr/>
		<a href="demo12?name=a_name&age=12">跳转</a>
	</body>
</html>