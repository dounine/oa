<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	${user.id}&nspb;${user.name}
	<form action="test3.do">
		<input name="id" value="1" /> <input name="name" value="mayj" />
	</form>
</body>
</html>