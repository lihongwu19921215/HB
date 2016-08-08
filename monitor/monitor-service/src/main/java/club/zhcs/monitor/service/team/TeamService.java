package club.zhcs.monitor.service.team;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;

import club.zhcs.monitor.domain.acl.User;
import club.zhcs.monitor.domain.team.Team;
import club.zhcs.monitor.domain.team.TeamUser;
import club.zhcs.titans.utils.biz.BaseService;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-service
 *
 * @file TeamService.java
 *
 * @description 团队业务
 *
 * @time 2016年7月12日 下午9:42:37
 *
 */
public class TeamService extends BaseService<Team> {

	@Inject
	TeamUserService teamUserService;

	/**
	 * 创建团队
	 * 
	 * @param team
	 *            团队
	 * @param user
	 *            用户
	 * @return 创建结果
	 */
	public Result createTeam(Team team, User user) {
		/**
		 * 1.检查团队是否存在<br>
		 * 2.检查用户的团队数是否超过5个<br>
		 * 3.保存团队数据<br>
		 * 4.设置用户为管理员<br>
		 */
		if (fetch(Cnd.where("name", "=", team.getName())) != null) {
			return Result.fail("团队 " + team.getName() + " 已经存在!");
		}

		// TODO 这里的数量理论上是可配置的,而且可以单独对用户进行配置
		if (listTeam(user.getId()).size() >= 5) {
			return Result.fail("个人最多只能创建5个团队!");
		}

		team = save(team);
		if (team == null) {
			return Result.fail("添加团队失败!");
		}
		TeamUser teamUser = new TeamUser();
		teamUser.setAdmin(true);
		teamUser.setTeamId(team.getId());
		teamUser.setUserId(user.getId());

		return teamUserService.save(teamUser) == null ? Result.fail("添加团队失败!") : Result.success().addData("team", team);
	}

	/**
	 * @param id
	 * @return
	 */
	public List<Team> listTeam(int id) {
		// 需要缓存,不要每次都从数据库去获取
		Sql sql = create("list.team.by.user.id");
		sql.params().set("userId", id);
		return searchObj(sql);
	}
}
