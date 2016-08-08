package club.zhcs.monitor.module.admin.config;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import club.zhcs.monitor.domain.config.Config;
import club.zhcs.monitor.service.config.ConfigService;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project thunder-web
 *
 * @file SettingModule.java
 *
 * @description 配置管理
 *
 * @copyright 内部代码,禁止转发
 *
 * @time 2016年5月12日 上午11:28:24
 *
 */
@At("setting")
public class SettingModule extends AbstractBaseModule {

	@Inject
	ConfigService configService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kerbores.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return "settings";
	}

	@At
	@Ok("beetl:pages/setting/list.html")
	@RequiresRoles("admin")
	public Result list(@Param(value = "page", df = "1") int page) {
		Pager<Config> pager = configService.searchByPage(_fixPage(page));
		pager.setUrl(_base() + "/setting/list");
		return Result.success().addData("pager", pager).setTitle("配置列表");
	}

	@At
	@Ok("beetl:pages/setting/list.html")
	@RequiresRoles("admin")
	public Result search(@Param("key") String key, @Param(value = "page", df = "1") int page) {
		page = _fixPage(page);
		key = _fixSearchKey(key);
		Pager<Config> pager = configService.searchByKeyAndPage(key, page, "name", "description");
		pager.setUrl(_base() + "/setting/search");
		pager.addParas("key", key);
		return Result.success().addData("pager", pager);
	}

	@At
	@GET
	@Ok("beetl:pages/setting/add_edit.html")
	@RequiresRoles("admin")
	public Result add() {
		return Result.success();
	}

	@At("/edit/*")
	@GET
	@Ok("beetl:pages/setting/add_edit.html")
	@RequiresRoles("admin")
	public Result edit(int id) {
		return Result.success().addData("config", configService.fetch(id));
	}

	@At
	@POST
	@RequiresRoles("admin")
	public Result edit(@Param("..") Config config) {
		return configService.update(config) == 1 ? Result.success() : Result.fail("更新失败!");
	}

	@At
	@POST
	@RequiresRoles("admin")
	public Result add(@Param("..") Config config) {
		return configService.save(config) == null ? Result.fail("添加配置失败") : Result.success();
	}

	@At("/delete/*")
	@RequiresRoles("admin")
	public Result delete(int id) {
		return configService.delete(id) == 1 ? Result.success() : Result.fail("删除配置失败!");
	}

}
