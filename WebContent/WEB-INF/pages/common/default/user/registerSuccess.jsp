<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>注册成功</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Progma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache,must-revalidate" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/user/register.js"></script>
<link href="${base}/static/common/default/css/user/registerSuccess.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="index">
		<div class="top">
			<img src="${base}/static/common/default/img/user/ttop.jpg" width="900" height="154" />
		</div>
		<div class="jingdu">
			<img src="${base}/static/common/default/img/user/top3.jpg" width="900" height="40" />
		</div>
		<div class="zhuce">
			<div style="font-family: '微软雅黑'; margin: 0 auto; padding-top: 50px; padding-bottom: 50px; width: 400px; font-size: 20px;">
				<img src="${base}/static/common/default/img/user/chenggong.jpg" />恭喜你，注册成功！
			</div>
			<div style="margin: 0 auto; padding-bottom: 50px;">
				<a href="javascript:void(0);"><img src="${base}/static/common/default/img/user/login.jpg" width="135" height="32" onclick="javascript: window.location.href='${base}/login.do';" style="cursor: pointer;" /></a>
			</div>
			<div class="clear"></div>
		</div>
		<div class="footer">
			Copyright Yaheen.net. All Rights Reserved. <a href="http://www.miibeian.gov.cn/" rel="noreferrer" target="_blank">粤ICP备13045457号-1</a>
		</div>
	</div>
</body>
</html>