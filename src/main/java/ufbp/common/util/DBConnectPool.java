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
 * ���ݿ����ӳ�
 * 
 * @see getConnection
 * @author С����
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

	private String driver;// ��������
	private String url;// ��ַ��Ϣ
	private String name;// �������ݿ��û���
	private String passwd;// �������ݿ��û�������
	private int initSize = 20;// ��ʼ������
	private int maxSzie = 50;// ���������
	private int increNum = 10;// ����
	private int busyNum;// ����ʹ��������
	private List<Connection> connList = new CopyOnWriteArrayList<Connection>();// ��������
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
	 * �������ݿ����ӳ�����
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
	 * ��ʼ�����ӳ�
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
			log.info("�������ݿ����ӳ����");
		}

	}

	/**
	 * ��ȡ����
	 * 
	 * @return ����
	 * @throws SQLException
	 */
	public synchronized Connection getConnection() throws SQLException {
		if (connList.size() > 0) {
			final Connection conn = connList.remove(connList.size() - 1);
			busyNum++;
			/**
			 * �������ӵĴ���
			 */
			return (Connection) Proxy.newProxyInstance(DBConnectPool.class.getClassLoader(),
					conn.getClass().getInterfaces(), new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							/**
							 * �����ӹر�ʱ�����ͷ����ӣ��������������·ŵ�connList�С�
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
			log.error("���ݿ����ӳ�����,���ֵ[" + maxSzie + "],��ʹ��[" + busyNum + "]");
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
