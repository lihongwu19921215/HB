package club.zhcs.monitor.service.team;

import org.nutz.dao.Cnd;
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
		 * 2.保存团队数据<br>
		 * 3.设置用户为管理员<br>
		 */
		if (fetch(Cnd.where("name", "=", team.getName())) != null) {
			return Result.fail("团队 " + team.getName() + " 已经存在!");
		}
		team = save(team);
		if (team == null) {
			return Result.fail("添加团队失败!");
		}
		TeamUser teamUser = new TeamUser();
		teamUser.setAdmin(true);
		teamUser.setTeamId(team.getId());
		teamUser.setUserId(user.getId());

		teamUser = teamUserService.save(teamUser);

		return teamUserService.save(teamUser) == null ? Result.fail("添加团队失败!") : Result.success().addData("team", team);
	}
}
