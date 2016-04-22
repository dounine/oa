<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/yahnfunction" prefix="yahnfn"%>
<%@ taglib uri="/yahncore" prefix="yahnc"%>
<yahnc:constants />
<!DOCTYPE>
<html>
<head>
<title>问卷调查</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:import url="/WEB-INF/pages/common/default/header.jsp" />
<script src="${base}/static/common/default/js/questionnaireInstance/instance.js"></script>
<link href="${base}/static/common/default/css/questionnaireInstance/instance.css" rel="stylesheet" type="text/css" />
</head>
<body ryt13946="1" topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
	<form name="vote" method="post" action="save.do" id="editDialog">
		<input type="hidden" id="operType" /> <input type="hidden" id="url" value="/questionnaireInstance/save.do" /> <input type="hidden" name="id" clear="true" value="${questionnaire.id}" />
		<table cellpadding="0" cellspacing="0" align="center" border="1" width="780">
			<tbody>
				<tr>
					<td class="16title" align="center" height="108"><font size="6"></font>
						<table class="12content" id="table1">
							<h1 align="center">${questionnaire.name}</h1>
							<tr>
								<td>填写时间：<input type="text" name="fillTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="readonly" /></td>
								<td>填写人：<input type="text" name="applicant" /></td>
							</tr>
							<tr>
								<td>联系方式：<input type="text" name="applicantMobile" /></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" align="center" border="0" width="95%">
							<tbody>
								<tr>
									<td><br>
										<table class="12content" cellpadding="0" cellspacing="0" border="0" width="100%" id="mytable">
											<tbody>
												<c:forEach var="relation" items="${questionnaire.tbQuestionnaireQuestionRelations}" varStatus="i">
													<c:if test="${relation.tbQuestionnaireQuestion.type=='S'}">
														<tr>
															<td class="14title" name="question">${i.index+1 }、${fn:escapeXml(relation.tbQuestionnaireQuestion.question)}</td>
														</tr>
														<c:forEach var="option" items="${relation.tbQuestionnaireQuestion.tbQuestionnaireOptions}">
															<tr>
																<td class="14title"><input type="radio" class=" radio" optionId="${option.id}" name="${ relation.tbQuestionnaireQuestion.id}" /> ${fn:escapeXml(option.content)}</td>
															</tr>
														</c:forEach>
													</c:if>
													<c:if test="${relation.tbQuestionnaireQuestion.type=='M'}">
														<tr>
															<td class="14title" name="question">${i.index+1 }、${fn:escapeXml(relation.tbQuestionnaireQuestion.question)}</td>
														</tr>
														<c:forEach var="option" items="${relation.tbQuestionnaireQuestion.tbQuestionnaireOptions}">
															<tr>
																<td class="14title"><input type="checkbox" class=" checkbox" name="${ relation.tbQuestionnaireQuestion.id}" optionId="${option.id}" /> ${fn:escapeXml(option.content)}</td>
															</tr>
														</c:forEach>
													</c:if>
													<c:if test="${relation.tbQuestionnaireQuestion.type=='C'}">
														<tr>
															<td class="14title" name="question">${i.index+1 }、${fn:escapeXml(relation.tbQuestionnaireQuestion.question)}</td>
														</tr>

														<tr>
															<td class="14title" name=" content"><textarea name="${ relation.tbQuestionnaireQuestion.id}" cols="65" rows="3"></textarea></td>
														</tr>

													</c:if>
												</c:forEach>
											</tbody>
										</table></td>
								</tr>
								<tr>
									<td colspan="5" align="center" height="40" valign="bottom"><input type="button" class="submit" onclick="sendData($('#editDialog'));" value="提交" /> <input type="reset" class="reset"></td>
								</tr>
							</tbody>
						</table> <br>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<c:import url="/WEB-INF/pages/common/default/modalDialog.jsp" />
</body>
</html>