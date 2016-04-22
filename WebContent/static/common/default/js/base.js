/* 框架级别的JS可以写在这里 */
$(window).bind("load", function() {
	mainSearchInit();
	mainCheckInit();
	mainLoadingInit();
	mainPageChangeInit();
	mainUnBindDialog();
	mainResetDialog();
	mainTableDoubleClickInit();
});

function mainLoadingInit() {
	$('button[data-loading-text]').click(function() {
		var btn = $(this).button('loading');
		// setTimeout(function() {
		// btn.button('reset');
		// }, 3000);
		// 实际上需要自行reset
	});
}

function mainCheckInit() {
	var checkAllBox = $("#mainTable input[type=checkbox].checkAll");
	checkAllBox.click(function() {
		var checkBox = $("#mainTable input[type=checkbox].checkbox:not(.checkAll)");
		if ($(this).is(":checked")) {
			checkBox.each(function() {
				this.checked = true;
			});
			// checkBox.attr("checked", true);
		} else {
			checkBox.each(function() {
				this.checked = false;
			});
			// checkBox.attr("checked", false);
		}
		var that = this;
		checkBox.on("click", function() {
			if ($(that).is(":checked") && !$(this).is(":checked")) {
				// $(that).attr("checked", false);
				that.checked = false;
			}
		});
	});
}

function mainSearchInit() {
	var searchBtn = $("#searchDialog #searchBtn");
	var clearSearchBtn = $("#searchDialog #clearSearchBtn");
	if (0 < searchBtn.length) {
		searchBtn.click(function() {
			$("#searchDialog #searchForm").submit();
		});
	}
	if (0 < clearSearchBtn.length) {
		clearSearchBtn.click(function() {
			mainClearForm($("#searchDialog"));
		});
	}
}

function mainPageChangeInit() {
	if (typeof pageChangeInit !== "function") {
		var pageObj = $("#page");
		if (0 < pageObj.length) {
			pageObj.on("change", function(e) {
				var pageStr = pageObj.attr("url") + "&pageNumber=" + e.val;
				var searchBtn = $("#searchDialog #searchBtn");
				if (0 < searchBtn.length) {
					pageStr = $("#searchDialog #backupForm").attr("action");
					pageStr += ("&pageNumber=" + e.val);
				}
				var backupForm = $("#searchDialog #backupForm");
				backupForm.attr("action", pageStr);
				backupForm.submit();
			});
		}
	} else {
		pageChangeInit();
	}
	$("#page").select2();// 启用Select2插件
}

function mainUnBindDialog() {
	if (typeof unBindDialog !== "function") {
		unBindAlertDialog();
		unBindSuccessDialog();
		unBindConfirmDialog();
	} else {
		unBindDialog();
	}
}

function mainResetDialog() {
	resetEditDialog();
}

function unBindAlertDialog() {
	$("#alertDialog").on("hidden", function() {
		$("#alertDialog #alertBtn").unbind();// 取消当前绑定事件，防止重复执行
	});
}

function unBindSuccessDialog() {
	$("#successDialog").on("hidden", function() {
		$("#successDialog #successBtn").unbind();// 取消当前绑定事件，防止重复执行
	});
}

function resetEditDialog() {
	$("#editDialog").on("hidden", function() {
		$("#editDialog #saveBtn").show();
		$("#editDialog #saveBtn").button('reset');
		$("#editDialog *[keepReadOnly!=true]").removeAttr('readOnly');
		$("#editDialog select[keepReadOnly!=true]").removeAttr('disabled');
		if (typeof clearForm === "function") {
			clearForm($("#editDialog"));
		} else {
			mainClearForm($("#editDialog"));
		}
	});
}

function unBindConfirmDialog() {
	$("#confirmDialog").on("hidden", function() {
		$("#confirmDialog #confirmBtn").unbind();// 取消当前绑定事件，防止重复执行
	});
}

function mainTableDoubleClickInit() {
	if (typeof tableDoubleClickInit !== "function") {
		$('#mainTable tbody tr').on("dblclick", function() {
			if (typeof doSaveOrUpdata === "function") {
				doSaveOrUpdata("update", this);
			}
		});
	}
}

