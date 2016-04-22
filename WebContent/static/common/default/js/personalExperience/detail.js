function addExperience() {
	var addBtn = $('#addBtn');
	if (addBtn.val() == "添加") {
		$("#descr").val("");
		$("#startTime").val("");
		$("#endTime").val("");
		$("#editDialog").show();
		$("#editDialog #url").val("/personalExperience/save.do");
		addBtn.val("取消");
	} else {
		$("#editDialog").hide();
		addBtn.val("添加");
		$("[name=update]").val("编辑");
	}
}

function doDelete(id) {
	$('#confirmDialog #confirmDialogH4').text('操作确认提示');
	$('#confirmDialog #confirmContext').text('是否确认删除这条记录');
	$('#confirmDialog #confirmBtn').text('确认');
	$('#confirmDialog #confirmBtn').click(function() {
		window.location.href = baseUrl + "/personalExperience/delete.do?id=" + id;
		$('#confirmDialog').modal('hide');
	});
	$('#confirmDialog #cancelBtn').text('取消');
	$('#confirmDialog #cancelBtn').click(function() {
		$('#confirmDialog').modal('hide');
	});
	$("#confirmDialog").modal('show');
}

function doUpdate(id, btnObj) {
	var update = $(btnObj);
	if (update.val() == "编辑") {
		$("#editDialog").show();
		$("#editDialog #url").val("/personalExperience/update.do");
		getData("/personalExperience/find.do", $("#editDialog"), id, setAjaxForm);
		$("[name=update]").val("编辑");
		update.val("取消");
		$('#addBtn').val("取消");
	} else {
		$("#editDialog").hide();
		update.val("编辑");
		$('#addBtn').val("添加");
	}
}