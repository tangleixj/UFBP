package ufbp.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ufbp.exception.LoadConfigFileException;

/**
 * 数据库连接池
 * 
 * @author 小磊子
 *
 */
public class DBConnectPool implements DataSource {
	private static Log log = LogFactory.getLog(DBConnectPool.class);
	private static final String DRIVER = "driver";
	private static final String URL = "url";
	private static final String NAME = "name";
	private static final String PASSWD = "passwd";
	private static final String INIT_SIZE = "init_size";
	private static final String MAX_SIZE = "max_size";
	private static final String INCREMENT_NUM = "increment_num";

	private int initSize = 20;// 初始连接数
	private int maxSzie = 50;// 最大连接数
	private int increNum = 10;// 增量

	private static DBConnectPool pool;

	/**
	 * 加载数据库连接池配置
	 */
	private Properties loadProp() {
		FileInputStream fis = null;
		Properties prop = new Properties();
		String propPath = DBConnectPool.class.getResource("/jdbc.properties").getFile();
		try {
			fis = new FileInputStream(propPath);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.FILE_NOT_FOUND, propPath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
				fis = null;
			}
		}

		return prop;
	}

	/**
	 * 初始化连接池
	 */
	public void initPool() {
		Properties prop = loadProp();
		String driver = prop.getProperty(DRIVER);
		if (driver == null) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.MISS_ELEMENT, DRIVER);
		}
		String url = prop.getProperty(URL);
		if (url == null) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.MISS_ELEMENT, URL);
		}
		String name = prop.getProperty(NAME);
		if (name == null) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.MISS_ELEMENT, NAME);
		}
		String passwd = prop.getProperty(PASSWD);
		if (passwd == null) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.MISS_ELEMENT, PASSWD);
		}
		String initSizeStr = prop.getProperty(INIT_SIZE);
		if (initSizeStr != null) {
			try {
				initSize = Integer.parseInt(initSizeStr);
			} catch (Exception e) {
				throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.TYPE_MISMATCH, INIT_SIZE);
			}
		}
		String maxSizeStr = prop.getProperty(MAX_SIZE);
		if (maxSizeStr != null) {
			try {
				maxSzie = Integer.parseInt(maxSizeStr);
			} catch (Exception e) {
				throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.TYPE_MISMATCH, MAX_SIZE);
			}
		}
		String incrNumStr = prop.getProperty(INCREMENT_NUM);
		if (incrNumStr != null) {
			try {
				increNum = Integer.parseInt(incrNumStr);
			} catch (Exception e) {
				throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.TYPE_MISMATCH, INCREMENT_NUM);
			}
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
	}
}