function mainClearForm(formJQObj) {
	formJQObj.find("[parentNode=true] *").attr("children", true);
	var fields = formJQObj.find("input[type=hidden][clear=true][children!=true],input:not(:disabled)[type!=hidden][children!=true],textarea:not(:disabled)[children!=true],select:not(:disabled)[children!=true],[parentNode=true]");
	fields.each(function(index, element) {
		if ($(element).is('select')) {// 用了select2类型
			if ($(element).is('[default=true]')) {// 非动态的默认值处理
				var defVal = $(element).find("option[default]").val();
				$(element).select2("val", defVal);// 用了select2类型
			} else {
				$(element).select2("val", null);// 用了select2类型
			}
		} else if ($(element).is('[select2=true]')) {// 特殊的select2类型
			if ($(element).is('[def-value]')) {// 动态的默认值处理
				if ($(element).is('[jsonArray=true]')) {
					$(element).select2("val", $(element).attr("def-value").split(","));// 特殊
				} else {
					$(element).select2("val", $(element).attr("def-value"));// 普通
				}
			} else {
				$(element).select2("val", null);// 用了select2类型
			}
		} else if ($(element).is('[ueditor=true]')) {
			window.ueEditorVal = "";
		} else if ($(element).is('[parentNode=true]')) {// 删除子元素
			if ($(element).is('table')) {
				var tbodyJQObj = $(element).find('tbody');
				if (0 < tbodyJQObj.length) {
					tbodyJQObj.empty();
				} else {
					$(element).empty();// 没有tbody的table
				}
			} else {
				$(element).empty();// div那些
			}
		} else {
			$(element).val(null);
		}
	});
}

function setAjaxForm(respData, textStatus, jqXHR, id, formJQObj) {
	if (respData.result) {
		var entity = respData.entity;
		formJQObj.find("[parentNode=true] *").attr("children", true);
		var fields = formJQObj.find("input:not(:disabled)[children!=true],textarea:not(:disabled)[children!=true],select:not(:disabled)[children!=true],[parentNode=true]");
		var readOnly = respData.readOnly;
		if (readOnly) {
			var readOnlyObj = $(formJQObj).find("#readOnly");
			readOnlyObj.val("true");
			readOnlyObj.attr("clear", "true");
		}
		fields.each(function(index, element) {
			var eleName = $(element).attr("name") || $(element).attr("childrenEntityName");// childrenEntityName 父节点段时候
			if (undefined !== entity[eleName] && null !== entity[eleName]) {
				if (!$(element).is('[set=false]')) {
					var val = entity[eleName];
					if ($(element).is('[base64=true]')) {
						val = Base64.decode(val);
					}
					if (readOnly) {
						$(element).attr("readOnly", "readOnly");
					}
					if ($(element).is('select')) {// 用了select2类型
						if (readOnly) {
							$(element).attr("disabled", "disabled");
						}
						if ($(element).is('[notSelectDefault=true]')) {// 有使用'没有选的用默认值代替'功能
							if ($(element).is('[multiple]')) {// 多选的情况下
								var valArr = val;
								var newValArr = new Array();
								for (var i = 0, len = valArr.length; i < len; i++) {
									var options = $(element).find("option[value='" + valArr[i] + "']");
									if (undefined !== options && null !== options && 0 < options.length) {
										newValArr.push(valArr[i]);
									}
								}
								if (0 == newValArr.length) {
									var defVals = $(element).find("option[default]");
									for (var i = 0, len = defVals.length; i < len; i++) {
										newValArr.push(defVals[i]);
									}
								}
								$(element).select2("val", newValArr);// 设置值
							} else {// 单选
								var options = $(element).find("option[value='" + val + "']");
								if (undefined !== options && null !== options && 0 < options.length) {
									$(element).select2("val", val);// 有改值就使用改值
								} else {
									var defVal = $(element).find("option[default]").val();
									$(element).select2("val", defVal);// 用了select2类型
								}
							}
						} else {
							$(element).select2("val", val);// 用了select2类型
						}
					} else if ($(element).is('[select2=true]')) {// 特殊的select2类型
						$(element).select2("val", [ val ]);// 用了select2类型
					} else if ($(element).is('[ueditor=true]')) {
						window.ueEditorVal = val;
					} else if ($(element).is('[parentNode=true]')) {
						var childrenEntitys = val;
						setChildrenEntityVal(respData, formJQObj, $(element), id, childrenEntitys);// 设置子节点的值需要重写这个方法
					} else {
						$(element).val(val);
					}
				}
			}
		});
		setAjaxFormCallback(respData, textStatus, jqXHR, id, formJQObj);
	} else {
		$('#alertDialog #alertDialogH4').text('错误提示');
		$('#alertDialog #alertDialogAlertHeading').text('获取失败：');
		$('#alertDialog #alertContext').text(respData.msg);
		$('#alertDialog #alertBtn').text('确认');
		$('#alertDialog #alertBtn').click(function() {
			$('#alertDialog').modal('hide');
		});
		$('#alertDialog').modal('show');
	}
}

