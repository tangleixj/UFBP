package ufbp.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ufbp.exception.LoadConfigFileException;

/**
 * 数据库连接池
 * 
 * @see getConnection
 * @author 小磊子
 *
 */
public class DBConnectPool {
	private static Log log = LogFactory.getLog(DBConnectPool.class);
	private static final String DRIVER = "driver";
	private static final String URL = "url";
	private static final String NAME = "name";
	private static final String PASSWD = "passwd";
	private static final String INIT_SIZE = "init_size";
	private static final String MAX_SIZE = "max_size";
	private static final String INCREMENT_NUM = "increment_num";

	private String driver;// 驱动类名
	private String url;// 地址信息
	private String name;// 连接数据库用户名
	private String passwd;// 连接数据库用户名密码
	private int initSize = 20;// 初始连接数
	private int maxSzie = 50;// 最大连接数
	private int increNum = 10;// 增量
	private int busyNum;// 正在使用链接数
	private List<Connection> connList = new CopyOnWriteArrayList<Connection>();// 连接数据
	private static DBConnectPool pool;

	public static DBConnectPool getInstance() {
		if (pool == null) {
			synchronized (DBConnectPool.class) {
				pool = new DBConnectPool();
			}
		}
		return pool;
	}

	private DBConnectPool() {
		initPool();
	}

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

	private void createConnection(int num) {
		try {
			Class.forName(driver);
			for (int i = 0; i < num; i++) {
				Connection conn = DriverManager.getConnection(url, name, passwd);
				connList.add(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.DATA_ERROR, driver);
		}
	}

	/**
	 * 初始化连接池
	 */
	private void initPool() {
		Properties prop = loadProp();
		driver = prop.getProperty(DRIVER);
		if (driver == null) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.MISS_ELEMENT, DRIVER);
		}
		url = prop.getProperty(URL);
		if (url == null) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.MISS_ELEMENT, URL);
		}
		name = prop.getProperty(NAME);
		if (name == null) {
			throw new LoadConfigFileException(LoadConfigFileException.ERROR_TYPE.MISS_ELEMENT, NAME);
		}
		passwd = prop.getProperty(PASSWD);
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
		createConnection(initSize);
		busyNum = 0;
		if (log.isInfoEnabled()) {
			log.info("创建数据库连接池完毕");
		}

	}

	/**
	 * 获取连接
	 * 
	 * @return 连接
	 * @throws SQLException
	 */
	public synchronized Connection getConnection() throws SQLException {
		if (connList.size() > 0) {
			final Connection conn = connList.remove(connList.size() - 1);
			busyNum++;
			/**
			 * 返回链接的代理。
			 */
			return (Connection) Proxy.newProxyInstance(DBConnectPool.class.getClassLoader(),
					conn.getClass().getInterfaces(), new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							/**
							 * 当链接关闭时，不释放连接，仅仅将连接重新放到connList中。
							 */
							if (method.getName().equals("close")) {
								connList.add(conn);
								busyNum--;
								return null;
							} else {
								return method.invoke(conn, args);
							}
						}
					});
		} else if (busyNum + increNum <= maxSzie) {
			createConnection(increNum);
			return getConnection();
		}
		if (log.isErrorEnabled()) {
			log.error("数据库连接池已满,最大值[" + maxSzie + "],已使用[" + busyNum + "]");
		}
		return null;
	}

	public static void main(String[] args) {
		DBConnectPool pool = DBConnectPool.getInstance();
		try {
			Connection conn = pool.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
