package ufbp.dao;

import ufbp.bean.UserBean;

/**
 * �û���Ϣ����
 * 
 * @author С����
 *
 */
public interface IUserDAO {
	/**
	 * ����˻�
	 * 
	 * @param user
	 *            �˻���Ϣ
	 * @return ִ�н��
	 */
	public boolean addUser(UserBean user);

	/**
	 * ��ѯ�˻���Ϣ
	 * 
	 * @param userID
	 *            �˻����
	 * @return �˻���Ϣ
	 */
	public UserBean selectUser(int userID);

	/**
	 * ��ѯ�˻����
	 * 
	 * @param name
	 *            �˻���
	 * @param passwd
	 *            �˻�����
	 * @return �˻����
	 */
	public int selectUserID(String name);

	/**
	 * ��ѯ�û�����
	 * 
	 * @param name
	 *            �û���
	 * @return �û�����
	 */
	public String selectUserPasswd(String name);

	/**
	 * ��ѯ�û���ɫ
	 * 
	 * @param name
	 *            �û���
	 * @return �û���ɫ
	 */
	public int selectRole(String name);

	/**
	 * �����û���
	 * 
	 * @param userID
	 *            �û����
	 * @param newName
	 *            �û���
	 * @return ִ�н��
	 */
	public boolean updateUserName(int id, String newName);

	/**
	 * �����û�����
	 * 
	 * @param userID
	 *            �˻�����
	 * @param passwd
	 *            �˻�����
	 * @return ִ�н��
	 */
	public boolean updateUserPasswd(String name, String newPasswd);

	/**
	 * �����˻���ɫ
	 * 
	 * @param userID
	 *            �˻�����
	 * @param role
	 *            �˻���ɫ
	 * @return ִ�н��
	 */
	public boolean updateUserRole(String name, int newRole);
}
