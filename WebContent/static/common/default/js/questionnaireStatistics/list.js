$(function() {
	select2Init();
	formValidateInit();
	showData();
});

function select2Init() {
	$("#questionnaireId").select2({
		placeholder : "选择问卷",
		formatSelection : function(data, container, escapeMarkup) {
			return data ? data.text : undefined;
		}, // 选择结果中的显示
		formatResult : function(result, container, query, escapeMarkup) {
			var reg = new RegExp(query.term, "g"); // 创建正则RegExp对象
			var text = result.text.replace(reg, "<span class='select2-match' style='color:red;'>" + query.term + "</span>");
			return text;
		}, // 搜索列表中的显示
		ajax : {
			url : baseUrl + "/questionnaireStatistics/ajaxLoadQuestionnaire.do",
			dataType : 'html',
			quietMillis : 100,
			cache : true,
			data : function(term, page) { // page is the
				// one-based page
				// number
				// tracked by Select2
				return {
					name : term, // search term
					username : term, // search term
					// pageSize : 1, // page size
					// page : page page number
					page : page
				};
			},
			results : function(data, page) { // parse the
				// results into
				// the
				// format expected by Select2.
				var jsonObj = jQuery.parseJSON(data);
				return {
					results : jsonObj.result,
					more : (jsonObj.totalPage > page)
				};
			}
		},
		initSelection : function(element, callback) {
			$.ajax({
				url : baseUrl + "/questionnaireStatistics/searchQuestionnaireById.do",
				data : {
					ids : $(element).val()
				},
				dataType : "json",
				success : function(respData, textStatus, jqXHR) {
					// var data = respData.result;// multiple
					// :
					// true
					// 多个，数组
					var data = respData.result[0];//
					// multiple : false 一个，非数组
					callback(data);
				}
			});
		}
	});
}

function saveData() {
	if ($('#editDialog').valid()) {
		var questionnaireIdVal = $("#questionnaireId").select2("val");
		var questionnaireId = questionnaireIdVal.toString();
		window.location.href = baseUrl + "/questionnaireStatistics/chart.do?questionnaireId=" + questionnaireId;
	}
}

function formValidateInit() {
	// 表单验证
	$("#editDialog").validate({
		rules : {
			questionnaireId : {
				required : true
			}
		},
		messages : {
			questionnaireId : {
				required : " 必须选择"
			}
		},/* 重写错误显示消息方法,以alert方式弹出错误消息 */
		showErrors : function(errorMap, errorList) {
			var msg = "";
			$.each(errorList, function(index, element) {
				msg += ($(element.element).attr("label") + ":" + element.message + "<br/>");
			});
			if (0 < msg.length) {
				$('#alertDialog #alertDialogH4').text('错误提示');
				$('#alertDialog #alertDialogAlertHeading').text('操作失败：');
				$('#alertDialog #alertContext').html(msg);
				$('#alertDialog #alertBtn').text('确认');
				$('#alertDialog #alertBtn').click(function() {
					$("#scoreDialog #saveBtn").button('reset');
					$('#alertDialog').modal('hide');
				});
				$('#alertDialog').modal('show');
			}
		},
		/* 失去焦点时不验证 */
		onfocusout : false
	});
}

function showData() {
	var questionJQObj = $("input[id^=questionChart]");
	for (var i = 0, len = questionJQObj.length; i < len; i++) {
		var colorsArr = [ "#1AFF06", "#06B7E8", "#7507FF", "#E8BB06", "#FF3A00", "#FF9A58", "#E8DF44", "#4BFF95", "#E244E8", "#5893FF", "#FFA700", "#82E804", "#0DFFE2", "#E80C0B", "#4500FF", "#FF0100", "#8E04E8", "#4E88FF", "#E87C0C", "#FFE945", "#FF002F", "#E829D8", "#C04EFF",
				"#E82F0C", "#FF8A45", "#FFCD00", "#8EE829", "#00FF82", "#E8680C", "#FF456F" ];
		var contents = $(questionJQObj[i]).attr("data-contents");
		var contentPercentages = $(questionJQObj[i]).attr("data-contentPercentages");
		var ocscs = $(questionJQObj[i]).attr("data-ocscs");
		var names = $(questionJQObj[i]).attr("data-names");
		var contentArr = contents.split(",");
		var contentPercentageArr = contentPercentages.split(",");
		var ocscArr = ocscs.split(",");
		var nameArr = names.split(",");
		$("#questionChartLabel" + i).html(nameArr[i]);
		var ocscsFloatArr = new Array();
		var newColorArr = new Array();
		for (var j = 0; j < ocscArr.length; j++) {
			var randomNum = Math.floor(Math.random() * colorsArr.length);
			var removeArr = colorsArr.splice(randomNum, 1);
			newColorArr.push(removeArr[0]);
			ocscsFloatArr[j] = parseFloat(ocscArr[j]);
		}
		var barChart = new AwesomeChart("barChart" + i);
		barChart.title = nameArr[i];
		barChart.data = ocscsFloatArr;
		barChart.labels = contentArr;
		barChart.colors = newColorArr;
		// barChart.chartType = "pareto";
		barChart.animate = true;
		barChart.draw();
		var pieChart = new AwesomeChart("pieChart" + i);
		pieChart.chartType = "pie";
		pieChart.title = nameArr[i];
		pieChart.data = ocscsFloatArr;
		pieChart.labels = contentPercentageArr;
		pieChart.colors = newColorArr;
		pieChart.animate = true;
		pieChart.draw();
	}
}