package ufbp.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import ufbp.bean.UserBean;
import ufbp.service.UserMangerService;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = -5601672869619992191L;
	private Log log = LogFactory.getLog(LoginAction.class);
	private UserMangerService service = new UserMangerService();
	private String name;
	private String passwd;
	private String errorMess;

	public String checkUserExists() {
		if (service.checkNameExists(name)) {
			errorMess = "true";
		} else {
			errorMess = "用户名不存在";
		}
		return SUCCESS;
	}

	public String checkUserInfor() {
		UserBean user = new UserBean(name, passwd);
		if (service.checkUserInfor(user)) {
			errorMess = "true";
		} else {
			errorMess = "用户密码错误";
		}
		return SUCCESS;
	}

	public String login() {
		UserBean user = new UserBean(name, passwd);
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put(UserMangerService.USER, service.login(user));
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getErrorMess() {
		return errorMess;
	}

	public void setErrorMess(String errorMess) {
		this.errorMess = errorMess;
	}

}