function setChildrenEntityVal(respData, formJQObj, parentJQObj, id, childrenEntitys) {
	alert("请重写setChildrenEntityVal方法,实现相关子借点设置值!");
}

function escapeCharacter(val) {
	if (val) {
		val = val.replace(/\\/g, "\\\\");
		val = val.replace(/\"/g, "\\\"");
		val = val.replace(/\?/g, "？");
		val = val.replace(/(\r\n|\n|\r)/g, "");
	}
	return val;
}

function getJSON(formJQObj) {
	var data = "{";
	formJQObj.find("[parentNode=true] *").attr("children", true);
	var fields = formJQObj.find("input:not(:disabled)[select2!=true][children!=true],input:not(:disabled)[select2=true][jsonArray!=true][children!=true],textarea:not(:disabled)[children!=true],[parentNode=true]");
	fields.each(function(index, element) {
		var eleName = $(element).attr("name") || $(element).attr("childrenEntityName");// childrenEntityName 父节点段时候
		if (eleName) {
			var val = $(element).val();
			if ($(element).is("[base64=true]")) {
				val = Base64.encode(val);
			} else if ($(element).is("[parentNode=true]")) {
				var childrenObjs = null;
				if ($(element).is('table')) {
					var tbodyJQObj = $(element).find('tbody');
					if (0 < tbodyJQObj.length) {
						childrenObjs = $(element).find("tbody tr");
					} else {
						childrenObjs = $(element).find("tr");
					}
				} else {
					childrenObjs = $(element).children();
				}
				var childData = "[";
				for (var i = 0, len = childrenObjs.length; i < len; i++) {
					var childObj = childrenObjs[i];
					var childField = $(childObj).find("input:not(:disabled)[select2!=true],input:not(:disabled)[select2=true][jsonArray!=true],textarea:not(:disabled)");
					childData += "{";
					childField.each(function(childIndex, childElement) {
						var childEleName = $(childElement).attr("name");
						if (childEleName) {
							var childVal = $(childElement).val();
							if ($(childElement).is("[base64=true]")) {
								childVal = Base64.encode(childVal);
							} else {
								if ($(childElement).is("[escape!=false]")) {
									childVal = escapeCharacter(childVal);
								}
							}
							childData += ('"' + $(childElement).attr("name") + '":"' + childVal + (childField.length - 1 > childIndex ? '",' : '"'));
						}
					});
					if (childData.lastIndexOf(",") === childData.length - 1) {
						childData = (childData.substr(0, childData.length - 1));
					}
					var childSelectFields = $(childObj).find("select:not(:disabled),:not(object)[select2=true][jsonArray=true]");
					childSelectFields.each(function(childIndex, childElement) {
						var childVal = $(childElement).val();
						if ($(childElement).is("[base64=true]")) {
							childVal = Base64.encode(childVal);
						} else {
							if ($(childElement).is("[escape!=false]")) {
								childVal = escapeCharacter(childVal);
							}
						}
						if (null != childVal && undefined != childVal) {
							if ($(childElement).attr("multiple") || $(childElement).is("[select2=true][jsonArray=true]")) {
								var multiVal = $(childElement).select2("val");
								var valArr = multiVal;
								var jsonVal = "[";
								if (0 < valArr.length) {
									for (var i = 0; i < valArr.length; i++) {
										jsonVal += ("\"" + valArr[i] + "\",");
									}
									jsonVal = jsonVal.substr(0, jsonVal.length - 1);
								}
								jsonVal += "]";
								childData += (',"' + $(childElement).attr("name") + '":' + jsonVal + '');
							} else {
								childVal = $(childElement).val();
								childData += (',"' + $(childElement).attr("name") + 'SelectText":"' + (childVal === '' ? '' : $(childElement).find('option:selected').text()) + '"');
								childData += (',"' + $(childElement).attr("name") + '":"' + $(childElement).select2("val") + '"');
							}
						}
					});
					childData += "}";
					childData += ((childrenObjs.length - 1 > i) ? "," : "");
				}
				childData += "]";
				val = childData;
			} else {
				if ($(element).is("[escape!=false]")) {
					val = escapeCharacter(val);
				}
			}
			data += ('"' + ($(element).attr("name") || $(element).attr("childrenEntityName")) + ($(element).attr("childrenEntityName") ? ('":' + val + (fields.length - 1 > index ? ',' : '')) : ('":"' + val + (fields.length - 1 > index ? '",' : '"'))));
		}
	});
	if (data.lastIndexOf(",") === data.length - 1) {
		data = (data.substr(0, data.length - 1));
	}
	var selectFields = formJQObj.find("select:not(:disabled)[children!=true],:not(object)[select2=true][jsonArray=true][children!=true]");
	selectFields.each(function(index, element) {
		var val = $(element).val();
		if ($(element).is("[base64=true]")) {
			val = Base64.encode(val);
		} else {
			if ($(element).is("[escape!=false]")) {
				val = escapeCharacter(val);
			}
		}
		if (null != val && undefined != val) {
			if ($(element).attr("multiple") || $(element).is("[select2=true][jsonArray=true]")) {
				var multiVal = $(element).select2("val");
				var valArr = multiVal;
				var jsonVal = "[";
				if (0 < valArr.length) {
					for (var i = 0; i < valArr.length; i++) {
						jsonVal += ("\"" + valArr[i] + "\",");
					}
					jsonVal = jsonVal.substr(0, jsonVal.length - 1);
				}
				jsonVal += "]";
				data += (',"' + $(element).attr("name") + '":' + jsonVal + '');
			} else {
				val = $(element).val();
				data += (',"' + $(element).attr("name") + 'SelectText":"' + (val === '' ? '' : $(element).find('option:selected').text()) + '"');
				data += (',"' + $(element).attr("name") + '":"' + $(element).select2("val") + '"');
			}
		}
	});
	data += "}";
	return data;
}

