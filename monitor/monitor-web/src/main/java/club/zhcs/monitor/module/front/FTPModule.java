package club.zhcs.monitor.module.front;

import org.nutz.dao.Cnd;
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
import club.zhcs.monitor.domain.record.FtpServerMonitroRecord;
import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.domain.resource.FtpServer.Type;
import club.zhcs.monitor.domain.resource.Resource.OwnerType;
import club.zhcs.monitor.domain.resource.Resource.TESTINGPERIOD;
import club.zhcs.monitor.domain.team.Team;
import club.zhcs.monitor.hb.checker.impl.FTPServerChecker;
import club.zhcs.monitor.service.quartz.QuartzService;
import club.zhcs.monitor.service.record.FtpServerMonitroRecordService;
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

	private @Inject FtpServerMonitroRecordService ftpServerMonitroRecordService;

	private @Inject FTPServerChecker ftpServerChecker;

	private @Inject QuartzService quartzService;

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

	@At("/add/base")
	@POST
	public Result add(@Param("..") FtpServer server, @Attr(SessionKeys.TEAM_KEY) Team team) {

		server.setOwnerId(team.getId());

		if (ftpServerService.fetch(Cnd.where("name", "=", server.getName())) != null) {
			return Result.fail("资源信息添加失败!<br>资源 <span style='color:red'>`" + server.getName() + "`</span> 已经存在!");
		}
		server = ftpServerService.save(server);
		if (server != null) {
			TableName.set(server.getId());
			ftpServerService.dao().create(FtpServerMonitroRecord.class, false);
			return Result.success().addData("ftpServer", server);
		}
		return Result.fail("资源信息添加失败!");
	}

	@At
	public Result delete(@Param("id") String uuid) {
		return ftpServerService.delete(ftpServerService.fetch(Cnd.where("uuid", "=", uuid))) == 1 ? Result.success() : Result.fail("删除失败!");
	}

	@At("/edit/info")
	@POST
	public Result editServerInfo(@Param("..") FtpServer server) {
		return ftpServerService.update(server, "type", "server", "port", "user", "password", "testResourcePath") ? Result.success() : Result.fail("更新信息失败!");
	}

	@At
	public Result charts(@Param("id") int id) {

		return Result.success().addData("connection", ftpServerMonitroRecordService.getConnectionTimes(id)).addData("download", ftpServerMonitroRecordService.getDownloadTimes(id));
	}

	@At("/edit/task")
	@POST
	public Result editServerTask(@Param("..") FtpServer server) {
		return ftpServerService.update(server, "testingperiod", "taskCron", "alarmEL") ? Result.success() : Result.fail("更新资源任务失败!");
	}

	@At("/check/connection")
	public Result connection(@Param("id") int id) {
		FtpServer server = ftpServerService.fetch(id);
		if (server == null) {
			return Result.fail("不存在这个服务器");
		}
		FtpServerMonitroRecord record = null;
		if (server.getType() == Type.SFTP) {
			record = ftpServerChecker.checkSFTPConnection(server);
		} else {
			record = ftpServerChecker.checkFTPConnection(server);
		}

		return record.isOk() ? Result.success() : Result.fail(record.getError());
	}

	@At("/check/download")
	public Result download(@Param("id") int id) {
		FtpServer server = ftpServerService.fetch(id);
		if (server == null) {
			return Result.fail("不存在这个服务器");
		}
		FtpServerMonitroRecord record = null;
		if (server.getType() == Type.SFTP) {
			record = ftpServerChecker.checkSFTPDownload(server);
		} else {
			record = ftpServerChecker.checkFTPDownload(server);
		}

		return record.isOk() ? Result.success() : Result.fail(record.getError());
	}

	@At("/check/start")
	public Result start(@Param("id") int id) {
		FtpServer server = ftpServerService.fetch(id);
		quartzService.addResourceMonitorJob(server);
		return Result.success();
	}
}
