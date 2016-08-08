package club.zhcs.monitor.domain.team;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-domain
 *
 * @file TeamUser.java
 *
 * @description 用户团队归属关系
 *
 * @time 2016年6月30日 下午8:21:37
 *
 */
@Table("t_team_user")
public class TeamUser extends Entity {

	@Column("tu_team_id")
	@Comment("团队id")
	private int teamId;

	@Column("tu_user_id")
	@Comment("用户id")
	private int userId;

	@Column("tu_u_is_admin")
	@Comment("是否管理员标识")
	private boolean admin;

	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the teamId
	 */
	public int getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId
	 *            the teamId to set
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