function getData(url, formJQObj, id, successCallback) {
	$.ajax({
		type : "POST",
		url : baseUrl + url,
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(respData, textStatus, jqXHR) {
			successCallback(respData, textStatus, jqXHR, id, formJQObj);
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

function sendData(dataObj, successCallback, url, dialogId, errorCallback) {
	var successCallback = (successCallback ? successCallback : saveOrUpdateCallback);
	var reallyDialogId = (dialogId ? dialogId : "editDialog");
	var reallyUrl = (url ? url : $("#" + reallyDialogId + " #url").val());
	var data = null;
	if (dataObj instanceof jQuery) {
		doBeforeJSON(dataObj);
		data = getJSON(dataObj);
	} else {
		data = dataObj;
	}
	$.ajax({
		type : "POST",
		url : baseUrl + reallyUrl,
		data : data,
		dataType : "json",
		success : function(respData, textStatus, jqXHR) {
			successCallback(respData, textStatus, jqXHR, data, reallyDialogId);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (errorCallback) {
				errorCallback(jqXHR, textStatus, errorThrown);
			} else {
				$("#" + reallyDialogId + " #saveBtn").button('reset');
				$('#alertDialog #alertDialogH4').text('错误提示');
				$('#alertDialog #alertDialogAlertHeading').text('获取返回值失败：');
				$('#alertDialog #alertContext').text(textStatus + " : " + errorThrown);
				$('#alertDialog #alertBtn').text('确认');
				$('#alertDialog #alertBtn').click(function() {
					$('#alertDialog').modal('hide');
					$("#" + reallyDialogId + " #saveBtn").button('reset');
				});
				$('#alertDialog').modal('show');
			}
		}
	});
}

function doBeforeJSON(dataObj) {
}

function setAjaxFormCallback(respData, textStatus, jqXHR, id, formJQObj) {
}

function saveOrUpdateCallback(respData, textStatus, jqXHR, insertData, dialogId) {
	var reallyDialogId = (dialogId ? dialogId : "editDialog");
	$("#" + reallyDialogId + " #saveBtn").button('reset');
	if (respData.result) {
		$('#successDialog #successDialogH4').text('操作提示');
		$('#successDialog #successDialogAlertHeading').text('保存成功：');
		$('#successDialog #successContext').text("信息 : 保存成功");
		$('#successDialog #successBtn').text('确认');
		$('#successDialog #successBtn').click(function() {
			$('#successDialog').modal('hide');
			var operType = $("#" + reallyDialogId + " #operType").val();
			if ("save" === operType) {
				afterSaveCallback(respData.id, jQuery.parseJSON(insertData));
			} else {
				afterUpdateCallback(respData.id, jQuery.parseJSON(insertData));
			}
		});
		$('#' + reallyDialogId + '').modal('hide');
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

function afterSaveCallback(id, rowObj) {
	// alert(rowObj.email);
	// var tdIdObj = $("#mainTable thead td[id]");
	// var newTdStr = "<tr onselectstart=\"return false;\" style=\"-moz-user-select: none\">";
	// tdIdObj.each(function(index, element) {
	// var val = rowObj[element.id];
	// if (val != undefined) {
	// if ("id" === element.id) {
	// newTdStr += "<td class=\"" + (index + 1) + "\"><label class=\"checkbox\"><input type=\"checkbox\" class=\"checkbox\" />" + id + "</label><input type=\"hidden\" name=\"userId\" identity=\"true\" value=\"" + id + "\" /></td>";
	// } else if ($(element).attr("element") === "select") {
	// var text = rowObj[element.id + "SelectText"];
	// newTdStr += "<td class=\"" + $(element).attr("class") + "\">" + text + "</td>";
	// } else {
	// newTdStr += "<td class=\"span" + (index + 1) + "\">" + val + "</td>";
	// }
	// }
	// });
	//
	// newTdStr += "<td class=\"span2\">" + getSelectTxt($("#selectUserType"), rowObj.userTypeId) + "</td><td class=\"span1\">" + rowObj.disable + "</td></tr>";
	// // alert(newTdStr);
	// $("#mainTable").append(newTdStr);
	// /* *************************** */
	// research();// 重置查询
	location.reload();
}

function afterUpdateCallback(id, rowObj) {
	// var thisTrObj = $("#mainTable tbody tr input[identity=true][value=" + id + "]").parent().parent();
	// var tdIdObj = $("#mainTable thead td[id]");
	// tdIdObj.each(function(index, element) {
	// var val = rowObj[element.id];
	// if (val != undefined) {
	// var tdObj = thisTrObj.find("input[identity=true],td:not(:has(label))")[index];
	// if ($(element).attr("element") === "select") {
	// var text = rowObj[element.id + "SelectText"];
	// $(tdObj).text(text);
	// } else {
	// $(tdObj).text(val);
	// }
	// }
	// });
	// var tdObj2 = thisTrObj.find("td:eq(4)");
	// if (rowObj.disable == "true") {
	// $(tdObj2).text("是");
	// } else {
	// $(tdObj2).text("否");
	// }
	// /* *************************** */
	// research();// 重置查询
	location.reload();
}

function afterDeleteCallback(trJQObj) {
	// trJQObj.remove();
	location.reload();
}

function deleteCallback(respData, textStatus, jqXHR, data, dialogId) {
	var reallyDialogId = (dialogId ? dialogId : "editDialog");
	if (respData.result) {
		$('#successDialog #successDialogH4').text('操作提示');
		$('#successDialog #successDialogAlertHeading').text('删除成功：');
		$('#successDialog #successContext').text("信息 : 删除成功");
		$('#successDialog #successBtn').text('确认');
		$('#successDialog #successBtn').click(function() {
			var trObjs = $("#mainTable tbody input[type=checkbox]:checked").parent().parent().parent();
			afterDeleteCallback(trObjs);
			$('#successDialog').modal('hide');
		});
		$('#' + reallyDialogId + '').modal('hide');
		$('#successDialog').modal('show');
	} else {
		$('#alertDialog #alertDialogH4').text('错误提示');
		$('#alertDialog #alertDialogAlertHeading').text('删除失败：');
		$('#alertDialog #alertContext').text(respData.msg);
		$('#alertDialog #alertBtn').text('确认');
		$('#alertDialog #alertBtn').click(function() {
			$('#alertDialog').modal('hide');
		});
		$('#alertDialog').modal('show');
	}
}