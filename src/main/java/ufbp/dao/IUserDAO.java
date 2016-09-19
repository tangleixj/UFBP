package ufbp.dao;

import ufbp.bean.UserBean;

/**
 * 用户信息管理
 * 
 * @author 小磊子
 *
 */
public interface IUserDAO {
	/**
	 * 添加账户
	 * 
	 * @param user
	 *            账户信息
	 * @return 执行结果
	 */
	public boolean addUser(UserBean user);

	/**
	 * 查询账户信息
	 * 
	 * @param userID
	 *            账户编号
	 * @return 账户信息
	 */
	public UserBean selectUser(int userID);

	/**
	 * 查询账户编号
	 * 
	 * @param name
	 *            账户名
	 * @param passwd
	 *            账户密码
	 * @return 账户编号
	 */
	public int selectUserID(String name);

	/**
	 * 查询用户密码
	 * 
	 * @param name
	 *            用户名
	 * @return 用户密码
	 */
	public String selectUserPasswd(String name);

	/**
	 * 查询用户角色
	 * 
	 * @param name
	 *            用户名
	 * @return 用户角色
	 */
	public int selectRole(String name);

	/**
	 * 更新用户名
	 * 
	 * @param userID
	 *            用户编号
	 * @param newName
	 *            用户名
	 * @return 执行结果
	 */
	public boolean updateUserName(int id, String newName);

	/**
	 * 更新用户密码
	 * 
	 * @param userID
	 *            账户编码
	 * @param passwd
	 *            账户密码
	 * @return 执行结果
	 */
	public boolean updateUserPasswd(String name, String newPasswd);

	/**
	 * 更新账户角色
	 * 
	 * @param userID
	 *            账户编码
	 * @param role
	 *            账户角色
	 * @return 执行结果
	 */
	public boolean updateUserRole(String name, int newRole);
}
