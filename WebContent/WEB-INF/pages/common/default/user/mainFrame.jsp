<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织架构管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
</head>
<frameset rows="50,*" frameborder="0" border="0" framespacing="0"> <frame src="${base}/user/topFrame.do" name="topFrame" id="topFrame" noresize="noresize" style="height:100%;width:1000px;" /> <frameset cols="220,*" id="bottomFrame" name="bottomFrame" frameborder="0"
	border="0" framespacing="0"> <frame src="${base}/user/leftFrame.do" name="leftFrame" id="leftFrame" /> <frame src="${base}/user/list.do?frame=true" name="bodyFrame" id="bodyFrame" /> </frameset> </frameset>
<noframes>
	<body></body>
</noframes>
</html>