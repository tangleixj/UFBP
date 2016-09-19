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
	 * 判断用户名是否存在
	 * 
	 * @param name
	 *            用户名
	 * @return 判断结果
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
	 * 校验用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 校验结果
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
	 * 登录操作
	 * 
	 * @param user
	 *            用户信息
	 * @return 更新用户信息
	 */
	public UserBean login(UserBean user) {
		if (user.getName() == null) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		int userID = userDAO.selectUserID(user.getName());
		return userDAO.selectUser(userID);
	}

	/**
	 * 注册
	 * 
	 * @param user
	 *            用户
	 * @return 执行结果
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
	 * 修改用户名
	 * 
	 * @param user
	 *            用户
	 * @param newName
	 *            新用户名
	 * @return 执行结果
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
	 * 修改密码
	 * 
	 * @param user
	 *            用户
	 * @param newPasswd
	 *            新密码
	 * @return 执行结果
	 */
	public boolean changeUserPasswd(UserBean user, String newPasswd) {
		if (user.getName() == null || newPasswd == null) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		return userDAO.updateUserPasswd(user.getName(), newPasswd);
	}

	/**
	 * 修改角色
	 * 
	 * @param user
	 *            用户名
	 * @param newRole
	 *            用户密码
	 * @return 执行结果
	 */
	public boolean changeUserRole(UserBean user, int newRole) {
		if (user.getName() == null || newRole == -1) {
			throw new ServiceException(ServiceException.PARAMETER_IS_NULL, null);
		}
		return userDAO.updateUserRole(user.getName(), newRole);
	}
}
