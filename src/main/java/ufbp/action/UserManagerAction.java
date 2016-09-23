package ufbp.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import ufbp.bean.UserBean;
import ufbp.service.UserMangerService;

/**
 * �û�������
 * 
 * @author С����
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
	 * ע��
	 */
	public String logout() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove(UserMangerService.USER);
		if (log.isDebugEnabled()) {
			log.debug("ע���ɹ�");
		}
		return SUCCESS;
	}

	/**
	 * ��ȡ�û���Ϣ
	 */
	public String getUserInfor() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserBean user = (UserBean) session.get(UserMangerService.USER);
		name = user.getName();
		passwd = user.getPasswd();
		return SUCCESS;
	}

	/**
	 * У���û����Ƿ����
	 */
	public String checkNameUsed() {
		if (service.checkNameExists(name)) {
			if (log.isInfoEnabled()) {
				log.info("�û���[" + name + "]��ʹ��");
			}
			errorMess = "�û���[" + name + "]��ʹ��";
		} else {
			errorMess = "true";
		}
		return SUCCESS;
	}

	/**
	 * �����û���Ϣ
	 */
	public String updateUserInfor() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserBean user = (UserBean) session.get(UserMangerService.USER);
		/**
		 * ǰ�˻���û���Ϣ���޸�������ȶԣ�������޸ģ������޸ĵ��ֶΣ�δ�޸ĵ��ֶβ����͡� ���Ժ�����ֶ�Ϊ�յı�ʾδ���޸ġ�
		 * ���������Ϣ��û���޸ģ�ǰ�˲��ᷢ�����󡣺��Ҳ���뿼�Ƕ�Ϊ�յ����
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
			log.debug("�����û���Ϣ���");
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
