$(function() {
	unBindDialog();
	select2Init();
	formValidateInit();
});

function select2Init() {
	$("#roleId").select2({
		placeholder : "选择角色",
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/rolePermission/ajaxLoadRole.do",
			dataType : 'html',
			quietMillis : 100,
			cache : true,
			data : function(term, page) { // page is the one-based page number tracked by Select2
				return {
					name : term, // search term
					code : term, // search term
					// pageSize : 1, // page size
					// page : page page number
					page : page
				};
			},
			results : function(data, page) { // parse the results into the format expected by Select2.
				var jsonObj = jQuery.parseJSON(data);
				return {
					results : jsonObj.result,
					more : (jsonObj.totalPage > page)
				};
			}
		},
		initSelection : function(element, callback) {
			$.ajax({
				url : baseUrl + "/rolePermission/searchRoleById.do",
				data : {
					ids : element.val()
				},
				dataType : "json",
				success : function(respData, textStatus, jqXHR) {
					var data = respData.result[0];
					$.ajax({
						url : baseUrl + "/rolePermission/findPermissionsId.do",
						data : {
							roleId : data.id
						},
						dataType : "json",
						success : function(respData, textStatus, jqXHR) {
							$("#permissionIds").select2("val", respData);
							$("#ajaxPermissionVal").val(respData);
							callback(data);
						}
					});
				}
			});
		}
	});
	$("#permissionIds").select2({
		multiple : true,
		placeholder : "选择权限",
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/rolePermission/ajaxLoadPermission.do",
			dataType : 'html',
			quietMillis : 100,
			cache : true,
			data : function(term, page) { // page is the one-based page number tracked by Select2
				return {
					name : term, // search term
					code : term, // search term
					// pageSize : 1, // page size
					// page : page page number
					page : page
				};
			},
			results : function(data, page) { // parse the results into the format expected by Select2.
				var jsonObj = jQuery.parseJSON(data);
				return {
					results : jsonObj.result,
					more : (jsonObj.totalPage > page)
				};
			}
		},
		initSelection : function(element, callback) {
			$.ajax({
				url : baseUrl + "/rolePermission/searchPermissionById.do",
				data : {
					ids : $(element).val()
				},
				dataType : "json",
				success : function(respData, textStatus, jqXHR) {
					var data = respData.result;
					callback(data);
				}
			});
		}
	});
	$("#roleId").on("select2-selecting", function(e) {
		var beforeVal = $("#roleId").select2("val");// 之前的值
		if (null !== e.choice.id) {
			if (beforeVal !== e.choice.id) {
				if ($("#permissionIds").val() !== $("#ajaxPermissionVal").val()) {// 值改变了
					$('#confirmDialog #confirmDialogH4').text('操作确认提示');
					$('#confirmDialog #confirmContext').text('数据已经被修改,是否确认放弃保存');
					$('#confirmDialog #confirmBtn').text('确认');
					$('#confirmDialog #confirmBtn').click(function() {
						$('#confirmDialog').modal('hide');
						$("#roleId").select2("val", [ e.choice.id ]);
					});
					$('#confirmDialog #cancelBtn').text('取消');
					$('#confirmDialog #cancelBtn').click(function() {
						$('#confirmDialog').modal('hide');
					});
					$("#confirmDialog").modal('show');
					$("#roleId").select2("close");
					return false;
				}
			}
		}
		return true;
	}).on("change", function(e) {
		$.ajax({
			url : baseUrl + "/rolePermission/findPermissionsId.do",
			data : {
				roleId : e.val
			},
			dataType : "json",
			success : function(respData, textStatus, jqXHR) {
				$("#permissionIds").select2("val", respData);
				$("#ajaxPermissionVal").val(respData);
			}
		});
	});
}

function saveData() {
	if ($('#saveForm').valid()) {
		var roleIdVal = $("#roleId").select2("val");
		var permissionIdsVal = $("#permissionIds").select2("val");
		$.ajax({
			url : baseUrl + "/rolePermission/save.do",
			data : {
				roleId : roleIdVal.toString(),
				permissionIds : permissionIdsVal.toString()
			},
			dataType : "json",
			success : function(respData, textStatus, jqXHR) {
				saveSuccess(respData, textStatus, jqXHR, permissionIdsVal.toString());
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$('#alertDialog #alertDialogH4').text('错误提示');
				$('#alertDialog #alertDialogAlertHeading').text('获取返回值失败：');
				$('#alertDialog #alertContext').text(textStatus + " : " + errorThrown);
				$('#alertDialog #alertBtn').text('确认');
				$('#alertDialog #alertBtn').click(function() {
					$('#alertDialog').modal('hide');
				});
				$('#alertDialog').modal('show');
			}
		});
	}
}

function saveSuccess(respData, textStatus, jqXHR, permissionIds) {
	if (respData.result) {
		$('#successDialog #successDialogH4').text('操作提示');
		$('#successDialog #successDialogAlertHeading').text('保存成功：');
		$('#successDialog #successContext').text("信息 : 保存成功");
		$('#successDialog #successBtn').text('确认');
		$('#successDialog #successBtn').click(function() {
			$('#successDialog').modal('hide');
			$("#ajaxPermissionVal").val(permissionIds);
		});
		$('#successDialog').modal('show');
	} else {
		$('#alertDialog #alertDialogH4').text('错误提示');
		$('#alertDialog #alertDialogAlertHeading').text('保存失败：');
		$('#alertDialog #alertContext').text(respData.msg);
		$('#alertDialog #alertBtn').text('确认');
		$('#alertDialog #alertBtn').click(function() {
			$('#alertDialog').modal('hide');
		});
		$('#alertDialog').modal('show');
	}
}

function formValidateInit() {
	// 表单验证
	$("#saveForm").validate({
		rules : {
			roleId : {
				required : true
			}
		},
		messages : {
			roleId : {
				required : "用户必须选择"
			}
		}
	});
}

function unBindDialog() {
	$("#alertDialog").on("hidden", function() {
		$("#alertDialog #alertBtn").unbind();// 取消当前绑定事件，防止重复执行
	});
	$("#successDialog").on("hidden", function() {
		$("#successDialog #successBtn").unbind();// 取消当前绑定事件，防止重复执行
	});
	// $("#editDialog").on("hidden", function() {
	// $("#editDialog #saveBtn").button('reset');
	// // clearForm($("#editDialog"));
	// });
	$("#confirmDialog").on("hidden", function() {
		$("#confirmDialog #confirmBtn").unbind();// 取消当前绑定事件，防止重复执行
		$('#confirmDialog #cancelBtn').unbind();// 取消当前绑定事件，防止重复执行
	});
}