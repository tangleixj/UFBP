package ufbp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ufbp.bean.UserBean;
import ufbp.common.util.DBConnectPool;
import ufbp.dao.IUserDAO;

public class UserDAOImpl implements IUserDAO {
	private Log log = LogFactory.getLog(UserDAOImpl.class);

	/**
	 * 释放资源
	 * 
	 * @param state
	 *            执行语句
	 * @param conn
	 *            连接
	 */
	private void close(PreparedStatement state, Connection conn) {
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
			}
		}
		if (conn != null) {
			try {
				/**
				 * 数据库连接池采用动态代理，此处关闭并不会关闭连接仅仅将连接还给连接池
				 */
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public boolean addUser(UserBean user) {
		boolean flag = true;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "insert into admin(username,password,level) value(?,?,?)";
		try {
			conn = DBConnectPool.getInstance().getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, user.getName());
			state.setString(2, user.getPasswd());
			state.setInt(3, user.getRole());

			if (log.isDebugEnabled()) {
				log.debug("执行SQL [" + state.toString() + "]");
			}

			state.executeUpdate();
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			close(state, conn);
		}
		return flag;
	}

	/**
	 * 查询表中单条记录的单个属性
	 * 
	 * @param optionName
	 *            条件名
	 * @param optionValue
	 *            条件值
	 * @param targetName
	 *            目标名
	 * @param targetType
	 *            目标数据类型
	 * @return 目标值
	 */
	private Object selectSingleColumn(String optionName, Object optionValue, String targetName, Object targetType) {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = DBConnectPool.getInstance().getConnection();
			state = conn.prepareStatement("select " + targetName + " from admin where " + optionName + "=?");
			if (optionValue instanceof String) {
				state.setString(1, (String) optionValue);
			} else if (optionValue instanceof Integer) {
				state.setInt(1, (Integer) optionValue);
			}

			if (log.isDebugEnabled()) {
				log.debug("执行SQL [" + state.toString() + "]");
			}

			ResultSet set = state.executeQuery();
			while (set.next()) {
				if (targetType == String.class) {
					return set.getString(targetName);
				} else if (targetType == Integer.class) {
					return set.getInt(targetName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(state, conn);
		}
		return null;
	}

	@Override
	public UserBean selectUser(int userID) {
		UserBean user = null;
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = DBConnectPool.getInstance().getConnection();
			state = conn.prepareStatement("select username,password,level from admin where id = ?");
			state.setInt(1, userID);

			if (log.isDebugEnabled()) {
				log.debug("执行SQL [" + state.toString() + "]");
			}

			ResultSet set = state.executeQuery();
			user = new UserBean();
			while (set.next()) {
				user.setName(set.getString("username"));
				user.setPasswd(set.getString("password"));
				user.setRole(set.getInt("level"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(state, conn);
		}
		return user;
	}

	@Override
	public int selectUserID(String name) {
		Object obj = selectSingleColumn("username", name, "id", Integer.class);
		return obj == null ? -1 : (Integer) obj;
	}

	@Override
	public String selectUserPasswd(String name) {
		Object obj = selectSingleColumn("username", name, "password", String.class);
		return obj == null ? null : (String) obj;
	}

	@Override
	public int selectRole(String name) {
		Object obj = selectSingleColumn("username", name, "level", Integer.class);
		return obj == null ? -1 : (Integer) obj;
	}
	/**
	 * 更新表中单条记录的单个属性
	 * 
	 * @param optionName
	 *            条件名
	 * @param optionValue
	 *            条件值
	 * @param targetName
	 *            目标名
	 * @param targetValue
	 *            目标值
	 * @return 执行结果
	 */
	public boolean updateSingleColumn(String optionName, Object optionValue, String targetName, Object targetValue) {
		boolean flag = true;
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = DBConnectPool.getInstance().getConnection();
			state = conn.prepareStatement("update admin set " + targetName + " =? where " + optionName + " =?");
			if (targetValue instanceof String) {
				state.setString(1, (String) targetValue);
			} else if (targetValue instanceof Integer) {
				state.setInt(1, (Integer) targetValue);
			}

			if (optionValue instanceof String) {
				state.setString(2, (String) optionValue);
			} else if (optionValue instanceof Integer) {
				state.setInt(2, (Integer) optionValue);
			}

			if (log.isDebugEnabled()) {
				log.debug("执行SQL [" + state.toString() + "]");
			}

			state.executeUpdate();
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			close(state, conn);
		}
		return flag;
	}

	@Override
	public boolean updateUserName(int id, String newName) {
		return updateSingleColumn("id", id, "username", newName);
	}

	@Override
	public boolean updateUserPasswd(String name, String newPasswd) {
		return updateSingleColumn("username", name, "password", newPasswd);
	}

	@Override
	public boolean updateUserRole(String name, int newRole) {
		return updateSingleColumn("username", name, "level", newRole);
	}

	public static void main(String[] args) {
	}
}
