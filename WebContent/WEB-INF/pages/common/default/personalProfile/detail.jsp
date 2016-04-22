<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>个人资料</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/plugins/uploadify/jquery.uploadify.js"></script>
<script src="${base}/static/common/default/js/personalProfile/detail.js"></script>
<link href="${base}/static/common/default/css/personalProfile/detail.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<form class="form-horizontal" id="editDialog">
		<input type="hidden" id="operType" value="update" /> <input type="hidden" id="url" value="/personalProfile/submit.do" /> <input type="hidden" name="id" value="${currentUserPersonalProfile.id}" />
		<legend>
			<h2>个人资料</h2>
		</legend>
		<div class="control-group">
			<div class="controls">
				<img src="${(not empty currentUserPersonalProfile.photo) ? base.concat(currentUserPersonalProfile.photo) :base.concat('/static/common/default/img/pic-none.png')}" class="img-polaroid" width="180px" height="180px" style="width: 180px; height: 180px;" id="photoImg">
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<input type="file" name="photoUpload" id="photoUpload" /> <input type="hidden" name="photo" value="${currentUserPersonalProfile.photo}" escape="false" clear="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="realName">真实姓名</label>
			<div class="controls">
				<input type="text" name="realName" placeholder="输入真实姓名" value="${currentUserPersonalProfile.realName}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="englishName">英文名</label>
			<div class="controls">
				<input type="text" name="englishName" placeholder="输入英文名" value="${currentUserPersonalProfile.englishName}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="birthday">出生日期</label>
			<div class="controls">
				<input type="text" name="birthday" placeholder="选择出生日期" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',startDate:'1980-01-01'});" value="${currentUserPersonalProfile.birthday}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="bloodType">血型</label>
			<div class="controls">
				<select class="form-control" name="bloodType" style="width: 206px;" placeholder="选择血型" value="${currentUserPersonalProfile.bloodType}">
					<option value=""></option>
					<option value="A" <c:if test="${fn:escapeXml(currentUserPersonalProfile.bloodType) == 'A'}">selected="selected"</c:if>>A型</option>
					<option value="B" <c:if test="${fn:escapeXml(currentUserPersonalProfile.bloodType) == 'B'}">selected="selected"</c:if>>B型</option>
					<option value="AB" <c:if test="${fn:escapeXml(currentUserPersonalProfile.bloodType) == 'AB'}">selected="selected"</c:if>>AB型</option>
					<option value="O" <c:if test="${fn:escapeXml(currentUserPersonalProfile.bloodType) == 'O'}">selected="selected"</c:if>>O型</option>
					<option value="OTH" <c:if test="${fn:escapeXml(currentUserPersonalProfile.bloodType) == 'OTH'}">selected="selected"</c:if>>其他</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="qq">QQ</label>
			<div class="controls">
				<input type="text" name="qq" placeholder="输入QQ" value="${currentUserPersonalProfile.qq}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="wechat">微信</label>
			<div class="controls">
				<input type="text" name="wechat" placeholder="输入微信" value="${currentUserPersonalProfile.wechat}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="residence">现居住地</label>
			<div class="controls">
				<input type="text" name="residence" placeholder="输入现居住地" style="width: 400px;" value="${currentUserPersonalProfile.residence}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="hometown">故乡</label>
			<div class="controls">
				<input type="text" name="hometown" placeholder="输入故乡" style="width: 400px;" value="${currentUserPersonalProfile.hometown}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="detailedAddress">详细住址</label>
			<div class="controls">
				<input type="text" name="detailedAddress" placeholder="输入详细住址" style="width: 400px;" value="${currentUserPersonalProfile.detailedAddress}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="individualResume">个人简介</label>
			<div class="controls">
				<textarea name="individualResume" placeholder="输入个人简介" cols="10" rows="5" style="width: 400px;">${currentUserPersonalProfile.individualResume}</textarea>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<button type="button" id="saveBtn" class="btn btn-primary" style="width: 200px;" onclick="javascript:sendData($('#editDialog'));">保存资料</button>
				<button type="button" id="clearBtn" class="btn btn-danger" style="width: 200px;" onclick="javascript:personalProfileClearForm($('#editDialog'));">清除资料</button>
			</div>
		</div>
	</form>
	<c:import url="/WEB-INF/pages/common/default/modalDialog.jsp" />
</body>
</html>