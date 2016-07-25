package club.zhcs.monitor.domain.resource;

import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Lang;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-domain
 *
 * @file DataBase.java
 *
 * @description //TODO description
 *
 * @time 2016年7月7日 下午7:15:06
 *
 */
@Table("t_database")
@Comment("数据库资源")
public class DataBase extends Resource {

	{
		setResourceType(ResourceType.DATABASE);
	}
	@Column("d_type")
	@Comment("数据库类型")
	private DB type;

	@Column("d_db_address")
	@Comment("数据库服务器地址")
	private String dbAddress;

	@Column("d_db_port")
	@Comment("数据库端口")
	private int port;

	@Column("d_db_schame")
	@Comment("数据库模式")
	private String schame;

	@Column("d_db_user")
	@Comment("数据库用户")
	private String user;

	@Column("d_db_pwd")
	@Comment("数据库密码")
	private String password;

	/**
	 * @return the type
	 */
	public DB getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(DB type) {
		this.type = type;
	}

	/**
	 * @return the dbAddress
	 */
	public String getDbAddress() {
		return dbAddress;
	}

	/**
	 * @param dbAddress
	 *            the dbAddress to set
	 */
	public void setDbAddress(String dbAddress) {
		this.dbAddress = dbAddress;
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
	 * @return the schame
	 */
	public String getSchame() {
		return schame;
	}

	/**
	 * @param schame
	 *            the schame to set
	 */
	public void setSchame(String schame) {
		this.schame = schame;
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

	/**
	 * 获取jdbc连接
	 * 
	 * @return
	 */
	public String getJDBCUrl() {
		switch (type) {
		case MYSQL:
			return String.format("jdbc:mysql://%s:%d/%s", dbAddress, port, schame);
		case ORACLE:
			return String.format("jdbc:oracle:thin:@%s:%d:%s", dbAddress, port, schame);
		case SQLSERVER:
			return String.format("jdbc:microsoft:sqlserver://%s:%d;DatabaseName=%s", dbAddress, port, schame);
		case PSQL:
			return String.format("jdbc:postgresql://%s:%d/%s", dbAddress, port, schame);
		default:
			break;
		}
		throw Lang.makeThrow("db %s not support", type);
	}

}
