package ufbp.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ufbp.bean.UserBean;
import ufbp.dao.IUserDAO;
import ufbp.dao.impl.UserDAOImpl;
import ufbp.exception.ServiceException;

public class UserMangerService {
	private Log log = LogFactory.getLog(UserMangerService.class);
	public static final String USER = "user";
	private IUserDAO userDAO = new UserDAOImpl();

	/**
	 * �ж��û����Ƿ����
	 * 
	 * @param name
	 *            �û���
	 * @return �жϽ��
	 */
	public boolean checkNameExists(String name) {
		if (name == null) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		int id = userDAO.selectUserID(name);
		if (id != -1) {
			return true;
		}
		return false;
	}

	/**
	 * У���û���Ϣ
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @return У����
	 */
	public boolean checkUserInfor(UserBean user) {
		if (user.getName() == null || user.getPasswd() == null) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		String passwd = userDAO.selectUserPasswd(user.getName());
		if (passwd != null && passwd.equals(user.getPasswd())) {
			return true;
		}
		return false;
	}

	/**
	 * ��¼����
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @return �����û���Ϣ
	 */
	public UserBean login(UserBean user) {
		if (user.getName() == null) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		int userID = userDAO.selectUserID(user.getName());
		return userDAO.selectUser(userID);
	}

	/**
	 * ע��
	 * 
	 * @param user
	 *            �û�
	 * @return ִ�н��
	 */
	public boolean register(UserBean user) {
		if (user.getName() == null || user.getPasswd() == null || user.getRole() == -1) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		if (!checkNameExists(user.getName())) {
			return userDAO.addUser(user);
		}
		return false;
	}

	/**
	 * �޸��û���
	 * 
	 * @param user
	 *            �û�
	 * @param newName
	 *            ���û���
	 * @return ִ�н��
	 */
	public boolean changeUserName(UserBean user, String newName) {
		if (user.getName() == null || newName == null) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		int userID = userDAO.selectUserID(user.getName());
		if (userID != -1) {
			return userDAO.updateUserName(userID, newName);
		}
		return false;
	}

	/**
	 * �޸�����
	 * 
	 * @param user
	 *            �û�
	 * @param newPasswd
	 *            ������
	 * @return ִ�н��
	 */
	public boolean changeUserPasswd(UserBean user, String newPasswd) {
		if (user.getName() == null || newPasswd == null) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		return userDAO.updateUserPasswd(user.getName(), newPasswd);
	}

	/**
	 * �޸Ľ�ɫ
	 * 
	 * @param user
	 *            �û���
	 * @param newRole
	 *            �û�����
	 * @return ִ�н��
	 */
	public boolean changeUserRole(UserBean user, int newRole) {
		if (user.getName() == null || newRole == -1) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		return userDAO.updateUserRole(user.getName(), newRole);
	}
}
