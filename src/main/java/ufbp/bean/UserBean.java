package ufbp.bean;

public class UserBean {
	private String name;
	private String passwd;
	private int role = -1;

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

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public UserBean(String name, String passwd, int role) {
		super();
		this.name = name;
		this.passwd = passwd;
		this.role = role;
	}

	public UserBean(String name, String passwd) {
		super();
		this.name = name;
		this.passwd = passwd;
	}

	public UserBean() {
		super();
	}

	@Override
	public String toString() {
		return "UserBean [name=" + name + ", passwd=" + passwd + ", role=" + role + "]";
	}

}
