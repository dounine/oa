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
	$("#questionnaireQuestionIds").select2({
		multiple : true,
		placeholder : "选择题目",
		formatSelection : function (state) {
			var id = state.id;
		    if (!state.id) return state.text; // optgroup
		    return "<a class='flag' onclick=showInfo('"+id+"');>" + state.text+"</a>";
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/questionnaire/ajaxLoadQuestionnaireQuestion.do",
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
			$.ajax({
				url : baseUrl + "/questionnaire/searchQuestionnaireQuestionById.do",
				data : {
					ids : $(element).val()
				},
				dataType : "json",
				success : function(respData, textStatus, jqXHR) {
					var data = respData.result;// multiple : true 多个，数组
					//var data = respData.result[0];// multiple : false 一个，非数组
					callback(data);
				}
			});
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
		$("#editDialog #tableTitle").text("增加——问卷信息");
		$('#editDialog #info').hide();
		$("#editDialog #url").val("/questionnaire/save.do");
		$('#editDialog').modal('show');
	} else {
		$("#editDialog #operType").val("update");
		$('#editDialog #info').hide();
		$("#editDialog #tableTitle").text("修改——问卷信息");
		$("#editDialog #url").val("/questionnaire/update.do");
		var idObj = $(caller).find("input[type=hidden][name=id]");
		getData("/questionnaire/find.do", $("#editDialog"), idObj.val(), setAjaxForm);
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
			sendData(ids, deleteCallback, '/questionnaire/delete.do');
			$('#confirmDialog').modal('hide');
		});
		$('#confirmDialog #cancelBtn').text('取消');
		$('#confirmDialog #cancelBtn').click(function() {
			$('#confirmDialog').modal('hide');
		});
		$("#confirmDialog").modal('show');
	}
}

function showInfo(id){
	$.ajax({
		type : "GET",
		url : baseUrl + "/questionnaire/questionnaireQuestionDescr.do?questionId="+id,
		dataType : "json",
		success : function(respData, textStatus, jqXHR) {
			//alert(resData);
			var obj = respData;
			if(respData.result){
			var data = respData.entity;
			$('#editDialog #info').text(data.question);
			$('#editDialog #info').show();}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#editDialog #saveBtn").button('reset');
			$('#alertDialog #alertDialogH4').text('错误提示');
			$('#alertDialog #alertDialogAlertHeading').text('获取返回值失败：');
			$('#alertDialog #alertContext').text(textStatus + " : " + errorThrown);
			$('#alertDialog #alertBtn').text('确认');
			$('#alertDialog #alertBtn').click(function() {
				$('#alertDialog').modal('hide');
				$("#editDialog #saveBtn").button('reset');
			});
			$('#alertDialog').modal('show');
		}
	});
}