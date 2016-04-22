<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>日志管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/logger/list.js"></script>
<link href="${base}/static/common/default/css/logger/list.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 10px;">
	<table class="table table-striped table-hover table-bordered" id="mainTable">
		<caption>
			<h1>日志管理</h1>
		</caption>
		<thead>
			<tr>
				<td id="id" class="span1"><label class="checkbox"><input type="checkbox" class="checkbox checkAll" />序号</label></td>
				<td id="title" class="span1">主题</td>
				<td id="loggerType" class="span1">日志类型</td>
				<td id="userName" class="span1">用户名称</td>
				<td id="ipAddress" class="span1">IP地址</td>
				<td id="url" class="span2">访问地址</td>
				<td id="logTime" class="span2">记录时间</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty resultSet}">
				<c:forEach var="log" items="${resultSet.resultList}" varStatus="status">
					<tr id="row${status.index}" onselectstart="return false;" style="-moz-user-select: none">
						<td class="span1"><label class="checkbox"><input type="checkbox" class="checkbox" />${status.count}</label><input type="hidden" name="loggerId" identity="true" value="${fn:escapeXml(log.id)}" /></td>
						<td class="span1"><a href="javascript:doSaveOrUpdata('update', $('#row${status.index}'));">${fn:escapeXml(log.title)}</a></td>
						<c:choose>
							<c:when test="${fn:escapeXml(log.loggerType) eq 'LOGIN'}">
								<td class="span1">登陆日志</td>
							</c:when>
							<c:otherwise>
								<td class="span1">普通日志</td>
							</c:otherwise>
						</c:choose>
						<td class="span1">${fn:escapeXml(log.userName)}</td>
						<td class="span1">${fn:escapeXml(log.ipAddress)}</td>
						<td class="span2">${fn:escapeXml(log.url)}</td>
						<td class="span2">${fn:escapeXml(log.logTime)}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
		<tfoot>
			<!-- 分页 START -->
			<tr>
				<td colspan="9"><yahnc:pagelet url="${base}/logger/list.do?1=1" resultSet="${resultSet}" /></td>
			</tr>
			<!-- 分页 END -->
			<tr>
				<td colspan="9">
					<button class="btn btn-primary" onclick="location.reload();">刷新</button> <c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}">
						<button class="btn btn-danger" data-toggle="modal" onclick="doDelete();">删除</button>
					</c:if>
					<button class="btn btn-info" data-toggle="modal" data-target="#searchDialog">搜索</button>
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
				<h3 id="tableTitle">日志信息</h3>
			</div>
			<div class="modal-body">
				<table>
					<tbody>
						<tr>
							<td><label class="sr-only" for="title" style="width: 280px;">主题&nbsp;&nbsp;&nbsp;</label> <input type="text" class="form-control" name="title" placeholder="主题" readonly="readonly" /></td>
							<td><label class="sr-only" for="loggerType">日志类型</label> <input type="text" class="form-control" name="loggerType" placeholder="日志类型" readonly="readonly" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="userId">用户ID</label> <input type="text" class="form-control" name="userId" placeholder="用户ID" readonly="readonly" />
								</div>
							</td>
							<td><label class="sr-only" for="userName">用户名称</label> <input type="text" class="form-control" name="userName" placeholder="用户名称" readonly="readonly" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="ipAddress">IP地址</label> <input type="text" class="form-control" name="ipAddress" placeholder="IP地址" readonly="readonly" />
								</div>
							</td>
							<td><label class="sr-only" for="url">访问地址</label> <input type="text" class="form-control" name="url" placeholder="访问地址" readonly="readonly" /></td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="logTime">记录时间</label> <input type="text" class="form-control" name="logTime" placeholder="记录时间" readonly="readonly" />
								</div>
							</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="content">备注</label>
									<textarea style="width: 500px;" name="content" readonly="readonly"></textarea>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<div class="modal-footer">
			<button type="button" class="btn" data-dismiss="modal">关闭</button>
		</div>
	</div>
	<!--增加编辑框 END-->
	<!--搜索框 START-->
	<div class="modal hide fade" id="searchDialog">
		<form action="${base}/logger/list.do?1=1" method="post" id="searchForm" class="form-horizontal">
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
									<label class="sr-only" for="title" style="width: 280px;">主题</label> <input type="text" class="form-control" name="title" placeholder="主题" value="${fn:escapeXml(title)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="loggerType">日志类型</label> <select class="form-control" name="loggerType" style="width: 206px;" value="${fn:escapeXml(loggerType)}" placeholder="日志类型">
										<option value=""></option>
										<option value="LOGIN" <c:if test="${fn:escapeXml(loggerType) == 'LOGIN'}">selected="selected"</c:if>>登录日志</option>
										<option value="NORMAL" <c:if test="${fn:escapeXml(loggerType) == 'NORMAL'}">selected="selected"</c:if>>普通日志</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="userName">用户名称</label> <input type="text" class="form-control" name="userName" placeholder="用户名称" value="${fn:escapeXml(userName)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="ipAddress">IP地址</label> <input type="text" class="form-control" name="ipAddress" placeholder="IP地址" value="${fn:escapeXml(ipAddress)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="url">访问地址</label> <input type="text" class="form-control" name="url" placeholder="访问地址" value="${fn:escapeXml(url)}" />
								</div>
							</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="logTime">记录时间</label>&nbsp;&nbsp;&nbsp;从&nbsp;&nbsp;&nbsp;<input type="text" name="logTime" value="${fn:escapeXml(operateValue['logTime'][0])}" placeholder="记录时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;<input
										type="text" name="logTime" value="${fn:escapeXml(operateValue['logTime'][1])}" placeholder="记录时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form action="${base}/logger/list.do?1=1" method="post" id="backupForm" style="display: none;">
			<input type="hidden" name="title" value="${fn:escapeXml(title)}" /> <input type="hidden" name="loggerType" value="${fn:escapeXml(loggerType)}" /> <input type="hidden" name="userName" value="${fn:escapeXml(userName)}" /> <input type="hidden" name="ipAddress"
				value="${fn:escapeXml(ipAddress)}" /> <input type="hidden" name="url" value="${fn:escapeXml(url)}" /> <input type="hidden" name="logTime" value="${fn:escapeXml(operateValue['logTime'][0])}" /> <input type="hidden" name="logTime"
				value="${fn:escapeXml(operateValue['logTime'][1])}" />
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