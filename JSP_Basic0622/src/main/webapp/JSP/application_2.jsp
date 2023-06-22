<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%--
    	application: 자원관리 (log,path)
     --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String driver = application.getInitParameter("driver");
		String url = application.getInitParameter("url");
		String username = application.getInitParameter("username");
		String pwd = application.getInitParameter("password");
		
		/* System.out.println("driver: "+driver);
		System.out.println("url: "+url);
		System.out.println("username: "+username);
		System.out.println("password: "+pwd); */
		application.log("driver: "+driver);
		application.log("url: "+url);
		application.log("username: "+username);
		application.log("password: "+pwd);
		
		String path=application.getRealPath("/");
		System.out.print(path);
	%>
</body>
</html>