<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>部门</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script type="text/javascript">
	var frame = ${not empty param.frame ? true : false};
	var srcParentId = ${not empty param.parentId ? "'".concat(param.parentId).concat("'") : "null"};
</script>
<script src="${base}/static/common/default/js/unit/list.js"></script>
<link href="${base}/static/common/default/css/unit/list.css" rel="stylesheet" type="text/css" />
<%-- <c:if test="${param.frame eq true}">
	<style type="text/css">
.modal.fade.in {
	top: 18% !important;
	left: 20% !important;
	width: 530px !important;
}

.modal.fade {
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
				<td class="span3" id="name">部门名称</td>
				<td class="span1" id="code">部门编码</td>
				<td class="span4" id="address">地址</td>
				<td class="span1" id="phone">固定电话</td>
				<td class="span1" id="postcode">邮政编码</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty resultSet}">
				<c:forEach var="entity" items="${resultSet.resultList}" varStatus="status">
					<tr id="row${status.index}" onselectstart="return false;" style="-moz-user-select: none">
						<td class="span1"><label class="checkbox"> <input type="checkbox" class="checkbox" />${status.count}</label> <input type="hidden" name="unitId" identity="true" value="${fn:escapeXml(entity.id)}" /></td>
						<td class="span3"><a href="javascript:doSaveOrUpdata('update', $('#row${status.index}'));">${fn:escapeXml(entity.name)}</a></td>
						<td class="span1">${fn:escapeXml(entity.code)}</td>
						<td class="span4">${fn:escapeXml(entity.address)}</td>
						<td class="span1">${fn:escapeXml(entity.phone)}</td>
						<td class="span1">${fn:escapeXml(entity.postcode)}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
		<tfoot>
			<!-- 分页 START -->
			<tr>
				<td colspan="9"><yahnc:pagelet url="${base}/unit/list.do?${param.frame eq true ? 'frame=true&' : ''}parentId=${param.parentId}" resultSet="${resultSet}" /></td>
			</tr>
			<!-- 分页 END -->
			<tr>
				<td colspan="7"><c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}">
						<button class="btn btn-primary" data-toggle="modal" onclick="doSaveOrUpdata('save',this,0);">增加单位</button>
						<button class="btn btn-danger" data-toggle="modal" onclick="doDelete();">删除单位</button>
					</c:if>
					<button class="btn btn-info" data-toggle="modal" data-target="#searchDialog">搜索</button></td>
			</tr>
		</tfoot>
	</table>
	<!--增加编辑框 START-->
	<div class="modal hide fade" id="editDialog">
		<form action="save.do" method="post" id="saveForm" class="form-horizontal">
			<input type="hidden" id="operType" /> <input type="hidden" id="url" />
			<%-- <input type="hidden" name="parentId" value="${param.parentId}" /> --%>
			<input type="hidden" name="id" clear="true" />
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3 id="tableTitle">增加单位信息</h3>
			</div>
			<div class="modal-body">
				<table>
					<tbody>
						<tr>
							<td><label class="sr-only" for="name" style="width: 280px;">部门名称&nbsp;&nbsp;&nbsp;</label> <input type="text" class="form-control" name="name" placeholder="单位名称" /></td>
							<td><label class="sr-only" for="code">部门编码</label> <input type="text" class="form-control" name="code" placeholder="单位编码" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="address">地址</label> <input type="text" class="form-control" name="address" id="address" placeholder="地址">
								</div>
							</td>
							<td><label class="sr-only" for="phone">固话</label> <input type="text" class="form-control" name="phone" id="phone" placeholder="固话" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="postcode">邮政编码</label> <input type="text" class="form-control" name="postcode" placeholder="邮政编码">
								</div>
							</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="parentId">父部门</label> <input type="hidden" style="width: 500px;" clear="true" select2="true" id="parentId" name="parentId" def-value="${param.parentId}" placeholder="选择父部门" />
								</div>
							</td>
						</tr>
						<yahnc:operatedUnitSelectInfo />
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="descr">描述</label>
									<textarea style="width: 500px;" id="descr" name="descr"></textarea>
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
		<form action="${base}/unit/list.do?${param.frame eq true ? 'frame=true&' : ''}&parentId=${param.parentId}" method="post" id="searchForm" class="form-horizontal">
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
									<label class="sr-only" for="name" style="width: 280px;">部门名称</label> <input type="text" class="form-control" name="name" placeholder="部门名称" value="${fn:escapeXml(name)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="code">部门编码</label> <input type="text" class="form-control" name="code" placeholder="部门编码" value="${fn:escapeXml(code)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="address">地址</label> <input type="text" class="form-control" name="address" placeholder="地址" value="${fn:escapeXml(address)}" />
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
									<label class="sr-only" for="postcode">邮政编码</label> <input type="text" class="form-control" name="postcode" placeholder="邮政编码" value="${fn:escapeXml(postcode)}" />
								</div>
							</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form action="${base}/unit/list.do?${param.frame eq true ? 'frame=true&' : ''}parentId=${param.parentId}" method="post" id="backupForm" style="display: none;">
			<input type="hidden" name="name" value="${fn:escapeXml(name)}" /> <input type="hidden" name="code" value="${fn:escapeXml(code)}" /> <input type="hidden" name="address" value="${fn:escapeXml(address)}" /> <input type="hidden" name="phone" value="${fn:escapeXml(phone)}" /> <input
				type="hidden" name="postcode" value="${fn:escapeXml(postcode)}" />
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