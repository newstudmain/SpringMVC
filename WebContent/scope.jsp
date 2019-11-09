<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${requestScope.reqParam } <br/>
${sessionScope.session } <br/>
${sessionScope.sessionParam } <br/>
${applicationScope.application }
<hr>
${mapK } <br/>
${requestScope.mapK }
<hr>
${model } <br/>
${requestScope.model }
<hr>
${mav } <br/>
${requestScope.mav }
</body>
</html>