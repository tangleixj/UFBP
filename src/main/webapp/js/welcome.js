/**
 * 页面加载
 */
jQuery(document).ready(function() {
	showTime();// 显示时间
	getUser();// 获取当前登录用户名
	loadPage();// 加载子页面
	/**
	 * 定义变量存储当前登录用户信息
	 */
	var user_name = "";
	var user_passwd = "";
});

/**
 * 加载各个子页面
 */
function loadPage() {
	$('#ipconfig').load('ipconfig.html');
	$('#bankconfig').load('bankconfig.html');
	// $('#log').load('log.html');
	$('#patchinfor').load('patchinfor.html');
	// $('#newpatch').load('newpatch.html');
	$('#interface').load('interface.html');
	$('#version').load('version.html');
	$('#help').load('help.html');
}

/**
 * 显示时间
 */
function showTime() {
	var now = (new Date()).toLocaleString();
	$('#time').text(now);
	setInterval(function() {
		now = (new Date()).toLocaleString();
		$('#time').text(now);
	}, 1000);
}

/**
 * 获取当前登录用户
 */
function getUser() {
	$.ajax({
		type : 'POST',
		url : 'getUserInfor',
		cache : false,
		success : function(data) {
			/**
			 * 利用ajax获取当前登录用户信息
			 */
			user_name = data.name;
			user_passwd = data.passwd;
			$('#user_name').html(user_name);
		}
	});
}

/**
 * 居中显示
 */
function center(obj) {
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $(obj).height();
	var popupWidth = $(obj).width();
	$(obj).css({
		"position" : "absolute",
		"top" : (windowHeight - popupHeight) / 4 + $(document).scrollTop(),
		"left" : (windowWidth - popupWidth) / 3
	});
}
/**
 * 用户信息面板支持拖动
 */
$('#mousemove').mousedown(function(event) {
	var isMove = true;
	var abs_x = event.pageX / 2;
	var abs_y = event.pageY / 2;
	$(document).mousemove(function(event) {
		if (isMove) {
			var obj = $('#user_infor_div');
			obj.css({
				'left' : event.pageX - abs_x,
				'top' : event.pageY - abs_y
			});
		}
	}).mouseup(function() {
		isMove = false;
	});
});
/**
 * 用户资料点击事件
 */
$("#userinfor").click(function() {
	$("#user_infor_div").show();
	center($("#user_infor_div"));
	$(window).scroll(function() {
		center($("#user_infor_div"));
	});
	$(window).resize(function() {
		center($("#user_infor_div"));
	});
	$("#name").val(user_name);
});
/**
 * 用户详情面板初始化
 */
function userInofrInit() {
	$("#name").val(user_name);
	$('#passwd').val("");
	$('#confir_passwd').val("");
	nameInit($('#name'), $('#error_mess'), $('#save_update'), false, false);
	passwdInit($('#passwd'), $('#error_mess'), $('#save_update'), false, false)
	passwdInit($('#confir_passwd'), $('#error_mess'), $('#save_update'), false,
			false);
}
/**
 * 关闭用户信息面板
 */
function closeUserInforDiv() {
	userInofrInit();
	$("#user_infor_div").hide();
}
/**
 * 取消按钮事件
 */
$("#close_user_infor_div").click(function() {
	closeUserInforDiv();
});
/**
 * 保存按钮事件
 */
$("#cancel_save").click(function() {
	closeUserInforDiv();
});

/**
 * 定义变量，用户表示用户名和密码校验状态
 */
name_correct = false;
passwd_correct = false;

/**
 * 用户名获取焦点
 */
$('#name').focus(
		function() {
			nameInit(this, $('#error_mess'), $('#save_update'), name_correct,
					passwd_correct);
		});

/**
 * 用户名失去焦点
 */
