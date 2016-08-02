package club.zhcs.monitor.module.front;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import club.zhcs.monitor.Application.SessionKeys;
import club.zhcs.monitor.domain.acl.User;
import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.domain.resource.FtpServer.Type;
import club.zhcs.monitor.domain.resource.Resource.OwnerType;
import club.zhcs.monitor.domain.resource.Resource.TESTINGPERIOD;
import club.zhcs.monitor.domain.team.Team;
import club.zhcs.monitor.service.resource.FTPServerService;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-web
 *
 * @file FTPModule.java
 *
 * @description FTP
 *
 * @time 2016年7月25日 下午9:42:15
 *
 */
@At("ftp")
@Filters({ @By(type = CheckSession.class, args = { SessionKeys.TEAM_KEY, "/team/create" }) })
// 没有团队就自己去创建吧
public class FTPModule extends AbstractBaseModule {

	private @Inject FTPServerService ftpServerService;

	@At("/*")
	@Ok("beetl:pages/front/ftp/list.html")
	public Result list(@Attr(SessionKeys.USER_KEY) User user, @Attr(SessionKeys.TEAM_KEY) Team team, @Param(value = "page", df = "1") int page) {
		page = _fixPage(page);
		Pager<FtpServer> pager = ftpServerService.searchByPage(
				page,
				Cnd.where(Cnd.exps("ownerType", "=", OwnerType.TEAM).and("ownerId", "=", team.getId())).or(
						Cnd.exps("ownerType", "=", OwnerType.PRIVATE).and("ownerId", "=", user.getId())));
		pager.setUrl(_base() + "/ftp");
		return Result.success().addData("pager", pager).setTitle("FTP资源列表");
	}

	@At
	@GET
	@Ok("beetl:pages/front/ftp/add_edit.html")
	public Result add() {
		return Result.success().setTitle("添加FTP服务器").addData("types", Type.values()).addData("testingperiods", TESTINGPERIOD.values());
	}
}
