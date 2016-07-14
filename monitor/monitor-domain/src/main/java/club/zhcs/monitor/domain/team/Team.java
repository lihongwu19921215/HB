package club.zhcs.monitor.domain.team;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-domain
 *
 * @file Team.java
 *
 * @description 管理团队
 *
 * @time 2016年6月30日 下午8:19:56
 *
 */
@Table("t_team")
@Comment("团队")
public class Team extends Entity {

	@Column("t_name")
	@Comment("团队名称")
	private String name;

	@Column("t_uuid")
	@Comment("团队UUID")
	private String uuid = R.UU16();

	@Column("t_description")
	@Comment("团队描述")
	private String description;

	@Column("t_create_time")
	@Comment("团队创建时间")
	private Date createTime = Times.now();

	@Column("t_app_limit")
	@Comment("应用限量")
	private int appLimit = 2;

	@Column("t_db_limit")
	@Comment("数据库限量")
	private int dbLimit = 3;

	@Column("t_ftp_limit")
	@Comment("FTP资源限量")
	private int ftpLimit = 1;

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the appLimit
	 */
	public int getAppLimit() {
		return appLimit;
	}

	/**
	 * @param appLimit
	 *            the appLimit to set
	 */
	public void setAppLimit(int appLimit) {
		this.appLimit = appLimit;
	}

	/**
	 * @return the dbLimit
	 */
	public int getDbLimit() {
		return dbLimit;
	}

	/**
	 * @param dbLimit
	 *            the dbLimit to set
	 */
	public void setDbLimit(int dbLimit) {
		this.dbLimit = dbLimit;
	}

	/**
	 * @return the ftpLimit
	 */
	public int getFtpLimit() {
		return ftpLimit;
	}

	/**
	 * @param ftpLimit
	 *            the ftpLimit to set
	 */
	public void setFtpLimit(int ftpLimit) {
		this.ftpLimit = ftpLimit;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
