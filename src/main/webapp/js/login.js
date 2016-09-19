jQuery(document).ready(function() {
	App.init(); // Initialise plugins and elements
	var name_correct = false;
	var passwd_correct = false;
});

function swapScreen(id) {
	jQuery('.visible').removeClass('visible animated fadeInUp');
	jQuery('#' + id).addClass('visible animated fadeInUp');
}
/**
 * 用户名获取焦点事件
 */
$("#name").focus(function() {
	$("#name").attr("class", "form-control");
	$("#name").val("");
	$("#name-group").attr("class", "form-group");
	$("#error-mess").html("<br/>");
	name_correct = false;
});

/**
 * 用户名丢失焦点事件
 */
$("#name").blur(function() {
	var name = $('#name').val();
	if (name != "") {
		$.ajax({
			type : 'POST',
			url : 'checkName',
			data : {
				'name' : name,
			},
			cache : false,
			success : function(data) {
				var errorMess = data.errorMess;
				if (errorMess == "true") {
					name_correct = true;
					$('#name-group').addClass("has-success");
				} else {
					name_correct = false;
					$('#name-group').addClass("has-error");
					$('#error-mess').html(errorMess);
					$("#login-button").attr("class", "btn btn-danger");
				}
			}
		});
	} else {
		name_correct = false;
		$('#name-group').addClass("has-error");
		$('#error-mess').html("用户名不能为空");
	}
})
/**
 * 密码获取焦点事件
 */
$("#passwd").focus(function() {
	$("#passwd").attr("class", "form-control");
	$("#passwd").val("");
	$("#passwd-group").attr("class", "form-group");
	$("#error-mess").html("<br/>");
	passwd_correct = false;
})

/**
 * 用户密码校验
 */
$("#passwd").blur(function() {
	var name = $('#name').val();
	if (name_correct == true) {
		var passwd = $('#passwd').val();
		if (passwd != "") {
			$.ajax({
				type : 'POST',
				url : 'checkPasswd',
				data : {
					'name' : name,
					'passwd' : passwd
				},
				cache : false,
				success : function(data) {
					var errorMess = data.errorMess;
					if (errorMess == "true") {
						passwd_correct = true;
						$('#passwd-group').addClass("has-success");
						$("#login-button").attr("class", "btn btn-success");
					} else {
						passwd_correct = false;
						$('#passwd-group').addClass("has-error");
						$('#error-mess').html(errorMess);
						$("#login-button").attr("class", "btn btn-danger");
					}
				}
			});
		} else {
			passwd_correct = false;
			$('#passwd-group').addClass("has-error");
			$('#error-mess').html("密码不能为空");
			$("#login-button").attr("class", "btn btn-danger");
		}
	} else {
		if (name == "") {
			$("#error-mess").html("用户名为空");
		} else {
			$("#error-mess").html("用户名错误,请先输入有效的用户名");
		}
	}
})

/**
 * 登录按钮事件
 */
$('#login_form').submit(function() {
	if (name_correct == true && passwd_correct == true) {
		return true;
	} else {
		$("#error-mess").html("请输入正确的用户名密码");
		return false;
	}
})