<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>题目</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/questionnaireQuestion/list.js"></script>
<link href="${base}/static/common/default/css/questionnaireQuestion/list.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 10px;">
	<table class="table table-striped table-hover table-bordered" id="mainTable">
		<caption>
			<h1>题目列表</h1>
		</caption>
		<thead>
			<tr>
				<td class="span1" id="id" style="white-space: nowrap;"><input type="checkbox" class="checkbox checkAll" />&nbsp;序号</td>
				<td class="span1" id="name" style="white-space: nowrap;">名称</td>
				<td class="span1" id="code" style="white-space: nowrap;">编码</td>
				<td class="span4" id="question">问题</td>
				<td class="span1" id="type" style="white-space: nowrap;">类型</td>
			</tr>
		</thead>
		<tbody>
			<!---->
			<c:if test="${not empty resultSet}">
				<c:forEach var="questionnaireQuestion" items="${resultSet.resultList}" varStatus="status">
					<tr id="row${status.index}" onselectstart="return false;" style="-moz-user-select: none">
						<td class="span1" style="white-space: nowrap;"><label class="checkbox"> <input type="checkbox" class="checkbox" />${status.count}</label> <input type="hidden" name="id" identity="true" value="${fn:escapeXml(questionnaireQuestion.id)}" /></td>
						<td class="span1" style="white-space: nowrap;"><a href="javascript:doSaveOrUpdata('update', $('#row${status.index}'));">${fn:escapeXml(questionnaireQuestion.name)}</a></td>
						<td class="span1" style="white-space: nowrap;">${fn:escapeXml(questionnaireQuestion.code)}</td>
						<td class="span4">${fn:escapeXml(questionnaireQuestion.question)}</td>
						<c:choose>
							<c:when test="${fn:escapeXml(questionnaireQuestion.type) == 'S' }">
								<td class="span1" style="white-space: nowrap;">单项</td>
							</c:when>
							<c:when test="${fn:escapeXml(questionnaireQuestion.type) == 'M' }">
								<td class="span1" style="white-space: nowrap;">多项</td>
							</c:when>
							<c:otherwise>
								<td class="span1" style="white-space: nowrap;">问答</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
		<tfoot>
			<!-- 分页 START -->
			<tr>
				<td colspan="5"><yahnc:pagelet url="${base}/questionnaireQuestion/list.do?1=1" resultSet="${resultSet}" /></td>
			</tr>
			<!-- 分页 END -->
			<tr>
				<td colspan="5">
					<%-- <c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}"> --%>
					<button class="btn btn-primary" data-toggle="modal" onclick="doSaveOrUpdata('save',this,0);">增加</button>
					<button class="btn btn-danger" data-toggle="modal" onclick="doDelete();">删除</button> <%-- </c:if> --%>
					<button class="btn btn-info" data-toggle="modal" data-target="#searchDialog">搜索</button>
				</td>
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
				<h3 id="tableTitle">增加题目信息</h3>
			</div>
			<div class="modal-body">
				<table id="table1">
					<!-- 	<input type="hidden" id="operType" /> <input type="hidden" id="url" /> <input type="hidden" name="id" clear="true" />-->
					<tbody>
						<tr>
							<td><label class="sr-only" for="name">题目名称</label> <input type="text" class="form-control" name="name" placeholder="名称" /></td>
							<td><label class="sr-only" for="code">题目编码</label> <input type="text" class="form-control" name="code" id="code" placeholder="编码" /></td>
							<td><label class="sr-only" for="type">题目类型</label> <select class="form-control" name="type" id="type" style="width: 206px;" default="true">
									<option value="S" default selected>单项</option>
									<option value="M">多项</option>
									<option value="C">问答</option>
							</select></td>
						</tr>
						<tr>
							<td colspan="3"><label class="sr-only" for="question">问题</label> <textarea style="width: 700px;" class="form-control" name="question" id="question" placeholder="问题"></textarea></td>
						</tr>
						<tr>
							<td colspan="3"><label class="sr-only" for="descr">描述</label> <textarea style="width: 700px;" class="form-control" name="descr" id="descr" placeholder="描述"></textarea></td>
						</tr>
						<yahnc:operatedUnitSelectInfo />
					</tbody>
				</table>
				<br />
				<button class="btn btn-danger" data-toggle="modal" id="addOptionsBtn" onclick="addRow('optionTable');">题目选项</button>
				<br /> <br />
				<table trLength="0" class="table table-bordered" id="optionTable" parentNode="true" childrenEntityName="optionDTOs">
					<thead>
						<tr>
							<td style="width: 180px;">具体内容</td>
							<td>选项描述</td>
							<td>删除</td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</form>
		<div class="modal-footer">
			<button type="button" class="btn" data-dismiss="modal">关闭</button>
			<%-- <c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}"> --%>
			<button id="saveBtn" type="button" class="btn btn-primary" onclick="sendData($('#editDialog'));" data-loading-text="保存中...">保存更新</button>
			<%-- </c:if> --%>
		</div>
	</div>
	<!--增加编辑框 END-->
	<!--搜索框 START-->
	<!---->
	<div class="modal hide fade" id="searchDialog">
		<form action="${base}/questionnaireQuestion/list.do?1=1" method="post" id="searchForm" class="form-horizontal">
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
									<label class="sr-only" for="name" style="width: 280px;">题目名称</label> <input type="text" class="form-control" name="name" placeholder="题目名称" value="${fn:escapeXml(name)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="code">题目编码</label> <input type="text" class="form-control" name="code" placeholder="编码" value="${fn:escapeXml(code)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="question">题目问题</label> <input type="text" class="form-control" name="question" placeholder="问题" value="${fn:escapeXml(question)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="type">题目类型</label> <select class="form-control" name="type" style="width: 206px;" value="${fn:escapeXml(type)}" placeholder="选择类型">

										<option value=""></option>
										<option value="S" <c:if test="${fn:escapeXml(type) == 'S'}">selected="selected"</c:if>>单项</option>
										<option value="M" <c:if test="${fn:escapeXml(type) == 'M'}">selected="selected"</c:if>>多项</option>
										<option value="C" <c:if test="${fn:escapeXml(type) == 'C'}">selected="selected"</c:if>>问答</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="descr">描述</label>
									<textarea style="width: 500px;" class="form-control" name="descr" placeholder="描述">${fn:escapeXml(descr)}</textarea>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form action="${base}/questionnaireQuestion/list.do?1=1" method="post" id="backupForm" style="display: none;">
			<input type="hidden" name="name" value="${fn:escapeXml(name)}" /> <input type="hidden" name="code" value="${fn:escapeXml(code)}" /> <input type="hidden" name="question" value="${fn:escapeXml(question)}" /> <input type="hidden" name="type" value="${fn:escapeXml(type)}" /> <input
				style="width: 500px;" type="hidden" name="descr" value="${fn:escapeXml(descr)}" />
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