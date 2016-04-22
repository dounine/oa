$(function() {
	formValidateInit();
});

function formValidateInit() {
	// 表单验证
	$("#mainForm").validate({
		rules : {
			username : {
				required : true,
				minlength : 3,
				remote : {
					type : "post",
					url : baseUrl + "/user/testUserName.do",
					data : {
						username : function() {
							return $("input[name=username]").val();
						}
					},
					dataType : "html",
					dataFilter : function(data, type) {
						return (data == "true");
					}
				}
			},
			password : {
				required : true,
				minlength : 6
			},
			cPassword : {
				required : true,
				minlength : 6,
				equalTo : "[name=password]"
			},
			name : {
				required : true
			},
			unit : {
				required : true
			},
			email : {
				email : true
			}
		},
		messages : {
			username : {
				required : "用户名必须填写",
				minlength : "用户名长度不能小于3个字符",
				remote : "用户名已经被使用"
			},
			password : {
				required : "密码必须填写",
				minlength : "密码长度至少为6位"
			},
			cPassword : {
				required : "确认密码必须填写",
				minlength : "密码长度至少为6位",
				equalTo : "两次输入密码不一致不一致"
			},
			name : {
				required : "真实姓名必须填写"
			},
			unit : {
				required : "单位必须填写"
			},
			email : {
				email : "请输入正确格式的电子邮件"
			}
		}
	});
}