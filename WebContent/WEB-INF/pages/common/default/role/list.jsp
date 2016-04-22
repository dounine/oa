<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>角色</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/role/list.js"></script>
<link href="${base}/static/common/default/css/role/list.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 10px;">
	<table class="table table-striped table-hover table-bordered" id="mainTable">
		<caption>
			<h1>角色列表</h1>
		</caption>
		<thead>
			<tr>
				<td class="span1" id="id"><input type="checkbox" class="checkbox checkAll" />&nbsp;序号</td>
				<td class="span1" id="name">角色名称</td>
				<td class="span1" id="code">编码</td>
				<td class="span1" id="descr">描述</td>
				<td class="span1" id="disable">是否禁用</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty resultSet}">
				<c:forEach var="role" items="${resultSet.resultList}" varStatus="status">
					<tr id="row${status.index}" onselectstart="return false;" style="-moz-user-select: none">
						<td class="span1"><label class="checkbox"> <input type="checkbox" class="checkbox" />${status.count}</label> <input type="hidden" name="id" identity="true" value="${fn:escapeXml(role.id)}" /></td>
						<td class="span1"><a href="javascript:doSaveOrUpdata('update', $('#row${status.index}'));">${fn:escapeXml(role.name)}</a></td>
						<td class="span1">${fn:escapeXml(role.code)}</td>
						<td class="span1">${fn:escapeXml(role.descr)}</td>
						<c:choose>
							<c:when test="${fn:escapeXml(role.disable) == 'Y' }">
								<td class="span1">是</td>
							</c:when>
							<c:otherwise>
								<td class="span1">否</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
		<tfoot>
			<!-- 分页 START -->
			<tr>
				<td colspan="5"><yahnc:pagelet url="${base}/role/list.do?1=1" resultSet="${resultSet}" /></td>
			</tr>
			<!-- 分页 END -->
			<tr>
				<td colspan="5"><c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}">
						<button class="btn btn-primary" data-toggle="modal" onclick="doSaveOrUpdata('save',this,0);">增加角色</button>
						<button class="btn btn-danger" data-toggle="modal" onclick="doDelete();">删除角色</button>
					</c:if>
					<button class="btn btn-info" data-toggle="modal" data-target="#searchDialog">搜索</button></td>
				</td>
			</tr>
		</tfoot>
	</table>
	<!--增加编辑框 START-->
	<div class="modal hide fade" id="editDialog">
		<form action="save.do" method="post" id="saveForm" class="form-horizontal">
			<input type="hidden" id="operType" /> <input type="hidden" id="url" /> <input type="hidden" name="id" clear="true" />
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3 id="tableTitle">增加角色信息</h3>
			</div>
			<div class="modal-body">
				<table>
					<tbody>
						<tr>
							<td><label class="sr-only" for="name" style="width: 280px;">角色名称</label> <input type="text" class="form-control" name="name" placeholder="角色名称" /></td>
							<td><label class="sr-only" for="code">编码</label> <input type="text" class="form-control" name="code" id="code" placeholder="编码" /></td>
						</tr>
						<tr>
							<td><label class="sr-only" for="descr">描述</label> <input type="text" class="form-control" name="descr" id="descr" placeholder="描述" /></td>
							<td><label class="sr-only" for="disable">是否禁用</label> <select class="form-control" name="disable" style="width: 206px;" default="true">
									<option value="N" default selected>否</option>
									<option value="Y">是</option>
							</select></td>
						</tr>
						<yahnc:operatedUnitSelectInfo />
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
		<form action="${base}/role/list.do?1=1" method="post" id="searchForm" class="form-horizontal">
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
									<label class="sr-only" for="name" style="width: 280px;">角色名称</label> <input type="text" class="form-control" name="name" placeholder="角色名称" value="${fn:escapeXml(name)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="code">编码</label> <input type="text" class="form-control" name="code" placeholder="编码" value="${fn:escapeXml(code)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="descr">描述</label> <input type="text" class="form-control" name="descr" placeholder="描述" value="${fn:escapeXml(descr)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="disable">是否禁用</label> <select class="form-control" name="disable" style="width: 206px;" value="${fn:escapeXml(disable)}" placeholder="是否禁用">
										<option value=""></option>
										<option value="Y" <c:if test="${fn:escapeXml(disable) == 'Y'}">selected="selected"</c:if>>是</option>
										<option value="N" <c:if test="${fn:escapeXml(disable) == 'N'}">selected="selected"</c:if>>否</option>
									</select>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form action="${base}/role/list.do?1=1" method="post" id="backupForm" style="display: none;">
			<input type="hidden" name="name" value="${fn:escapeXml(name)}" /> <input type="hidden" name="code" value="${fn:escapeXml(code)}" /> <input type="hidden" name="descr" value="${fn:escapeXml(descr)}" /> <input type="hidden" name="disable" value="${fn:escapeXml(disable)}" />
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