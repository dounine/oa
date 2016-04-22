<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>UEditor文件管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/ueditorFile/list.js"></script>
<link href="${base}/static/common/default/css/ueditorFile/list.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 10px;">
	<table class="table table-striped table-hover table-bordered" id="mainTable" style="white-space: nowrap;">
		<caption>
			<h1>UEditor文件列表</h1>
		</caption>
		<thead>
			<tr>
				<td class="span1" id="id"><input type="checkbox" class="checkbox checkAll" />&nbsp;序号</td>
				<td class="span2" id="fileName">文件名称</td>
				<td class="span2" id="originalName">原始文件名称</td>
				<td class="span2" id="fileType">文件类型</td>
				<td class="span1" id="size">文件大小</td>
				<td class="span1" id="createUserId">上传人</td>
				<td class="span2" id="uploadTime">上传时间</td>
				<td class="span1" id="operate">操作</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty resultSet}">
				<c:forEach var="ueditorFile" items="${resultSet.resultList}" varStatus="status">
					<tr id="row${status.index}" onselectstart="return false;" style="-moz-user-select: none">
						<td class="span1"><label class="checkbox"> <input type="checkbox" class="checkbox" />${status.count}</label> <input type="hidden" name="id" identity="true" value="${fn:escapeXml(ueditorFile.id)}" /></td>
						<c:choose>
							<c:when test="${not empty ueditorFile.tbWebFile}">
								<td class="span2"><a href="javascript:doSaveOrUpdata('update', $('#row${status.index}'));">${fn:escapeXml(ueditorFile.fileName)}</a></td>
							</c:when>
							<c:otherwise>
								<td class="span2"><a href="${fn:escapeXml(ueditorFile.url)}">${fn:escapeXml(ueditorFile.originalName)}</a></td>
							</c:otherwise>
						</c:choose>
						<td class="span2">${fn:escapeXml(ueditorFile.originalName)}</td>
						<td class="span2">${fn:escapeXml(ueditorFile.fileType)}</td>
						<td class="span1">${fn:escapeXml(ueditorFile.size)}</td>
						<td class="span1">${fn:escapeXml(nameMap[ueditorFile.createUserId])}</td>
						<td class="span2">${fn:escapeXml(ueditorFile.uploadTime)}</td>
						<td class="span1"><a class="btn btn-primary" href="${base}/ueditorFile/download.do?id=${fn:escapeXml(ueditorFile.id)}&showName=${fn:escapeXml(ueditorFile.originalName)}" target="_blank">下载</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
		<tfoot>
			<!-- 分页 START -->
			<tr>
				<td colspan="8"><yahnc:pagelet url="${base}/ueditorFile/list.do?1=1" resultSet="${resultSet}" /></td>
			</tr>
			<!-- 分页 END -->
			<tr>
				<td colspan="8"><c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}">
						<button class="btn btn-danger" data-toggle="modal" onclick="doDelete();">删除文件</button>
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
				<h3 id="tableTitle">查看文件信息</h3>
			</div>
			<div class="modal-body">
				<table>
					<tbody>
						<tr>
							<td><label class="sr-only" for="fileName" style="width: 280px;">文件名</label> <input type="text" class="form-control" name="fileName" placeholder="文件名" readonly="readonly" /></td>
							<td><label class="sr-only" for="originalName">原始文件名</label> <input type="text" class="form-control" name="originalName" placeholder="原始文件名" readonly="readonly" /></td>
						</tr>
						<tr>
							<td><label class="sr-only" for="fileType">文件类型</label> <input type="text" class="form-control" name="fileType" placeholder="文件类型" readonly="readonly" /></td>
							<td><label class="sr-only" for="createUserId">上传人</label> <input type="text" class="form-control" name="createUserId" placeholder="上传人" readonly="readonly" /></td>
						</tr>
						<tr>
							<td><label class="sr-only" for="size">文件大小</label> <input type="text" class="form-control" name="size" placeholder="文件大小" readonly="readonly" /></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="filePath">文件路径</label>
									<textarea style="width: 500px;" name="filePath" readonly="readonly"></textarea>
								</div>
							</td>
						</tr>
						<yahnc:operatedUnitSelectInfo />
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
		<form action="${base}/ueditorFile/list.do?1=1" method="post" id="searchForm" class="form-horizontal">
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
									<label class="sr-only" for="fileName" style="width: 280px;">文件名称</label> <input type="text" class="form-control" name="fileName" placeholder="文件名称" value="${fn:escapeXml(fileName)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="originalName">原始文件名称</label> <input type="text" class="form-control" name="originalName" placeholder="原始文件名称" value="${fn:escapeXml(originalName)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="fileType">文件类型</label> <input type="text" class="form-control" name="fileType" placeholder="文件类型" value="${fn:escapeXml(fileType)}" />
								</div>
							</td>
							<td>
								<div class="form-group">
									<label class="sr-only" for="createUserId">上传人</label> <input select2="true" name="createUserId" id="createUserId" style="width: 208px" value="${fn:escapeXml(createUserId)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="createDate">上传时间</label>&nbsp;&nbsp;&nbsp;从&nbsp;&nbsp;&nbsp;<input type="text" name="uploadTime" value="${fn:escapeXml(operateValue['uploadTime'][0])}" placeholder="上传时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;<input
										type="text" name="uploadTime" value="${fn:escapeXml(operateValue['uploadTime'][1])}" placeholder="上传时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form action="${base}/ueditorFile/list.do?1=1" method="post" id="backupForm" style="display: none;">
			<input type="hidden" name="fileName" value="${fn:escapeXml(fileName)}" /> <input type="hidden" name="originalFileName" value="${fn:escapeXml(originalName)}" /> <input type="hidden" name="fileType" value="${fn:escapeXml(fileType)}" /> <input type="hidden" name="createUserId"
				value="${fn:escapeXml(createUserId)}" /> <input type="hidden" name="createDate" value="${fn:escapeXml(operateValue['createDate'][0])}" /> <input type="hidden" name="createDate" value="${fn:escapeXml(operateValue['createDate'][1])}" />
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