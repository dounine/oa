<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<title>用户角色关联</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/userRole/edit.js"></script>
<link href="${base}/static/common/default/css/userRole/edit.css" rel="stylesheet" type="text/css" />
</head>
<body style="font-size: 14px;">
	<form method="post" id="saveForm" style="margin: 10px;">
		<div class="control-group">
			<caption>
				<h3>用户角色关联</h3>
			</caption>
		</div>
		<div class="control-group">
			<label class="control-label" for="courseId">用户：</label>
			<div class="controls">
				<input name="userId" id="userId" style="width: 300px" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="courseId">角色：</label>
			<div class="controls">
				<input type="hidden" name="roleIds" id="roleIds" style="width: 600px;" />
			</div>
			<input type="hidden" name="ajaxRolesVal" id="ajaxRolesVal" />
		</div>
		<button type="button" class="btn" data-dismiss="modal" onclick="javascript:location.reload();">关闭</button>
		<button type="button" class="btn btn-primary" onclick="javascript:saveData();">保存</button>
	</form>
	<c:import url="/WEB-INF/pages/common/default/modalDialog.jsp" />
</body>
</html>