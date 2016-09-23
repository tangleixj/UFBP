package ufbp.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import ufbp.bean.UserBean;
import ufbp.service.UserMangerService;

/**
 * 用户管理动作
 * 
 * @author 小磊子
 *
 */
public class UserManagerAction extends ActionSupport {
	private static final long serialVersionUID = -1285376018357891565L;
	private Log log = LogFactory.getLog(UserManagerAction.class);
	private UserMangerService service = new UserMangerService();
	private String name;
	private String passwd;
	private String errorMess;

	/**
	 * 注销
	 */
	public String logout() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove(UserMangerService.USER);
		if (log.isDebugEnabled()) {
			log.debug("注销成功");
		}
		return SUCCESS;
	}

	/**
	 * 获取用户信息
	 */
	public String getUserInfor() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserBean user = (UserBean) session.get(UserMangerService.USER);
		name = user.getName();
		passwd = user.getPasswd();
		return SUCCESS;
	}

	/**
	 * 校验用户名是否可用
	 */
	public String checkNameUsed() {
		if (service.checkNameExists(name)) {
			if (log.isInfoEnabled()) {
				log.info("用户名[" + name + "]已使用");
			}
			errorMess = "用户名[" + name + "]已使用";
		} else {
			errorMess = "true";
		}
		return SUCCESS;
	}

	/**
	 * 更新用户信息
	 */
	public String updateUserInfor() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserBean user = (UserBean) session.get(UserMangerService.USER);
		/**
		 * 前端会对用户信息的修改情况做比对，如果有修改，则发送修改的字段，未修改的字段不发送。 所以后端中字段为空的表示未作修改。
		 * 如果所有信息都没有修改，前端不会发送请求。后端也无须考虑都为空的情况
		 */
		if (name != null && !"".equals(name)) {
			service.changeUserName(user, name);
			user.setName(name);
		}
		if (passwd != null && !"".equals(passwd)) {
			service.changeUserPasswd(user, passwd);
			user.setPasswd(passwd);
		}
		name = user.getName();
		passwd = user.getPasswd();
		if (log.isDebugEnabled()) {
			log.debug("更新用户信息完毕");
		}
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
