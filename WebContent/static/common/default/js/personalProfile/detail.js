/**
 * 列表页匹配框JS
 */
$(function() {
	select2Init();
	setTimeout(function() {
		uploadInit();
	}, 100);
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
	$("#searchDialog select[name=bloodType]").select2({
		placeholder : "选择血型",
		allowClear : true
	});
}

function personalProfileClearForm(JQFormObj) {
	mainClearForm(JQFormObj);
	$("#photoImg").attr("src", baseUrl + "/static/common/default/img/pic-none.png");
}

function uploadInit() {
	$("#photoUpload").uploadify({
		buttonText : "浏览",
		swf : baseUrl + "/static/plugins/uploadify/uploadify.swf",
		uploader : baseUrl + "/personalProfile/uploadPhoto.do?jsessionid=" + sessionId,
		buttonImage : baseUrl + "/static/plugins/uploadify/browse-btn.png",
		multi : false,
		debug : false,
		fileTypeDesc : "请选择图片文件",
		fileTypeExts : "*.jpg;*.gif;*.jpeg;*.png;*.bmp",
		fileObjName : 'photoUpload',
		onUploadSuccess : function(file, response, success) {
			var obj = $.parseJSON(response);
			if (success && obj.success) {
				$("input[name=photo]").val(obj.url);
				$("#photoImg").attr("src", baseUrl + obj.url);
			} else {
				$('#alertDialog #alertDialogH4').text('错误提示');
				$('#alertDialog #alertDialogAlertHeading').text('上传失败：');
				$('#alertDialog #alertContext').text(obj.msg);
				$('#alertDialog #alertBtn').text('确认');
				$('#alertDialog #alertBtn').click(function() {
					$('#alertDialog').modal('hide');
				});
				$('#alertDialog').modal('show');
			}
		},
		onUploadError : function(file, errorCode, errorMsg, errorString) {
			$('#alertDialog #alertDialogH4').text('错误提示');
			$('#alertDialog #alertDialogAlertHeading').text('上传失败：');
			$('#alertDialog #alertContext').text(file.name + ": " + errorString);
			$('#alertDialog #alertBtn').text('确认');
			$('#alertDialog #alertBtn').click(function() {
				$('#alertDialog').modal('hide');
			});
			$('#alertDialog').modal('show');
		},
		onUploadError : function(file, errorCode, errorMsg, errorString) {
			$('#alertDialog #alertDialogH4').text('错误提示');
			$('#alertDialog #alertDialogAlertHeading').text('上传失败：');
			$('#alertDialog #alertContext').text(file.name + ": " + errorString);
			$('#alertDialog #alertBtn').text('确认');
			$('#alertDialog #alertBtn').click(function() {
				$('#alertDialog').modal('hide');
			});
			$('#alertDialog').modal('show');
		},
		onUploadStart : function(file) {
			$("#editDialog #saveBtn").attr('data-loading-text', "操作中...");
			$("#editDialog #clearBtn").attr('data-loading-text', "操作中...");
			$("#editDialog #saveBtn").button('loading');
			$("#editDialog #clearBtn").button('loading');
		},
		onUploadComplete : function(file) {
			$("#editDialog #saveBtn").attr('data-loading-text', "保存中...");
			$("#editDialog #clearBtn").attr('data-loading-text', "操作中...");
			$("#editDialog #saveBtn").button('reset');
			$("#editDialog #clearBtn").button('reset');
		}
	});
}