$('#name').blur(
		function() {
			var name = $('#name').val();
			if (name != "") {
				if (name != user_name) {
					$.ajax({
						type : 'POST',
						url : 'checkNameUsed',
						data : {
							'name' : name,
						},
						cache : false,
						success : function(data) {
							var errorMess = data.errorMess;
							if (errorMess == "true") {
								name_correct = nameSuccess($('#name'),
										$('#error_mess'), $('#save_update'),
										name_correct, passwd_correct);
								$('#error_mess').html("用户名可用");
							} else {
								nameError($('#name'), $('#error_mess'),
										$('#save_update'), errorMess,
										name_correct, passwd_correct);
							}
						}
					});
				} else {
					name_correct = nameSuccess(this, $('#error_mess'),
							$('#save_update'), name_correct, passwd_correct);
				}
			} else {
				nameError(this, $('#error_mess'), $('#save_update'), "用户名不能为空",
						name_correct, passwd_correct);
			}
		});

/**
 * 密码获取焦点
 */
$('#passwd').focus(
		function() {
			passwdInit(this, $('#error_mess'), $('#save_update'), name_correct,
					passwd_correct);
		});

/**
 * 确认密码获取焦点
 */
$('confir_passwd').focus(
		function() {
			passwdInit(this, $('#error_mess'), $('#save_update'), name_correct,
					passwd_correct);
		});

/**
 * 两次密码一致性校验
 * 
 * @param passwd_obj
 *            密码输入框对象
 * @param confirm_passwd_obj
 *            确认密码输入框对象
 * @param button_obj
 *            保存按钮对象
 * @param name_flag
 *            用户名校验标志
 * @param passwd_flag
 *            密码校验标志
 */
function passwdCheck(passwd_obj, confirm_passwd_obj, error_obj, button_obj) {
	var passwd = $(passwd_obj).val();
	var confirm_passwd = $(confirm_passwd_obj).val();
	if (confirm_passwd != "") {
		if (passwd == confirm_passwd) {
			passwd_correct = true;
			inputSuccessView(passwd_obj, error_obj);
			inputSuccessView(confirm_passwd_obj, error_obj);
		} else {
			passwd_correct = false;
			inputErrorView(passwd_obj, error_obj, "");
			inputErrorView(confirm_passwd_obj, error_obj, "两次密码不一致");
		}
		buttonView(name_correct, passwd_correct, button_obj);
	}
}
/**
 * 密码失去焦点
 */
$('#passwd').blur(
		function() {
			var passwd = $(this).val();
			if (passwd != "") {
				passwdCheck(this, $('#confir_passwd'), $('#error_mess'),
						$('#save_update'), name_correct, passwd_correct);
			} else {
				passwdError(this, $('#error_mess'), $('#save_update'),
						'密码不能为空', name_correct, passwd_correct);
			}
		});
/**
 * 确认密码丢失焦点
 */
$('#confir_passwd').blur(
		function() {
			var passwd = $(this).val();
			if (passwd != "") {
				passwdCheck($('#passwd'), this, $('#error_mess'),
						$('#save_update'), name_correct, passwd_correct);
			} else {
				passwdError(this, $('#error_mess'), $('#save_update'),
						'密码不能为空', name_correct, passwd_correct);
			}
		})
/**
 * 保存按钮事件
 */
$('#save_update').click(function() {
	if (name_correct == true && passwd_correct == true) {
		var name = $('#name').val();
		var passwd = $('#confir_passwd').val();
		if (name == user_name && passwd == user_passwd) {
			$('#error_mess').html("数据未修改,不保存");
		} else {
			if (name == user_name) {
				name = null;
			}
			if (passwd == user_passwd) {
				passwd = null;
			}
			$.ajax({
				type : 'POST',
				url : 'updateUserInfor',
				data : {
					'name' : name,
					'passwd' : passwd,
				},
				cache : false,
				success : function(data) {
					user_name = data.name;
					user_passwd = data.passwd;
					$('#user_name').html(user_name);
					$('#error_mess').html("更新完毕");
				}
			});
		}
	} else {
		$('#error_mess').html("不符合要求，无法保存");
	}
});