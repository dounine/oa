<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>用户</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script type="text/javascript">
	var frame = ${not empty param.frame ? true : false};
	var srcUnitId = ${not empty param.unitId ? "'".concat(param.unitId).concat("'") : "null"};
</script>
<script src="${base}/static/common/default/js/user/list.js"></script>
<link href="${base}/static/common/default/css/user/list.css" rel="stylesheet" type="text/css" />
<%-- <c:if test="${param.frame eq true}">
	<style type="text/css">
#editDialog.in {
	top: 10% !important;
	left: 20% !important;
	width: 530px !important;
}

#editDialog.fade {
	left: 20% !important;
	width: 530px !important;
}

#searchDialog.in, #alertDialog.in, #successDialog.in, #confirmDialog.in {
	top: 20% !important;
	left: 20% !important;
	width: 530px !important;
}

#searchDialog.fade, #alertDialog.fade, #successDialog.fade, #confirmDialog.fade {
	left: 20% !important;
	width: 530px !important;
}
</style>
</c:if> --%>
</head>
<body style="padding: 10px;">
	<input type="hidden" id="openTreeNode" value="${param.openTreeNode}" />
	<table class="table table-striped table-hover table-bordered" id="mainTable" style="white-space: nowrap;">
		<thead>
			<tr>
				<td class="span1" id="id"><input type="checkbox" class="checkbox checkAll" />序号</td>
				<td class="span1" id="username">用户名称</td>
				<td class="span1" id="name">真实姓名</td>
				<td class="span1" id="mobile">移动电话</td>
				<td class="span1" id="phone">固定电话</td>
				<td class="span1" id="email">电子邮件</td>
				<td class="span1" id="disable">是否禁用</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty resultSet}">
				<c:forEach var="user" items="${resultSet.resultList}" varStatus="status">
					<tr id="row${status.index}" onselectstart="return false;" style="-moz-user-select: none">
						<td class="span1"><label class="checkbox"> <input type="checkbox" class="checkbox" />${status.count}</label> <input type="hidden" name="userId" identity="true" value="${fn:escapeXml(user.id)}" /></td>
						<td class="span1"><a href="javascript:doSaveOrUpdata('update', $('#row${status.index}'));">${fn:escapeXml(user.username)}</a></td>
						<td class="span1">${fn:escapeXml(user.name)}</td>
						<td class="span1">${fn:escapeXml(user.mobile)}</td>
						<td class="span1">${fn:escapeXml(user.phone)}</td>
						<td class="span1">${fn:escapeXml(user.email)}</td>
						<c:choose>
							<c:when test="${fn:escapeXml(user.disable) == 'Y' }">
								<td class="span1">已禁用</td>
							</c:when>
							<c:when test="${fn:escapeXml(user.disable) == 'N' }">
								<td class="span1">未禁用</td>
							</c:when>
							<c:when test="${fn:escapeXml(user.disable) == 'A' }">
								<td class="span1">未审核</td>
							</c:when>
							<c:otherwise>
								<td class="span1">未知状态</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
		<tfoot>
			<!-- 分页 START -->
			<tr>
				<td colspan="9"><yahnc:pagelet url="${base}/user/list.do?${param.frame eq true ? 'frame=true&' : ''}unitId=${param.unitId}" resultSet="${resultSet}" /></td>
			</tr>
			<!-- 分页 END -->
			<tr>
				<td colspan="7"><c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}">
						<button class="btn btn-primary" data-toggle="modal" onclick="doSaveOrUpdata('save',this,0);">增加用户</button>
						<button class="btn btn-danger" data-toggle="modal" onclick="doDelete();">删除用户</button>
					</c:if>
					<button class="btn btn-info" data-toggle="modal" data-target="#searchDialog">搜索</button></td>
			</tr>
		</tfoot>
	</table>
	<!--增加编辑框 START-->
	<div class="modal hide fade" id="editDialog">
		<form action="save.do" method="post" id="saveForm" class="form-horizontal">
			<input type="hidden" id="operType" /> <input type="hidden" id="url" /><input type="hidden" name="id" clear="true" />
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3 id="tableTitle">增加用户信息</h3>
			</div>
			<div class="modal-body">
				<table>
					<tbody>
						<tr>
							<td><label class="sr-only" for="username" style="width: 280px;">用户名&nbsp;&nbsp;&nbsp;</label> <input type="text" class="form-control" name="username" placeholder="用户名" /></td>
							<td><label class="sr-only" for="name">真实姓名</label> <input type="text" class="form-control" name="name" placeholder="真实姓名" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="userpassword">密码</label> <input type="password" class="form-control" name="password" id="userpassword" placeholder="Password">
								</div>
							</td>
							<td><label class="sr-only" for="phone">固话</label> <input type="text" class="form-control" name="phone" id="phone" placeholder="固话" /></td>
						</tr>
						<tr>
							<td><label class="sr-only" for="sex">性别</label> <select class="form-control" name="sex" style="width: 206px;" default="true">
									<option value="M" default selected>男</option>
									<option value="F">女</option>
							</select></td>
							<td><label class="sr-only" for="disable">禁用</label> <select class="form-control" name="disable" style="width: 206px;" default="true">
									<option value="N" default selected>未禁用</option>
									<option value="A">未审核</option>
									<option value="Y">已禁用</option>
							</select></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="email">电子邮件</label> <input type="email" class="form-control" name="email" placeholder="电子邮件">
								</div>
							</td>
							<td><label class="sr-only" for="education">学历</label> <input type="text" class="form-control" name="education" id="education" placeholder="学历" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="jobTitle">职称</label> <input type="text" class="form-control" name="jobTitle" id="jobTitle" placeholder="职称">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="mobile">手机号码</label> <input type="text" class="form-control" name="mobile" id="mobile" placeholder="手机号码">
								</div>
							</td>
						</tr>
						<yahnc:operatedUnitSelectInfo />
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="unitId">单位</label> <input type="hidden" style="width: 500px;" clear="true" select2="true" id="unitId" name="unitId" def-value="${param.unitId}" placeholder="选择单位" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<div class="modal-footer">
			<button type="button" class="btn" data-dismiss="modal">关闭</button>
			<c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}">
				<button id="saveBtn" type="button" class="btn btn-primary" onclick="sendData($('#editDialog'));" data-loading-text="保存中...">保存更新</button>
			</c:if>
		</div>
	</div>
	<!--增加编辑框 END-->
	<!--搜索框 START-->
	<div class="modal hide fade" id="searchDialog">
		<form action="${base}/user/list.do?${param.frame eq true ? 'frame=true&' : ''}unitId=${param.unitId}" method="post" id="searchForm" class="form-horizontal">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3 id="tableTitle">搜索信息</h3>
			</div>
			<div class="modal-body">
				<table>
					<tbody>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="username" style="width: 280px;">用户名称</label> <input type="text" class="form-control" name="username" placeholder="用户名称" value="${fn:escapeXml(username)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="name">真实姓名</label> <input type="text" class="form-control" name="name" placeholder="真实姓名" value="${fn:escapeXml(name)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="mobile">移动电话</label> <input type="text" class="form-control" name="mobile" placeholder="移动电话" value="${fn:escapeXml(mobile)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="phone">固定电话</label> <input type="text" class="form-control" name="phone" placeholder="固定电话" value="${fn:escapeXml(phone)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="email">电子邮件</label> <input type="text" class="form-control" name="email" placeholder="电子邮件" value="${fn:escapeXml(email)}" />
								</div>
							</td>
							<td><div class="form-group">
									<label class="sr-only" for="disable">是否禁用</label> <select class="form-control" name="disable" style="width: 206px;" value="${fn:escapeXml(disable)}" placeholder="是否禁用">
										<option value=""></option>
										<option value="Y" <c:if test="${fn:escapeXml(disable) == 'Y'}">selected="selected"</c:if>>已禁用</option>
										<option value="N" <c:if test="${fn:escapeXml(disable) == 'N'}">selected="selected"</c:if>>未禁用</option>
										<option value="A" <c:if test="${fn:escapeXml(disable) == 'A'}">selected="selected"</c:if>>未审核</option>
									</select>
								</div></td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form action="${base}/user/list.do?${param.frame eq true ? 'frame=true&' : ''}unitId=${param.unitId}" method="post" id="backupForm" style="display: none;">
			<input type="hidden" name="username" value="${fn:escapeXml(username)}" /> <input type="hidden" name="name" value="${fn:escapeXml(name)}" /> <input type="hidden" name="mobile" value="${fn:escapeXml(mobile)}" /> <input type="hidden" name="phone" value="${fn:escapeXml(phone)}" />
			<input type="hidden" name="email" value="${fn:escapeXml(email)}" /> <input type="hidden" name="disable" value="${fn:escapeXml(disable)}" />
		</form>
		<div class="modal-footer">
			<button class="btn btn-primary" id="searchBtn" data-toggle="modal">搜索</button>
			<button class="btn btn-warning" id="clearSearchBtn" data-toggle="modal">清空</button>
			<button type="button" class="btn" data-dismiss="modal">关闭</button>
		</div>
	</div>
	<!--搜索框 END-->
	<c:import url="/WEB-INF/pages/common/default/modalDialog.jsp" />
</body>
</html>