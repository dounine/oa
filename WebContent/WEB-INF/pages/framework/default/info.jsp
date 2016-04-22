<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>用户</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/framework/default/js/info.js"></script>
<link href="${base}/static/framework/default/css/info.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 10px;">
	<!--增加编辑框-->
	<div class="modal" id="editDialog" style="top: 10%;">
		<form action="infoSave.do" method="post" id="saveForm" class="form-horizontal">
			<input type="hidden" id="operType" /> <input type="hidden" id="url" value="/user/infoUpdate.do" /> <input type="hidden" name="id" clear="true" value="${fn:escapeXml(user.id)}" />
			<div class="modal-header">
				<a class="close" data-dismiss="modal"></a>
				<h3 id="tableTitle">修改个人信息</h3>
			</div>
			<div class="modal-body">
				<table>
					<tbody>
						<tr>
							<td><label class="sr-only" for="username" style="width: 280px;">用户名&nbsp;&nbsp;&nbsp;</label> <input type="text" readonly="true" class="form-control" name="username" placeholder="用户名" value="${fn:escapeXml(user.username)}" /></td>
							<td><label class="sr-only" for="name">真实姓名</label> <input type="text" class="form-control" name="name" placeholder="真实姓名" value="${fn:escapeXml(user.name)}" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="oldPassword">旧密码</label> <input type="password" class="form-control" name="oldPassword" id="oldPassword" placeholder="旧密码">
								</div>
							</td>
							<td><label class="sr-only" for="phone">固话</label> <input type="text" class="form-control" name="phone" id="phone" placeholder="固话" value="${fn:escapeXml(user.phone)}" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="password">新密码</label> <input type="password" class="form-control" name="password" id="password" placeholder="新密码">
								</div>
							</td>
							<td><label class="sr-only" for="confirmPassword">确认密码</label> <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="确认密码" /></td>
						</tr>
						<tr>
							<td><label class="sr-only" for="sex">性别</label> <select class="form-control" name="sex" style="width: 206px;" value="${fn:escapeXml(user.sex)}">
									<option value="M" <c:if test="${fn:escapeXml(user.sex) == 'M'}">selected="selected"</c:if>>男</option>
									<option value="F" <c:if test="${fn:escapeXml(user.sex) == 'F'}">selected="selected"</c:if>>女</option>
							</select></td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="mobile">手机号码</label> <input type="text" class="form-control" name="mobile" id="mobile" placeholder="手机号码" value="${fn:escapeXml(user.mobile)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="email">电子邮件</label> <input type="email" class="form-control" name="email" placeholder="电子邮件" value="${fn:escapeXml(user.email)}" />
								</div>
							</td>
							<td><label class="sr-only" for="education">学历</label> <input type="text" class="form-control" name="education" id="education" placeholder="学历" value="${fn:escapeXml(user.education)}" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="jobTitle">职称</label> <input type="text" class="form-control" name="jobTitle" id="jobTitle" placeholder="职称" value="${fn:escapeXml(user.jobTitle)}" />
								</div>
							</td>
							<td></td>
						</tr>
						<yahnc:operatedUnitSelectInfo value="${fn:escapeXml(operatedUnitId)}" />
						<c:if test="${not (user.getClass().name eq SUPER_USER_CLASS_NAME)}">
							<tr>
								<td colspan="2">
									<div class="form-group">
										<label class="sr-only" for="unitId">单位</label> <input type="hidden" style="width: 500px;" clear="true" select2="true" id="unitId" name="unitId" value="${fn:escapeXml(unitId)}" readonly="readonly" />
									</div>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</form>
		<div class="modal-footer">
			<button id="saveBtn" type="button" class="btn btn-primary" onclick="sendData($('#editDialog'));" data-loading-text="保存中...">保存更新</button>
		</div>
	</div>
	<c:import url="/WEB-INF/pages/common/default/modalDialog.jsp" />
</body>
</html>