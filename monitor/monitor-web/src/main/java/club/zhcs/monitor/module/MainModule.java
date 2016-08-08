package club.zhcs.monitor.module;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.beetl.ext.nutz.BeetlViewMaker;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.meta.Email;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.ChainBy;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import club.zhcs.monitor.Application.SessionKeys;
import club.zhcs.monitor.MonitorSetup;
import club.zhcs.monitor.chain.MonitorChainMaker;
import club.zhcs.monitor.domain.acl.User;
import club.zhcs.monitor.domain.acl.User.Status;
import club.zhcs.monitor.domain.team.Team;
import club.zhcs.monitor.hb.checker.impl.FTPConnectionChecker;
import club.zhcs.monitor.service.acl.RoleService;
import club.zhcs.monitor.service.acl.ShiroUserService;
import club.zhcs.monitor.service.acl.UserService;
import club.zhcs.monitor.service.email.EmailService;
import club.zhcs.monitor.service.team.TeamService;
import club.zhcs.monitor.tasks.APMTask;
import club.zhcs.titans.nutz.captcha.JPEGView;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.codec.DES;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project thunder-web
 *
 * @file MainModule.java
 *
 * @description 主模块
 *
 * @time 2016年3月8日 上午10:51:26
 *
 */

@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = { "*anno", "club.zhcs", "*tx", "*js", "ioc", "*async", "*quartz", "quartz" })
@Views({ BeetlViewMaker.class })
@Fail("http:500")
@Ok("json")
@Filters({ @By(type = CheckSession.class, args = { SessionKeys.USER_KEY, "/login" }) })
@SetupBy(MonitorSetup.class)
@ChainBy(type = MonitorChainMaker.class, args = {})
@Localization(value = "msg/", defaultLocalizationKey = "zh-CN")
public class MainModule extends AbstractBaseModule {

	@Inject
	APMTask apmTask;

	@Inject
	FTPConnectionChecker ftpChecker;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kerbores.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return "main";
	}

	@At
	@Filters
	public Result hello() {
		ftpChecker.check(null);
		return Result.success().addData("msg", "Hello nutz-monitor!");
	}

	@At
	@Filters
	public Result dashboard() {
		return apmTask.data();
	}

	@At
	@Filters
	@RequiresAuthentication
	public Result shiro() {

		return Result.success();
	}

	private @Inject RoleService roleService;

	private @Inject UserService userService;

	private @Inject EmailService emailService;

	private @Inject TeamService teamService;

	@Inject
	ShiroUserService shiroUserService;

	@At
	@Filters
	public View captcha(@Param("length") int length) {
		return new JPEGView(null, length);
	}

	@At("/")
	@Ok("beetl:pages/front/index.html")
	@Filters
	public Result index() {
		Mvcs.getReq().getSession(true);// 哈哈,你猜猜我能有什么用
		return Result.success().setTitle("控制台");
	}

	@At
	@GET
	@Ok("beetl:pages/front/register.html")
	@Filters
	public Result register() {
		return Result.success().setTitle("控制台");
	}

	@At
	@GET
	@Ok("beetl:pages/front/login.html")
	@Filters
	public Result login() {
		return Result.success().setTitle("控制台");
	}

	@At
	@POST
	@Filters
	public Result login(@Param("name") String user, @Param("password") String password, @Param("captcha") String captcha, HttpSession session) {
		if (Strings.equalsIgnoreCase(captcha, session.getAttribute(JPEGView.CAPTCHA).toString())) {
			Result result = shiroUserService.login(user, password);
			if (result.isSuccess()) {
				// 登录成功处理
				User u = (User) result.getData().get("loginUser");
				_putSession(SessionKeys.USER_KEY, u);
				List<Team> teams = teamService.listTeam(u.getId());
				if (teams != null && teams.size() > 0) {
					_putSession(SessionKeys.TEAM_KEY, teams.get(0));
				}
			}
			return result;
		} else {
			return Result.fail("验证码输入错误");
		}
	}

	@At
	@POST
	@Filters
	public Result switchLocale(@Param("locale") String locale) {
		return Mvcs.setLocalizationKey(locale) ? Result.success() : Result.fail(Strings.equalsIgnoreCase(locale, "zh-CN") ? "设置失败" : "Setting failed!");
	}

	@At
	@POST
	@Filters
	@Ok("re:beetl:pages/front/register_success.html")
	public String register(@Param("..") User user, HttpServletRequest request) throws IOException {
		user.setPassword(Lang.md5(user.getPassword()));
		user.setStatus(Status.DISABLED);
		if (userService.save(user) != null) {
			request.setAttribute("email", user.getEmail());
			Email email = new Email(user.getEmail());
			request.setAttribute("domain", email.getHost());
			emailService.sendRegisterEmail(user);
			return null;
		}
		request.setAttribute("error", "注册失败!");
		return "beetl:pages/front/register.html";
	}

	@At("/active/*")
	@Filters
	@Ok("beetl:pages/front/active_success.html")
	public Result active(String pwd, int id) {
		User user = userService.fetch(id);
		if (user.getStatus() == Status.ACTIVED) {
			return Result.fail("你的账号已经激活了,请勿重复操作!!");
		}
		if (Strings.equals(user.getPassword(), pwd)) {
			user.setStatus(Status.ACTIVED);
			return userService.update(user) == 1 ? Result.success() : Result.fail("激活失败");
		}
		return Result.fail("激活地址不正确,请确认!");
	}

	@At("/admin")
	@Ok("jsp:/login")
	@Filters
	public View login(@Attr(SessionKeys.USER_KEY) User user, HttpServletRequest request) {
		String cookie = _getCookie("kerbores");
		if (!Strings.isBlank(cookie)) {
			NutMap data = Lang.map(DES.decrypt(cookie));
			request.setAttribute("loginInfo", data);
		}
		return null;
	}

}
