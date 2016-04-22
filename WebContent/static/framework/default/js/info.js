/**
 * 列表页匹配框JS
 */
$(function() {
	select2Init();
	setUnitSelect();
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
	$("#page").select2();// 启用Select2插件
	$("#unitId").select2({
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		/* multiple : true, */
		placeholder : "选择单位",
		ajax : {
			url : baseUrl + "/unit/ajaxLoadUnit.do",
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

function afterSaveCallback(id, rowObj) {
	location.reload();
}

function afterUpdateCallback(id, rowObj) {
	location.reload();
	window.top.leftFrame.location.reload();
}

function setUnitSelect() {
	var unitId = $("#operatedUnitId").attr("value");
	var options = $("#operatedUnitId").find("option[value='" + unitId + "']");
	if (undefined !== options && null !== options && 0 < options.length) {
		$("#operatedUnitId").select2("val", unitId);// 有改值就使用改值
	} else {
		var defVal = $("#operatedUnitId").find("option[default]").val();
		$("#operatedUnitId").select2("val", defVal);// 用了select2类型
	}
}