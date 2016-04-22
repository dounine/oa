/**
 * 列表页匹配框JS
 */
$(function() {
	$("#editDialog select").select2();// 启用Select2插件
	$("#searchDialog select[name=disable]").select2({
		placeholder : "是否禁用",
		allowClear : true
	});
	window.alert = function(msg, callback) {
		$('#successDialog #successDialogH4').text('操作提示');
		$('#successDialog #successDialogAlertHeading').text('提醒');
		$('#successDialog #successContext').text(msg);
		$('#successDialog #successBtn').text('确认');
		$('#successDialog #successBtn').click(function() {
			if (callback) {
				callback();
			}
			$('#successDialog').modal('hide');
		});
		$('#successDialog').modal('show');
	};
});

function doSaveOrUpdata(oper, caller, type) {
	if ("save" === oper) {
		$("#editDialog #operType").val("save");
		$("#editDialog #tableTitle").text("保存信息");
		$("#editDialog #url").val("/questionnaireInstance/save.do");
		$('#editDialog').modal('show');
	}
}

function getJSON(formJQObj) {
	var data = "{";
	data += '"questionnaireId":"' + formJQObj.find("[name=id]").val() + '",';
	data += '"fillTime":"' + formJQObj.find("[name=fillTime]").val() + '",';
	data += '"applicant":"' + formJQObj.find("[name=applicant]").val() + '",';
	data += '"applicantMobile":"' + formJQObj.find("[name=applicantMobile]").val() + '",';
	data += '"answerDTOs":[';
	var fields = formJQObj.find("input[type=radio]:not(:disabled):checked,input[type=checkbox]:not(:disabled):checked,textarea:not(:disabled)");
	fields.each(function(index, element) {
		data += "{";
		data += ("\"questionnaireQuestionId\":\"" + $(element).attr("name") + "\",");
		if ($(element).is("[type=radio]")) {
			data += ("\"questionnaireOptionId\":\"" + $(element).attr("optionId") + "\"");
		} else if ($(element).is("[type=checkbox]")) {
			data += ("\"questionnaireOptionId\":\"" + $(element).attr("optionId") + "\"");
		} else {
			data += ("\"content\":\"" + $(element).val() + "\"");
		}
		data += "}";
		data += (fields.length - 1 > index ? "," : "");
	});
	data += "]";
	data += "}";
	return data;
}

function saveOrUpdateCallback(respData, textStatus, jqXHR, insertData, dialogId) {
	var reallyDialogId = (dialogId ? dialogId : "editDialog");
	$("#" + reallyDialogId + " #saveBtn").button('reset');
	if (respData.result) {
		$('#successDialog #successDialogH4').text('操作提示');
		$('#successDialog #successDialogAlertHeading').text('提交成功：');
		$('#successDialog #successContext').text("信息 : 提交成功,谢谢参与!");
		$('#successDialog #successBtn').text('确认');
		$('#successDialog #successBtn').click(function() {
			$('#successDialog').modal('hide');
			window.close();
		});
		$('#' + reallyDialogId + '').modal('hide');
		$('#successDialog').modal('show');
	} else {
		$('#alertDialog #alertDialogH4').text('错误提示');
		$('#alertDialog #alertDialogAlertHeading').text('提交失败：');
		$('#alertDialog #alertContext').text(respData.msg);
		$('#alertDialog #alertBtn').text('确认');
		$('#alertDialog #alertBtn').click(function() {
			$('#alertDialog').modal('hide');
		});
		$('#alertDialog').modal('show');
	}
}