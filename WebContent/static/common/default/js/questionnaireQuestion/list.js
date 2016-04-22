/**
 * 列表页匹配框JS
 */
$(function() {
	$("#editDialog select").select2();// 启用Select2插件
	$("#searchDialog select").select2();
	$("#searchDialog select[name=disable]").select2({
		placeholder : "是否禁用",
		allowClear : true
	});
	$("#editDialog #type").on("change", function(e) {
		if ("C" === e.val) {
			$("#optionTable").hide();
			$("#addOptionsBtn").hide();
		} else {
			$("#optionTable").show();
			$("#addOptionsBtn").show();
		}
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

function setAjaxFormCallback(respData, textStatus, jqXHR, id, formJQObj) {
	var typeVal = $("#editDialog #type").select2("val");
	if ("C" === typeVal) {
		$("#optionTable").hide();
		$("#addOptionsBtn").hide();
	} else {
		$("#optionTable").show();
		$("#addOptionsBtn").show();
	}
}

function doSaveOrUpdata(oper, caller, type) {
	if ("save" === oper) {
		$("#optionTable").show();
		$("#addOptionsBtn").show();
		$("#editDialog #operType").val("save");
		$("#editDialog #tableTitle").text("增加——题目信息");
		$("#editDialog #url").val("/questionnaireQuestion/save.do");
		$('#editDialog').modal({
			show : true,
			backdrop : 'static'
		});
	} else {
		$("#editDialog #operType").val("update");
		$("#editDialog #tableTitle").text("修改——题目信息");
		$("#editDialog #url").val("/questionnaireQuestion/update.do");
		var idObj = $(caller).find("input[type=hidden][name=id]");
		getData("/questionnaireQuestion/find.do", $("#editDialog"), idObj.val(), setAjaxForm);
		$('#editDialog').modal('show');
	}
}

function setChildrenEntityVal(respData, formJQObj, parentJQObj, id, childrenEntitys) {
	if (childrenEntitys) {
		for (var i = 0, len = childrenEntitys.length; i < len; i++) {
			var childEntity = childrenEntitys[i];
			var trLength = parseInt($(parentJQObj).attr("trLength"));
			var htmlCode = "";
			htmlCode += "<tr id=\"tr" + trLength + "\">";
			htmlCode += "<td><input type='text' name='content' value='" + childEntity.content + "' placeholder='内容' /></td>";
			htmlCode += "<td><input style='width: 400px;' type='text' value='" + childEntity.descr + "' name='descr' placeholder='描述' /></td>";
			htmlCode += "<td><button class='btn btn-info' data-toggle='modal' onclick=\"javascript:$('#tr" + trLength + "').remove();\">删除</button></td>";
			htmlCode += "</tr>"
			trLength += 1;
			$(parentJQObj).attr("trLength", trLength);
			$(parentJQObj).append(htmlCode);
		}
	}
}

function doDelete() {
	var idObjs = $("#mainTable tbody input[type=checkbox]:checked").parent().next("[type=hidden][name=id]");
	if (0 < idObjs.length) {
		$('#confirmDialog #confirmDialogH4').text('操作确认提示');
		$('#confirmDialog #confirmContext').text('是否确认删除这' + idObjs.length + '条记录');
		$('#confirmDialog #confirmBtn').text('确认');
		$('#confirmDialog #confirmBtn').click(function() {
			var ids = "{\"ids\":[";
			var vals = "";
			idObjs.each(function(index, element) {
				if (idObjs.length - 1 > index) {
					vals += ("\"" + element.value + "\",");
				} else {
					vals += ("\"" + element.value + "\"");
				}
			});
			ids = (ids + vals + "]}");
			ids = jQuery.parseJSON(ids);
			sendData(ids, deleteCallback, '/questionnaireQuestion/delete.do');
			$('#confirmDialog').modal('hide');
		});
		$('#confirmDialog #cancelBtn').text('取消');
		$('#confirmDialog #cancelBtn').click(function() {
			$('#confirmDialog').modal('hide');
		});
		$("#confirmDialog").modal('show');
	}
}

function addRow(tableId) {
	var trLength = parseInt($("#" + tableId).attr("trLength"));
	var htmlCode = "";
	htmlCode += "<tr id=\"tr" + trLength + "\">";
	htmlCode += "<td><input type='text' name='content' placeholder='内容' /></td>";
	htmlCode += "<td><input style='width: 400px;' type='text'  name='descr' placeholder='描述' /></td>";
	htmlCode += "<td><button class='btn btn-info' data-toggle='modal' onclick=\"javascript:$('#tr" + trLength + "').remove();\">删除</button></td>";
	htmlCode += "</tr>"
	trLength += 1;
	$("#" + tableId).attr("trLength", trLength);
	$("#" + tableId).append(htmlCode);
}