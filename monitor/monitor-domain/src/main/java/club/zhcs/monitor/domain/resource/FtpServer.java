package club.zhcs.monitor.domain.resource;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-domain
 *
 * @file FtpServer.java
 *
 * @description //TODO description
 *
 * @time 2016年7月7日 下午7:18:41
 *
 */
@Table("t_ftp_server")
@Comment("ftp服务器")
public class FtpServer extends Resource {

	{
		setResourceType(ResourceType.FTP);
	}

	@Column("ftp_type")
	@Comment("ftp服务类型")
	private Type type = Type.FTP;

	@Column("ftp_server")
	@Comment("ftp服务器地址")
	private String server;

	@Column("ftp_port")
	@Comment("ftp端口")
	private int port;

	@Column("ftp_user")
	@Comment("ftp用户")
	private String user;

	@Column("ftp_pwd")
	@Comment("ftp密码")
	private String password;

	@Column("ftp_test_resource_path")
	@Comment("测试资源路径")
	private String testResourcePath;

	/**
	 * @return the testResourcePath
	 */
	public String getTestResourcePath() {
		return testResourcePath;
	}

	/**
	 * @param testResourcePath
	 *            the testResourcePath to set
	 */
	public void setTestResourcePath(String testResourcePath) {
		this.testResourcePath = testResourcePath;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server
	 *            the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public static enum Type {
		FTP, SFTP
	}

}
