package club.zhcs.monitor.module.front;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import club.zhcs.monitor.Application.SessionKeys;
import club.zhcs.monitor.domain.acl.User;
import club.zhcs.monitor.domain.team.Team;
import club.zhcs.monitor.service.team.TeamService;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-web
 *
 * @file TeamModule.java
 *
 * @description 团队
 *
 * @time 2016年7月12日 下午8:27:52
 *
 */
@At("/team")
public class TeamModule extends AbstractBaseModule {

	@Inject
	TeamService teamService;

	@At
	@GET
	@Ok("beetl:pages/front/team/create.html")
	public Result create() {
		return Result.success();
	}

	@At
	@POST
	public Result create(@Param("..") Team team, @Attr(SessionKeys.USER_KEY) User user) {

		return teamService.createTeam(team, user);
	}

	@At("/*")
	@Ok("beetl:pages/front/team/detail.html")
	public Result detail(String uuid) {
		Team team = teamService.fetch(Cnd.where("uuid", "=", uuid));
		_putSession(SessionKeys.TEAM_KEY, team);
		return Result.success().addData("team", team);
	}
}
