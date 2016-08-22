package club.zhcs.monitor.module.front;

import org.nutz.dao.Cnd;
import org.nutz.dao.DB;
import org.nutz.dao.TableName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import club.zhcs.monitor.Application.SessionKeys;
import club.zhcs.monitor.domain.acl.User;
import club.zhcs.monitor.domain.record.DataBaseMonitorRecode;
import club.zhcs.monitor.domain.resource.DataBase;
import club.zhcs.monitor.domain.resource.Resource.OwnerType;
import club.zhcs.monitor.domain.resource.Resource.TESTINGPERIOD;
import club.zhcs.monitor.domain.team.Team;
import club.zhcs.monitor.service.resource.DataBaseService;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-web
 *
 * @file DataBaseModule.java
 *
 * @description 数据库
 *
 * @time 2016年8月22日 下午3:31:32
 *
 */
@At("db")
@Filters({ @By(type = CheckSession.class, args = { SessionKeys.TEAM_KEY, "/team/create" }) })
public class DataBaseModule extends AbstractBaseModule {

	@Inject
	DataBaseService dataBaseService;

	@At("/*")
	@Ok("beetl:pages/front/db/list.html")
	public Result list(@Attr(SessionKeys.USER_KEY) User user, @Attr(SessionKeys.TEAM_KEY) Team team, @Param(value = "page", df = "1") int page) {
		page = _fixPage(page);
		Pager<DataBase> pager = dataBaseService.searchByPage(
				page,
				Cnd.where(Cnd.exps("ownerType", "=", OwnerType.TEAM).and("ownerId", "=", team.getId())).or(
						Cnd.exps("ownerType", "=", OwnerType.PRIVATE).and("ownerId", "=", user.getId())));
		pager.setUrl(_base() + "/db");
		return Result.success().addData("pager", pager).setTitle(" 数据库资源列表");
	}

	@At
	@GET
	@Ok("beetl:pages/front/db/add_edit.html")
	public Result add() {
		return Result.success().setTitle("添加数据库服务器").addData("types", DB.values()).addData("testingperiods", TESTINGPERIOD.values());
	}

	@At("/add/base")
	@POST
	public Result add(@Param("..") DataBase db, @Attr(SessionKeys.TEAM_KEY) Team team) {

		db.setOwnerId(team.getId());

		if (dataBaseService.fetch(Cnd.where("name", "=", db.getName())) != null) {
			return Result.fail("资源信息添加失败!<br>资源 <span style='color:red'>`" + db.getName() + "`</span> 已经存在!");
		}
		db = dataBaseService.save(db);
		if (db != null) {
			TableName.set(db.getId());
			dataBaseService.dao().create(DataBaseMonitorRecode.class, false);
			return Result.success().addData("db", db);
		}
		return Result.fail("资源信息添加失败!");
	}

}
