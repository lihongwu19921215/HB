package club.zhcs.monitor;

import java.nio.charset.Charset;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.integration.shiro.NutShiro;
import org.nutz.ioc.Ioc;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.Encoding;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import club.zhcs.monitor.domain.acl.Role;
import club.zhcs.monitor.domain.acl.User;
import club.zhcs.monitor.domain.acl.User.Status;
import club.zhcs.monitor.domain.acl.UserRole;
import club.zhcs.monitor.domain.record.ApplicationMonitorRecord;
import club.zhcs.monitor.domain.record.DataBaseMonitorRecode;
import club.zhcs.monitor.domain.record.FtpServerMonitroRecord;
import club.zhcs.monitor.domain.resource.DataBase;
import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.service.acl.RoleService;
import club.zhcs.monitor.service.acl.UserRoleService;
import club.zhcs.monitor.service.acl.UserService;
import club.zhcs.monitor.service.resource.ApplicationService;
import club.zhcs.monitor.service.resource.DataBaseService;
import club.zhcs.monitor.service.resource.FTPServerService;

/**
 * 
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 数据初始化
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年6月29日 下午3:26:32
 */
public class MonitorSetup implements Setup {
	private static final Log log = Logs.get();

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see org.nutz.mvc.Setup#destroy(org.nutz.mvc.NutConfig)
	 */
	@Override
	public void destroy(NutConfig nc) {
		// 初始化

	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see org.nutz.mvc.Setup#init(org.nutz.mvc.NutConfig)
	 */
	@Override
	public void init(NutConfig nc) {

		NutShiro.DefaultLoginURL = "/";

		if (!Charset.defaultCharset().name().equalsIgnoreCase(Encoding.UTF8)) {
			log.warn("This project must run in UTF-8, pls add -Dfile.encoding=UTF-8 to JAVA_OPTS");
		}

		Ioc ioc = nc.getIoc();

		Dao dao = ioc.get(Dao.class);

		// 为全部标注了@Table的bean建表

		Daos.createTablesInPackage(dao, getClass().getPackage().getName() + ".domain", false);
		Daos.migration(dao, getClass().getPackage().getName() + ".domain", true, true);

		FTPServerService ftpServerService = ioc.get(FTPServerService.class);
		DataBaseService dataBaseService = ioc.get(DataBaseService.class);
		ApplicationService applicationService =
				ioc.get(ApplicationService.class);

		Lang.each(ftpServerService.queryAll(), new Each<FtpServer>() {

			@Override
			public void invoke(int index, FtpServer ftpServer, int length) throws
					ExitLoop, ContinueLoop, LoopException {
				TableName.set(ftpServer.getId());
				dao.create(FtpServerMonitroRecord.class, false);
				Daos.migration(dao, FtpServerMonitroRecord.class, true, true, true);
			}
		});

		Lang.each(dataBaseService.queryAll(), new Each<DataBase>() {

			@Override
			public void invoke(int index, DataBase db, int length) throws
					ExitLoop, ContinueLoop, LoopException {
				TableName.set(db.getId());
				dao.create(DataBaseMonitorRecode.class, false);
				Daos.migration(dao, DataBaseMonitorRecode.class, true, true, true);
			}
		});

		Lang.each(applicationService.queryAll(), new
				Each<club.zhcs.monitor.domain.resource.Application>() {

					@Override
					public void invoke(int index,
							club.zhcs.monitor.domain.resource.Application app, int length) throws
							ExitLoop, ContinueLoop, LoopException {
						TableName.set(app.getId());
						dao.create(ApplicationMonitorRecord.class,
								false);
						Daos.migration(dao,
								ApplicationMonitorRecord.class, true, true,
								true);
					}
				});

		// 超级管理员

		UserService userService = ioc.get(UserService.class);

		RoleService roleService = ioc.get(RoleService.class);

		UserRoleService userRoleService = ioc.get(UserRoleService.class);

		User surperMan = null;
		if ((surperMan = userService.fetch(Cnd.where("name", "=", "admin"))) == null) {
			surperMan = new User();
			surperMan.setEmail("kerbores@zhcs.club");
			surperMan.setName("admin");
			surperMan.setPassword(Lang.md5("123456"));
			surperMan.setPhone("18996359755");
			surperMan.setRealName("王贵源");
			surperMan.setStatus(Status.ACTIVED);
			surperMan = userService.save(surperMan);
		}

		Role admin = roleService.fetch(Cnd.where("name", "=", "admin"));
		if (admin == null) {
			admin = new Role();
			admin.setInstalled(true);
			admin.setName("admin");
			admin.setDescription("超级管理员");
			admin = roleService.save(admin);
		}

		if (userRoleService.fetch(Cnd.where("userId", "=", surperMan.getId()).and("roleId", "=", admin.getId())) == null) {
			UserRole userRole = new UserRole();
			userRole.setRoleId(admin.getId());
			userRole.setUserId(surperMan.getId());
			userRoleService.save(userRole);
		}

		ioc.get(NutQuartzCronJobFactory.class);// 触发任务

	}
}