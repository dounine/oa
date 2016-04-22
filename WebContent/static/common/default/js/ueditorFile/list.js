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
	select2Init();
});

function doSaveOrUpdata(oper, caller, type) {
	if ("save" === oper) {
		$("#editDialog #operType").val("save");
		$("#editDialog #tableTitle").text("查看UEditor文件信息");
		$("#editDialog #url").val("/ueditorFile/save.do");
		$('#editDialog').modal('show');
	} else {
		$("#editDialog #operType").val("update");
		$("#editDialog #tableTitle").text("查看UEditor文件信息");
		$("#editDialog #url").val("/ueditorFile/update.do");
		var idObj = $(caller).find("input[type=hidden][name=id]");
		getData("/ueditorFile/find.do", $("#editDialog"), idObj.val(), setAjaxForm);
		$('#editDialog').modal('show');
	}
}

function doDelete() {
	var idObjs = $("#mainTable tbody input[type=checkbox]:checked").parent().next("[type=hidden][name=id]");
	if (0 < idObjs.length) {
		$('#confirmDialog #confirmDialogH4').text('操作确认提示');
		$('#confirmDialog #confirmContext').html('是否确认删除这' + idObjs.length + '条记录');
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
			sendData(ids, deleteCallback, '/ueditorFile/delete.do');
			$('#confirmDialog').modal('hide');
		});
		$('#confirmDialog #cancelBtn').text('取消');
		$('#confirmDialog #cancelBtn').click(function() {
			$('#confirmDialog').modal('hide');
		});
		$("#confirmDialog").modal('show');
	}
}

function select2Init() {
	$("#searchDialog #createUserId").select2({
		placeholder : "选择上传人",
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/user/ajaxLoadUser.do",
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
				url : baseUrl + "/user/searchUserById.do",
				data : {
					ids : element.val()
				},
				dataType : "json",
				success : function(respData, textStatus, jqXHR) {
					var data = respData.result[0];
					callback(data);
				}
			});
		}
	});
}