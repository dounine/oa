/**
 * 列表页匹配框JS
 */
$(function() {
	$("select").select2();// 启用Select2插件
	$("#searchDialog select[name=loggerType]").select2({
		placeholder : "日志类型",
		allowClear : true
	});
});

function doDelete() {
	var idObjs = $("#mainTable tbody input[type=checkbox]:checked:not(:hidden)").parent().next("[type=hidden][name=loggerId]");
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
			sendData(ids, deleteCallback, '/logger/delete.do');
			$('#confirmDialog').modal('hide');
		});
		$('#confirmDialog #cancelBtn').text('取消');
		$('#confirmDialog #cancelBtn').click(function() {
			$('#confirmDialog').modal('hide');
		});
		$("#confirmDialog").modal('show');
	}
}

function doSaveOrUpdata(oper, caller, type) {
	if ("save" === oper) {
		$("#editDialog #operType").val("save");
		$("#editDialog #tableTitle").text("日志信息");
		$("#editDialog #url").val("/logger/save.do");
		$('#editDialog').modal('show');
	} else {
		$("#editDialog #operType").val("update");
		$("#editDialog #tableTitle").text("日志信息");
		$("#editDialog #url").val("/logger/update.do");
		var idObj = $(caller).find("input[type=hidden][name=loggerId]");
		getData("/logger/find.do", $("#editDialog"), idObj.val(), setAjaxForm);
		$('#editDialog').modal('show');
	}
}