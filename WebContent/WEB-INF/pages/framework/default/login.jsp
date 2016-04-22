<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>艾佳天诚信息技术有限公司</title>
<meta name="description" content="Flat UI Kit Free is a Twitter Bootstrap Framework design and Theme, this responsive framework includes a PSD and HTML version." />
<meta name="viewport" content="width=1000, initial-scale=1.0, maximum-scale=1.0">
<SCRIPT type="text/javascript" src="${base}/static/plugins/FlatUI/js/jquery-1.8.3.min.js"></SCRIPT>
<!-- Loading Bootstrap -->
<link href="${base}/static/plugins/FlatUI/bootstrap/css/bootstrap.css" rel="stylesheet">
<!-- Loading Flat UI -->
<link href="${base}/static/plugins/FlatUI/css/flat-ui.css" rel="stylesheet">
<link href="${base}/static/plugins/FlatUI/css/demo.css" rel="stylesheet">
<link rel="shortcut icon" href="images/favicon.ico">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
      <script src="${base}/static/plugins/FlatUI/js/html5shiv.js"></script>
      <script src="${base}/static/plugins/FlatUI/js/respond.min.js"></script>
<![endif]-->
<STYLE type="text/css">
.login2 {
	color: #ffffff;
	margin-bottom: 77px;
	position: relative;
	border-color: #13202C;
	border-width: 38px;
	border-style: solid;
	border-radius: 25px 25px 25px 25px;
}

.login-screen2 {
	background-color: #1abc9c;
	min-height: 473px;
	padding: 123px 199px 33px 306px;
	overflow: hidden;
}

.container2 {
	padding-right: 15px;
	padding-left: 15px;
	margin-right: auto;
	margin-left: auto;
	margin-bottom: auto;
	margin-top: auto;
	max-width: 970px;
	margin-top: auto;
}
</STYLE>
<!--[if IE]>
<STYLE type="text/css">
#login-name,#login-pass {
	line-height: 10mm;
}
</STYLE>
<SCRIPT type="text/javascript">
	$(function() {
		input("login-name", "Enter your name");
	});
	function input(theInput, val) {
		var $input = $("#" + theInput + "");
		var val = val;
		$input.attr({
			value : val
		});
		$input.focus(function() {
			if ($input.val() == val) {
				$(this).attr({
					value : ""
				});
			}
		}).blur(function() {
			if ($input.val() == "") {
				$(this).attr({
					value : val
				});
			}
		});
	}
</SCRIPT>
<![endif]-->
</head>
<body>
	<div class="container2">
		<div class="login2" style="margin-bottom: 50px; margin-top: 50px;">
			<div class="login-screen2">
				<div class="login-icon" style="left: 50px; top: 125px;">
					<img src="${base}/static/plugins/FlatUI/images/login/login_pic.png" style="width: 250px; height: 230px;" />
				</div>
				<div class="login-form">
					<form action="${base}/login.do" method="post">
						<div class="form-group">
							<input style="padding-bottom: 0px; padding-top: 0px;" type="text" class="form-control login-field" value="" placeholder="Enter your name" id="username" name="username" /> <label class="login-field-icon fui-user" for="username"></label>
						</div>
						<div class="form-group">
							<input style="padding-bottom: 0px; padding-top: 0px;" type="password" class="form-control login-field" value="" placeholder="Password" id="password" name="password" /> <label class="login-field-icon fui-lock" for="password"></label>
						</div>
						<input type="submit" class="btn btn-primary btn-block" value='Login'> <a style="padding-left: 15px; width: 100%; padding-right: 15px; color: red;" href="#">${loginErrMsg}</a>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
	<!-- Load JS here for greater good =============================-->
	<script src="${base}/static/plugins/FlatUI/js/jquery-1.8.3.min.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/jquery.ui.touch-punch.min.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/bootstrap.min.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/bootstrap-select.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/bootstrap-switch.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/flatui-checkbox.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/flatui-radio.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/jquery.tagsinput.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/jquery.placeholder.js"></script>
	<script src="${base}/static/plugins/FlatUI/js/jquery.stacktable.js"></script>
</body>
</html>