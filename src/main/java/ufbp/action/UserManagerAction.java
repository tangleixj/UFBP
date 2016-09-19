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
	private String name;
	private String passwd;

	/**
	 * 获取登录用户名
	 * 
	 */
	public String getLoginName() {
		ActionContext act = ActionContext.getContext();
		Map<String, Object> session = act.getSession();
		UserBean user = session.get(UserMangerService.USER) == null ? null
				: (UserBean) session.get(UserMangerService.USER);
		if (user != null) {
			name = user.getName();
			if (log.isDebugEnabled()) {
				log.debug("当前登录用户[" + name + "]");
			}
			return SUCCESS;
		}
		if (log.isErrorEnabled()) {
			log.error("当前未登录");
		}
		return ERROR;
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

}
