<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织架构管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<c:import url="/WEB-INF/pages/common/default/zTreeHeader.jsp" />
<script type="text/javascript" src="${base}/static/common/default/js/user/leftFrame.js"></script>
</head>
<body>
	<input type="hidden" id="openTreeNode" value="${param.openTreeNode}" />
	<div class="zTreeDemoBackground left">
		<ul id="zTree" class="ztree"></ul>
	</div>
</body>
</html>