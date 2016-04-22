<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>个人经历</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/personalExperience/detail.js"></script>
<link href="${base}/static/common/default/css/personalExperience/detail.css" rel="stylesheet" type="text/css" />
</head>
<body style="padding: 10px;">
	<caption>
		<h3>个人经历</h3>
	</caption>
	<hr />
	<table style="text-overflow: ellipsis; overflow: hidden;">
		<tbody>
			<c:if test="${not empty resultSet}">
				<c:forEach var="experince" items="${resultSet.resultList}" varStatus="status">
					<tr onselectstart="return false;" style="-moz-user-select: none">
						<td class="span1"><label> ${status.count}、</label></td>
						<td class="span1" style="width: 500px; word-break: break-all;">${fn:escapeXml(experince.descr)}&nbsp;&nbsp;&nbsp;</td>
						<td class="span1" style="width: 200px; white-space: nowrap;">${fn:escapeXml(experince.startTime)}&nbsp;&nbsp;至&nbsp;&nbsp;${fn:escapeXml(experince.endTime)}</td>
						<td style="white-space: nowrap;"><input class="btn btn-success" type="button" value="编辑" name="update" onclick="doUpdate('${fn:escapeXml(experince.id)}',this)" /></td>
						<td style="white-space: nowrap;"><button class="btn btn-info" data-toggle="modal" onclick="doDelete('${fn:escapeXml(experince.id)}',this)">删除</button></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<hr />
	<input type="button" id="addBtn" value="添加" onclick="javascript:addExperience();" class="btn btn-warning" />
	<!-- <a style="color: red" onmouseover="this.style.color='blue';" onmouseout="this.style.color='red';" id="btn" onclick="btn()">+添加</a>  -->
	<div style="height: 200px; width: 500px; display: none" class="modal-body" id="editDialog">
		<form action="save.do" method="post" id="saveForm" class="form-horizontal">
			<input type="hidden" id="operType" /> <input type="hidden" id="url" /> <input type="hidden" name="id" clear="true" />
			<table>
				<tbody>
					<tr>
						<td colspan="2"><label class="sr-only" for="descr">经历</label> <textarea class="form-control" name="descr" id="descr" placeholder="描述" style="width: 420px;" rows="5"></textarea></td>
					</tr>
					<tr>
						<td><label class="sr-only" for="startTime">起始時間：</label><input type="text" id="startTime" name="startTime" value="" placeholder="记录时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly" /></td>
						<td><label class="sr-only" for="endTime"> 結束時間：</label><input type="text" id="endTime" name="endTime" value="" placeholder="记录时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly" /></td>
					</tr>
				</tbody>
			</table>
		</form>
		<div>
			<button id="saveBtn" type="button" class="btn btn-danger" onclick="sendData($('#editDialog'));" data-loading-text="保存中...">保存</button>
		</div>
	</div>
	<c:import url="/WEB-INF/pages/common/default/modalDialog.jsp" />
</body>
</html>