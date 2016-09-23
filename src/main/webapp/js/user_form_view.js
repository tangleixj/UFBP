/**
 * 文本框校验成功显示
 * 
 * @param input_obj
 *            文本框对象
 * @param error_obj
 *            异常信息显示对象
 * @param flag
 *            校验标志
 */
function inputSuccessView(input_obj, error_obj) {
	$(input_obj).parent().attr("class", "form-group has-success");
	$(error_obj).html('<br/>');
}

/**
 * 文本框校验异常显示
 * 
 * @param input_obj
 *            文本框对象
 * @param error_obj
 *            异常信息显示对象
 * @param error_mess
 *            异常信息
 * @param flag
 *            校验标志
 */
function inputErrorView(input_obj, error_obj, error_mess) {
	$(input_obj).parent().attr("class", "form-group has-error");
	$(error_obj).html(error_mess);
}

/**
 * 文本框初始化显示
 * 
 * @param input_obj
 *            文本框对象
 * @param error_obj
 *            异常信息显示对象
 */
function inputInitView(input_obj, error_obj) {
	$(input_obj).parent().attr("class", "form-group");
	$(error_obj).html("<br/>");
}

/**
 * 按钮显示更新
 * 
 * @param flag1
 *            校验标志
 * @param flag2
 *            校验标志
 * @param button_obj
 *            按钮对象
 */
function buttonView(flag1, flag2, button_obj) {
	if (flag1 == true && flag2 == true) {
		$(button_obj).attr("class", "btn btn-success");
	} else {
		$(button_obj).attr("class", "btn btn-danger");
	}
}

/**
 * 用户名校验成功显示
 * 
 * @param name_obj
 *            用户名输入框对象
 * @param error_obj
 *            异常信息显示对象
 * @param button_obj
 *            保存按钮对象
 * @param name_flag
 *            用户名校验标志
 * @param passwd_flag
 *            密码校验标志
 */
function nameSuccess(name_obj, error_obj, button_obj, name_flag, passwd_flag) {
	inputSuccessView(name_obj, error_obj);
	name_flag = true;
	buttonView(name_flag, passwd_flag, button_obj);
	return true;
}

/**
 * 用户名校验失败显示
 * 
 * @param name_obj
 *            用户名输入框对象
 * @param error_obj
 *            异常信息显示对象
 * @param button_obj
 *            保存按钮对象
 * @param error_mess
 *            异常信息
 * @param name_flag
 *            用户名校验标志
 * @param passwd_flag
 *            密码校验标志
 */
function nameError(name_obj, error_obj, button_obj, error_mess, name_flag,
		passwd_flag) {
	inputErrorView(name_obj, error_obj, error_mess);
	name_flag = false;
	buttonView(name_flag, passwd_flag, button_obj);
}

/**
 * 用户名初始化显示
 * 
 * @param name_obj
 *            用户名输入框对象
 * @param error_obj
 *            异常显示对象
 * @param button_obj
 *            按钮对象
 * @param name_flag
 *            用户名校验标志
 * @param passwd_flag
 *            密码校验标志
 */
function nameInit(name_obj, error_obj, button_obj, name_flag, passwd_flag) {
	inputInitView(name_obj, error_obj);
	name_flag = false;
	buttonView(name_flag, passwd_flag, button_obj);
}

/**
 * 密码初始化显示
 * 
 * @param passwd_obj
 *            密码输入框对象
 * @param error_obj
 *            异常信息显示对象
 * @param button_obj
 *            保存按钮对象
 * @param name_flag
 *            用户名校验标志
 * @param passwd_flag
 *            密码校验标志
 */
function passwdInit(passwd_obj, error_obj, button_obj, name_flag, passwd_flag) {
	inputInitView(passwd_obj, error_obj);
	passwd_flag = false;
	buttonView(name_flag, passwd_flag, button_obj);
}

/**
 * 密码校验异常显示
 * 
 * @param passwd_obj
 *            密码输入框对象
 * @param error_obj
 *            异常显示对象
 * @param button_obj
 *            按钮对象
 * @param error_mess
 *            异常信息
 * @param name_flag
 *            用户名校验标志
 * @param passwd_flag
 *            密码校验标志
 */
function passwdError(passwd_obj, error_obj, button_obj, error_mess, name_flag,
		passwd_flag) {
	inputErrorView(passwd_obj, error_obj, error_mess);
	passwd_flag = false;
	buttonView(name_flag, passwd_flag, button_obj);
}
