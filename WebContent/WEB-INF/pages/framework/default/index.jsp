<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>艾佳天诚信息技术有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
</head>
<frameset cols="235,*" frameborder="0" border="0" framespacing="0">
	<frame src="${base}/main/left.do" name="leftFrame" id="leftFrame" noresize="noresize" style="background-color: black; height: 100%; width: 1000px;" />
	<!-- <frameset rows="170,*" frameborder="0" border="0" framespacing="0"> -->
	<%-- <frame src="${base}/main/top.do" name="topFrame" /> --%>
	<frame src="${base}/main/main.do" name="mainFrame" id="mainFrame" />
</frameset>
<!-- </frameset> -->
<noframes>
	<body>
	</body>
</noframes>
</html>