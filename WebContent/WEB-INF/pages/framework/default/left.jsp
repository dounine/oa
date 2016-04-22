<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>雅恒后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Javascript Global Variables START -->
<c:import url="/WEB-INF/pages/common/default/globalHeader.jsp" />
<!-- Javascript Global Variables END -->
<!--                       CSS                       -->
<!-- Reset Stylesheet -->
<link rel="stylesheet" href="${base}/static/framework/default/resources/css/reset.css" type="text/css" media="screen" />
<!-- Main Stylesheet -->
<link rel="stylesheet" href="${base}/static/framework/default/resources/css/style_main.css" type="text/css" media="screen" />
<!-- Invalid Stylesheet. This makes stuff look pretty. Remove it if you want the CSS completely valid -->
<link rel="stylesheet" href="${base}/static/framework/default/resources/css/invalid.css" type="text/css" media="screen" />
<!-- Colour Schemes
Default colour scheme is green. Uncomment prefered stylesheet to use it.
<link rel="stylesheet" href="${base}/static/framework/default/resources/css/blue.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${base}/static/framework/default/resources/css/red.css" type="text/css" media="screen" />  
-->
<!-- Internet Explorer Fixes Stylesheet -->
<!--[if lte IE 7]>
<link rel="stylesheet" href="${base}/static/framework/default/resources/css/ie.css" type="text/css" media="screen" />
<![endif]-->
<!--                       Javascripts                       -->
<!-- jQuery -->
<script type="text/javascript" src="${base}/static/framework/default/resources/scripts/jquery-1.3.2.min.js"></script>
<!-- jQuery Configuration -->
<script type="text/javascript" src="${base}/static/framework/default/resources/scripts/simpla.jquery.configuration.js"></script>
<!-- Facebox jQuery Plugin -->
<script type="text/javascript" src="${base}/static/framework/default/resources/scripts/facebox.js"></script>
<!-- jQuery WYSIWYG Plugin -->
<script type="text/javascript" src="${base}/static/framework/default/resources/scripts/jquery.wysiwyg.js"></script>
<!-- jQuery Datepicker Plugin -->
<script type="text/javascript" src="${base}/static/framework/default/resources/scripts/jquery.datePicker.js"></script>
<script type="text/javascript" src="${base}/static/framework/default/resources/scripts/jquery.date.js"></script>
<!--[if IE]><script type="text/javascript" src="${base}/static/framework/default/resources/scripts/jquery.bgiframe.js"></script><![endif]-->
<!-- Internet Explorer .png-fix -->
<!--[if IE 6]>
<script type="text/javascript" src="${base}/static/framework/default/resources/scripts/DD_belatedPNG_0.0.7a.js"></script>
<script type="text/javascript">
DD_belatedPNG.fix('.png_bg, img, li');
</script>
<![endif]-->
<script type="text/javascript">
	$(function() {
		setInterval(function() {
			var wrapperHeigth = $("#sidebar-wrapper").height();
			var winHeigth = $(window).height();
			if (wrapperHeigth > winHeigth) {
				$("#sidebar").css("overflow-x", "hidden").css("overflow-y", "scroll").width(250);
			} else {
				$("#sidebar").css("overflow-x", "hidden").css("overflow-y", "hidden").width(235);
			}
		}, 1000);
	});
</script>
</head>
<body style="overflow: hidden;width: 100%;height: 100%;background: #f0f0f0 url('${base}/static/framework/default/resources/images/bg-body.gif') top left repeat-y;">
	<div id="body-wrapper">
		<!-- Wrapper for the radial gradient background -->
		<div id="sidebar">
			<div id="sidebar-wrapper">
				<!-- Sidebar with logo and menu -->
				<h1 id="sidebar-title">
					<a href="#">后台管理</a>
				</h1>
				<!-- Logo (221px wide) -->
				<div class="user">
					<div>
						<img src="${base}/static/framework/default/img/avatar.png" width="44" height="44" class="hoverimg" alt="Avatar" id="Avatar" onclick="javascript:window.top.mainFrame.location='${base}/user/info.do'" />
						<p style="height: 8px;">已登录:</p>
						<p class="username">${sessionScope.login_user.name }</p>
					</div>
				</div>
				<div class="guanli">
					<div class="gl">
						<a href="${base}/main/top.do" target="mainFrame">管理</a>
					</div>
					<div class="out">
						<a href="javascript:window.parent.location='${base}/logout.do'">登出</a>
					</div>
				</div>
				<ul id="main-nav">
					<!-- Accordion Menu -->
					<li><a href="#" class="nav-top-item">系统设置 </a>
						<ul>
							<li><a href="${base}/unit/mainFrame.do" target="mainFrame">部门管理</a></li>
							<li><a href="${base}/user/mainFrame.do" target="mainFrame">用户管理</a></li>
							<li><a href="${base}/role/list.do" target="mainFrame">角色管理</a></li>
							<li><a href="${base}/userRole/edit.do" target="mainFrame">用户角色绑定</a></li>
							<li><a href="${base}/permission/list.do" target="mainFrame">权限管理</a></li>
							<li><a href="${base}/rolePermission/edit.do" target="mainFrame">角色权限绑定</a></li>
							<li><a href="${base}/logger/list.do" target="mainFrame">日志管理</a></li>
						</ul></li>
					<li><a href="#" class="nav-top-item">系统文件 </a>
						<ul>
							<li><a href="${base}/webFile/list.do" target="mainFrame">统一文件管理</a></li>
							<li><a href="${base}/ueditorFile/list.do" target="mainFrame">UEditor文件管理</a></li>
						</ul></li>
					<li><a href="#" class="nav-top-item">调查问卷 </a>
						<ul>
							<li><a href="${base}/questionnaire/list.do" target="mainFrame">问卷管理</a></li>
							<li><a href="${base}/questionnaireQuestion/list.do" target="mainFrame">题目管理</a></li>
							<li><a href="${base}/questionnaireStatistics/list.do" target="mainFrame">问卷统计</a></li>
						</ul></li>
					<li><a href="#" class="nav-top-item">个人空间 </a>
						<ul>
							<li><a href="${base}/personalProfile/detail.do" target="mainFrame">个人资料</a></li>
							<li><a href="${base}/personalExperience/detail.do" target="mainFrame">个人经历</a></li>
						</ul></li>
				</ul>
				<!-- End #main-nav -->
			</div>
		</div>
		<!-- End #sidebar -->
	</div>
</body>
<!-- Download From www.exet.tk-->
</html>