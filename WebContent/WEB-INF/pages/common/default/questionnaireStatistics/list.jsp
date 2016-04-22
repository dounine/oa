<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE>
<html>
<head>
<title>问卷统计</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/questionnaireStatistics/list.js"></script>
<script src="${base}/static/plugins/AwesomeChartJS/awesomechart.js" type="text/javascript"></script>
<link href="${base}/static/common/default/css/questionnaireStatistics/list.css" rel="stylesheet" type="text/css" />
<!--[if IE]><script type="text/javascript" src="${base}/static/plugins/excanvas/excanvas.js"></script><![endif]-->
</head>
<body style="font-size: 14px;">
	<form method="post" style="margin: 10px;" id="editDialog">
		<div class="control-group">
			<caption>
				<h3>问卷统计</h3>
			</caption>
		</div>
		<div class="control-group">
			<label class="control-label" for="courseId">选择问卷：</label>
			<div class="controls">
				<input name="questionnaireId" id="questionnaireId" style="width: 300px" value="${questionnaireId}" label="问卷" select2="true" />
			</div>
		</div>
		<button type="button" id="searchBtn" class="btn btn-danger" onclick="saveData('#editDialog')">查询</button>
		<c:if test="${not empty questionnaireName}">
			<c:choose>
				<c:when test="${0 lt fn:length(resultMap)}">
					<br />
					<br />
					<h3>
						<p style="position: absolute; left: 0; text-align: center; width: 100%;">问卷名：${questionnaireName}</p>
					</h3>
					<br />
					<h5>题目总数： ${fn:length(resultMap)}道</h5>
					<c:forEach items="${resultMap}" var="entry" varStatus="status">
						<input type="hidden" id="questionChart${status.index}" key="${entry.key}" ${entry.value} />
					</c:forEach>
					<hr />
					<c:forEach items="${resultMap}" var="entry" varStatus="status">
						<h4>
							题目：<label id="questionChartLabel${status.index}" style="display: inline;"></label>
						</h4>
						<canvas id="barChart${status.index}" width="600" height="400"></canvas>
						<canvas id="pieChart${status.index}" width="450" height="450"></canvas>
						<br />
						<br />
						<br />
						<br />
					</c:forEach>
					<br />
				</c:when>
				<c:otherwise>
					<br />
					<br />
					<h3>
						<p style="position: absolute; left: 0; text-align: center; width: 100%;">暂无人接受调查</p>
					</h3>
				</c:otherwise>
			</c:choose>
		</c:if>
	</form>
	<c:import url="/WEB-INF/pages/common/default/modalDialog.jsp" />
</body>
</html>