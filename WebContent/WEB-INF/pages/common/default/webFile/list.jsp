<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>统一文件管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/webFile/list.js"></script>
<link href="${base}/static/common/default/css/webFile/list.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 10px;">
	<table class="table table-striped table-hover table-bordered" id="mainTable" style="white-space: nowrap;">
		<caption>
			<h1>文件列表</h1>
		</caption>
		<thead>
			<tr>
				<td class="span1" id="id"><input type="checkbox" class="checkbox checkAll" />&nbsp;序号</td>
				<td class="span2" id="fileName">文件名称</td>
				<td class="span2" id="originalFileName">原始文件名称</td>
				<td class="span2" id="fileType">文件类型</td>
				<td class="span1" id="md5">文件MD5</td>
				<td class="span1" id="size">文件大小</td>
				<td class="span2" id="createDate">上传时间</td>
				<td class="span2" id="createUserId">上传人</td>
				<td class="span1" id="operate">操作</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty resultSet}">
				<c:forEach var="webFile" items="${resultSet.resultList}" varStatus="status">
					<tr onselectstart="return false;" style="-moz-user-select: none">
						<td class="span1"><label class="checkbox"> <input type="checkbox" class="checkbox" />${status.count}</label> <input type="hidden" name="id" identity="true" value="${fn:escapeXml(webFile.id)}" /></td>
						<td class="span2"><a href="${base}/webFile/download.do?id=${fn:escapeXml(webFile.id)}&showName=${fn:escapeXml(webFile.originalFileName)}">${fn:escapeXml(webFile.fileName)}</a></td>
						<td class="span2">${fn:escapeXml(webFile.originalFileName)}</td>
						<td class="span2">${fn:escapeXml(webFile.fileType)}</td>
						<td class="span1">${fn:escapeXml(webFile.md5)}</td>
						<td class="span1">${fn:escapeXml(webFile.size)}</td>
						<td class="span2">${fn:escapeXml(webFile.createDate)}</td>
						<td class="span1">${fn:escapeXml(nameMap[webFile.createUserId])}</td>
						<td class="span1"><a class="btn btn-primary" href="${base}/webFile/download.do?id=${fn:escapeXml(webFile.id)}&showName=${fn:escapeXml(webFile.originalFileName)}" target="_blank">下载</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
		<tfoot>
			<!-- 分页 START -->
			<tr>
				<td colspan="9"><yahnc:pagelet url="${base}/webFile/list.do?1=1" resultSet="${resultSet}" /></td>
			</tr>
			<!-- 分页 END -->
			<tr>
				<td colspan="9"><c:if test="${yahnfn:isRoles(sessionScope.login_user,fn:split(ADMIN_ROLE_CODE,','))}">
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
							<td><label class="sr-only" for="originalFileName">原始文件名</label> <input type="text" class="form-control" name="originalFileName" placeholder="原始文件名" readonly="readonly" /></td>
						</tr>
						<tr>
							<td><label class="sr-only" for="fileType">文件类型</label> <input type="text" class="form-control" name="fileType" placeholder="文件类型" readonly="readonly" /></td>
							<td><label class="sr-only" for="md5">MD5</label> <input type="text" class="form-control" name="md5" placeholder="MD5" readonly="readonly" /></td>
						</tr>
						<tr>
							<td><label class="sr-only" for="size">文件大小</label> <input type="text" class="form-control" name="size" placeholder="文件大小" readonly="readonly" /></td>
							<td><label class="sr-only" for="uploadUrl">上传URL地址</label> <input type="text" class="form-control" name="uploadUrl" placeholder="上传URL地址" readonly="readonly" /></td>
						</tr>
						<tr>
							<td><label class="sr-only" for="ipAddress">上传IP地址</label> <input type="text" class="form-control" name="ipAddress" placeholder="上传IP地址" readonly="readonly" /></td>
							<td><label class="sr-only" for="createUserId">上传人</label> <input type="text" class="form-control" name="createUserId" placeholder="上传人" readonly="readonly" /></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="filePath">文件路径</label>
									<textarea style="width: 500px;" name="filePath" readonly="readonly"></textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="dummyFiles">关联虚拟文件</label>
									<textarea style="width: 500px;" name="dummyFiles" readonly="readonly" rows="5"></textarea>
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
		<form action="${base}/webFile/list.do?1=1" method="post" id="searchForm" class="form-horizontal">
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
									<label class="sr-only" for="originalFileName">原始文件名称</label> <input type="text" class="form-control" name="originalFileName" placeholder="原始文件名称" value="${fn:escapeXml(originalFileName)}" />
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
									<label class="sr-only" for="md5">文件MD5</label> <input type="text" class="form-control" name="md5" placeholder="文件MD5" value="${fn:escapeXml(md5)}" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label class="sr-only" for="createUserId">上传人</label> <input select2="true" name="createUserId" id="createUserId" style="width: 208px" value="${fn:escapeXml(createUserId)}" />
								</div>
							</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="sr-only" for="createDate">上传时间</label>&nbsp;&nbsp;&nbsp;从&nbsp;&nbsp;&nbsp;<input type="text" name="createDate" value="${fn:escapeXml(operateValue['createDate'][0])}" placeholder="上传时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;<input
										type="text" name="createDate" value="${fn:escapeXml(operateValue['createDate'][1])}" placeholder="上传时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form action="${base}/webFile/list.do?1=1" method="post" id="backupForm" style="display: none;">
			<input type="hidden" name="fileName" value="${fn:escapeXml(fileName)}" /> <input type="hidden" name="originalFileName" value="${fn:escapeXml(originalFileName)}" /> <input type="hidden" name="fileType" value="${fn:escapeXml(fileType)}" /> <input type="hidden" name="md5"
				value="${fn:escapeXml(md5)}" /> <input type="hidden" name="createDate" value="${fn:escapeXml(operateValue['createDate'][0])}" /> <input type="hidden" name="createDate" value="${fn:escapeXml(operateValue['createDate'][1])}" />
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