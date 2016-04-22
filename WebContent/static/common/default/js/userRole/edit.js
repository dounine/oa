$(function() {
	unBindDialog();
	select2Init();
	formValidateInit();
});

function select2Init() {
	$("#userId").select2({
		placeholder : "选择用户",
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/userRole/ajaxLoadUser.do",
			dataType : 'html',
			quietMillis : 100,
			cache : true,
			data : function(term, page) { // page is the one-based page number tracked by Select2
				return {
					name : term, // search term
					username : term, // search term
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
				url : baseUrl + "/userRole/searchUserById.do",
				data : {
					ids : element.val()
				},
				dataType : "json",
				success : function(respData, textStatus, jqXHR) {
					var data = respData.result[0];
					$.ajax({
						url : baseUrl + "/userRole/findRolesId.do",
						data : {
							userId : data.id
						},
						dataType : "json",
						success : function(respData, textStatus, jqXHR) {
							$("#roleIds").select2("val", respData);
							$("#ajaxRolesVal").val(respData);
							callback(data);
						}
					});
				}
			});
		}
	});
	$("#roleIds").select2({
		multiple : true,
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
			url : baseUrl + "/userRole/ajaxLoadRole.do",
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
				url : baseUrl + "/userRole/searchRoleById.do",
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
	$("#userId").on("select2-selecting", function(e) {
		var beforeVal = $("#userId").select2("val");// 之前的值
		if (null !== e.choice.id) {
			if (beforeVal !== e.choice.id) {
				if ($("#roleIds").val() !== $("#ajaxRolesVal").val()) {// 值改变了
					$('#confirmDialog #confirmDialogH4').text('操作确认提示');
					$('#confirmDialog #confirmContext').text('数据已经被修改,是否确认放弃保存');
					$('#confirmDialog #confirmBtn').text('确认');
					$('#confirmDialog #confirmBtn').click(function() {
						$('#confirmDialog').modal('hide');
						$("#userId").select2("val", [ e.choice.id ]);
					});
					$('#confirmDialog #cancelBtn').text('取消');
					$('#confirmDialog #cancelBtn').click(function() {
						$('#confirmDialog').modal('hide');
					});
					$("#confirmDialog").modal('show');
					$("#userId").select2("close");
					return false;
				}
			}
		}
		return true;
	}).on("change", function(e) {
		$.ajax({
			url : baseUrl + "/userRole/findRolesId.do",
			data : {
				userId : e.val
			},
			dataType : "json",
			success : function(respData, textStatus, jqXHR) {
				$("#roleIds").select2("val", respData);
				$("#ajaxRolesVal").val(respData);
			}
		});
	});
}

function saveData() {
	if ($('#saveForm').valid()) {
		var userIdVal = $("#userId").select2("val");
		var roleIdsVal = $("#roleIds").select2("val");
		$.ajax({
			url : baseUrl + "/userRole/save.do",
			data : {
				userId : userIdVal.toString(),
				roleIds : roleIdsVal.toString()
			},
			dataType : "json",
			success : function(respData, textStatus, jqXHR) {
				saveSuccess(respData, textStatus, jqXHR, roleIdsVal.toString());
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

function saveSuccess(respData, textStatus, jqXHR, roleIds) {
	if (respData.result) {
		$('#successDialog #successDialogH4').text('操作提示');
		$('#successDialog #successDialogAlertHeading').text('保存成功：');
		$('#successDialog #successContext').text("信息 : 保存成功");
		$('#successDialog #successBtn').text('确认');
		$('#successDialog #successBtn').click(function() {
			$('#successDialog').modal('hide');
			$("#ajaxRolesVal").val(roleIds);
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
			userId : {
				required : true
			}
		},
		messages : {
			userId : {
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

// function clearForm(formJQObj) {
// var fields = formJQObj.find("input[type=hidden][clear=true],input:not(:disabled)[type!=hidden],textarea:not(:disabled),select:not(:disabled)");
// fields.each(function(index, element) {
// if ($(element).is('select')) {// 用了select2类型
// $(element).select2("val", null);// 用了select2类型
// } else {
// $(element).val(null);
// }
// });
// }
