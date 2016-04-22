/**
 * 列表页匹配框JS
 */
$(function() {
	select2Init();
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

function select2Init() {
	$("#editDialog select").select2();// 启用Select2插件
	$("#whiteUrls").select2({
		multiple : true,
		placeholder : "选择白名单地址",
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/permission/ajaxLoadUrls.do",
			dataType : 'html',
			quietMillis : 100,
			cache : true,
			data : function(term, page) { // page is the one-based page number tracked by Select2
				return {
					key : term, // search term
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
			var data = [];
			var vals = $(element).val();
			if (null !== vals && undefined !== vals && vals.length > 0) {
				var valArr = vals.split(",");
				for (var i = 0, len = valArr.length; i < len; i++) {
					data.push({
						id : valArr[i],
						text : valArr[i]
					});
				}
			}
			callback(data);
		},
		createSearchChoice : function(term, data) {
			if ($(data).filter(function() {
				return this.text.localeCompare(term) === 0;
			}).length === 0) {
				return {
					id : term,
					text : term
				};
			}
		}
	});
	$("#blackUrls").select2({
		multiple : true,
		placeholder : "选择黑名单地址",
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/permission/ajaxLoadUrls.do",
			dataType : 'html',
			quietMillis : 100,
			cache : true,
			data : function(term, page) { // page is the one-based page number tracked by Select2
				return {
					key : term, // search term
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
			var data = [];
			var vals = $(element).val();
			if (null !== vals && undefined !== vals && vals.length > 0) {
				var valArr = vals.split(",");
				for (var i = 0, len = valArr.length; i < len; i++) {
					data.push({
						id : valArr[i],
						text : valArr[i]
					});
				}
			}
			callback(data);
		},
		createSearchChoice : function(term, data) {
			if ($(data).filter(function() {
				return this.text.localeCompare(term) === 0;
			}).length === 0) {
				return {
					id : term,
					text : term
				};
			}
		}
	});
	$("#searchDialog select[name=disable]").select2({
		placeholder : "是否禁用",
		allowClear : true
	});
}

function doSaveOrUpdata(oper, caller, type) {
	if ("save" === oper) {
		$("#editDialog #operType").val("save");
		$("#editDialog #tableTitle").text("增加——权限信息");
		$("#editDialog #url").val("/permission/save.do");
		$('#editDialog').modal('show');
	} else {
		$("#editDialog #operType").val("update");
		$("#editDialog #tableTitle").text("修改——权限信息");
		$("#editDialog #url").val("/permission/update.do");
		var idObj = $(caller).find("input[type=hidden][name=id]");
		getData("/permission/find.do", $("#editDialog"), idObj.val(), setAjaxForm);
		$('#editDialog').modal('show');
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
			sendData(ids, deleteCallback, '/permission/delete.do');
			$('#confirmDialog').modal('hide');
		});
		$('#confirmDialog #cancelBtn').text('取消');
		$('#confirmDialog #cancelBtn').click(function() {
			$('#confirmDialog').modal('hide');
		});
		$("#confirmDialog").modal('show');
	}
}