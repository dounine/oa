/**
 * 列表页匹配框JS
 */
$(function() {
	// framePosition();
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
	if (srcParentId) {
		$("#editDialog #parentId").select2("val", srcParentId);
	}
});

// function framePosition() {
// $("#editDialog").on("shown", function() {
// $(this).css({
// "top" : function() {
// var top = ($(window.parent).height() - $(this).height()) / 2;
// return (0 < top ? top : 0);
// }
// });
// });
// $("#editDialog").on("hide", function() {
// $(this).css({
// "top" : function() {
// return -($(this).height());
// }
// });
// });
// }

function select2Init() {
	$("#editDialog select").select2();// 启用Select2插件
	// $("#unitIds").select2({
	// multiple : true,
	// placeholder : "选择单位",
	// ajax : {
	// url : baseUrl + "/unit/ajaxLoadUnit.do",
	// dataType : 'html',
	// quietMillis : 100,
	// cache : true,
	// data : function(term, page) { // page is the one-based page number tracked by Select2
	// return {
	// // query : term, // search term
	// // pageSize : 1, // page size
	// // page : page page number
	// page : page
	// };
	// },
	// results : function(data, page) { // parse the results into the format expected by Select2.
	// var jsonObj = jQuery.parseJSON(data);
	// return {
	// results : jsonObj.result,
	// more : (jsonObj.totalPage > page)
	// };
	// }
	// },
	// initSelection : function(element, callback) {
	// $.ajax({
	// url : baseUrl + "/unit/searchUnitById.do",
	// data : {
	// ids : $(element).val()
	// },
	// dataType : "json",
	// success : function(respData, textStatus, jqXHR) {
	// var data = respData.result;
	// callback(data);
	// }
	// });
	// }
	// });
	$("#editDialog #parentId").select2({
		/* multiple : true, */
		allowClear : true,
		placeholder : "选择父部门",
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/unit/ajaxLoadNotSubUnit.do",
			dataType : 'html',
			quietMillis : 100,
			cache : true,
			data : function(term, page) { // page is the one-based page number tracked by Select2
				return {
					name : term, // search term
					id : $("#editDialog input[name=id]").val(),
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
				url : baseUrl + "/unit/searchUnitById.do",
				data : {
					ids : $(element).val()
				},
				dataType : "json",
				success : function(respData, textStatus, jqXHR) {
					// var data = respData.result; multiple : true 多个，数组
					var data = respData.result[0];// multiple : false 一个，非数组
					callback(data);
				}
			});
		}
	});
}

function doSaveOrUpdata(oper, caller, type) {
	if ("save" === oper) {
		$("#editDialog #operType").val("save");
		$("#editDialog #tableTitle").text("增加——部门信息");
		$("#editDialog #url").val("/unit/save.do");
		$('#editDialog').modal('show');
	} else {
		$("#editDialog #operType").val("update");
		$("#editDialog #tableTitle").text("修改——部门信息");
		$("#editDialog #url").val("/unit/update.do");
		var idObj = $(caller).find("input[type=hidden][name=unitId]");
		getData("/unit/find.do", $("#editDialog"), idObj.val(), setAjaxForm);
		$('#editDialog').modal('show');
	}
}

function doDelete() {
	var idObjs = $("#mainTable tbody input[type=checkbox]:checked").parent().next("[type=hidden][name=unitId]");
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
			sendData(ids, deleteCallback, '/unit/delete.do');
			$('#confirmDialog').modal('hide');
		});
		$('#confirmDialog #cancelBtn').text('取消');
		$('#confirmDialog #cancelBtn').click(function() {
			$('#confirmDialog').modal('hide');
		});
		$("#confirmDialog").modal('show');
	}
}

function refresh() {
	var openTreeNodeIdMap = window.parent.frames['leftFrame'].openTreeNodeIdMap;
	var ids = "";
	for (prop in openTreeNodeIdMap) {
		ids += (prop + ",")
	}
	window.parent.frames['leftFrame'].location.href = (baseUrl + "/unit/leftFrame.do?openTreeNode=" + ids);
	location.reload();
}

function afterSaveCallback(id, rowObj) {
	refresh();
}

function afterUpdateCallback(id, rowObj) {
	refresh();
}

function afterDeleteCallback(trJQObj) {
	refresh();
}