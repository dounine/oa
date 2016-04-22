<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<title>用户注册</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Progma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache,must-revalidate" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/user/register.js"></script>
<link href="${base}/static/common/default/css/user/register.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<STYLE type="text/css">
#sex{width: 220px;}
</STYLE>
<![endif]-->
<STYLE type="text/css">
select {
	width: 206px;
}
</STYLE>
</head>
<body>
	<form action="registerSubmit.do" method="post" id="mainForm">
		<div class="index">
			<div class="top">
				<img src="${base}/static/common/default/img/user/ttop.jpg" width="900px" height="154px" />
			</div>
			<div class="jingdu">
				<img src="${base}/static/common/default/img/user/top.jpg" width="900px" height="40px" />
			</div>
			<div class="zhuce">
				<div class="left">
					<a style="padding-left: 15px; width: 100%; padding-right: 15px; color: red; display: block;" href="jacascript:void(0);">${error}</a>
					<table width="301px" border="0px" cellspacing="0" cellpadding="0">
						<tr>
							<td>用户名：</td>
							<td><input name="username" type="text" />&nbsp;</td>
						</tr>
						<tr>
							<td>密码：</td>
							<td><input name="password" type="password" />&nbsp;</td>
						</tr>
						<tr>
							<td>确认密码：</td>
							<td><input name="cPassword" type="password" />&nbsp;</td>
						</tr>
						<tr>
							<td>真实姓名：</td>
							<td><input name="name" type="text" />&nbsp;</td>
						</tr>
						<tr>
							<td>性别：</td>
							<td><select class="form-control" name="sex" id="sex">
									<option value="M">男</option>
									<option value="F">女</option>
							</select>&nbsp;</td>
						</tr>
						<tr>
							<td>单位：</td>
							<td><input name="unit" type="text" />&nbsp;</td>
						</tr>
						<tr>
							<td>电子邮箱：</td>
							<td><input name="email" type="text" />&nbsp;</td>
						</tr>
						<tr>
							<td>手机号码：</td>
							<td><input name="mobile" type="text" />&nbsp;</td>
						</tr>
					</table>
					<div style="padding-top: 20px; width: 500px;">
						<img src="${base}/static/common/default/img/user/zc.jpg" width="108px" height="33px" style="cursor: pointer; float: left; padding-left: 150px;" onclick="javascript: if($('#mainForm').valid()){$('#mainForm').submit();}" /> <img
							src="${base}/static/common/default/img/user/back.jpg" width="108px" height="33px" style="cursor: pointer; float: right;" onclick="javascript: window.location.href='${base}/login.do';" />
					</div>
				</div>
				<div class="right">
					<img src="${base}/static/common/default/img/user/diannao.png" width="330" height="262" />
				</div>
				<div class="clear"></div>
			</div>
			<div class="footer">
				Copyright Yaheen.net. All Rights Reserved. <a href="http://www.miibeian.gov.cn/" rel="noreferrer" target="_blank">粤ICP备13045457号-1</a>
			</div>
		</div>
		<c:import url="/WEB-INF/pages/common/default/modalDialog.jsp" />
	</form>
</body>
</html>