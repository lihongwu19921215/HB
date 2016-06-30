package club.zhcs.monitor.module.apm;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project app
 *
 * @file DruidModule.java
 *
 * @description 数据库
 *
 * @time 2016年3月15日 下午5:18:55
 *
 */
@At("db")
public class DruidModule extends AbstractBaseModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kerbores.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return "db";
	}

	@At
	@RequiresRoles("admin")
	@Ok("beetl:pages/db/dashboard.html")
	public Result dashboard() {
		return Result.success();
	}

	@At
	@Ok("beetl:pages/db/sqlDetail.html")
	public Result sqlDetail(int sqlId) {
		return Result.success().addData("sqlId", sqlId);
	}

	@At
	@Ok("beetl:pages/db/sessionDetail.html")
	public Result sessionDetail(String sessionId) {
		return Result.success().addData("sessionId", sessionId);
	}

	@At
	@Ok("beetl:pages/db/uriDetail.html")
	public Result uriDetail(String uri) {
		return Result.success().addData("uri", uri);
	}

	@At
	@Ok("beetl:pages/db/connectionPool.html")
	public Result connectionPool(String id) {
		return Result.success().addData("id", id);
	}

}
