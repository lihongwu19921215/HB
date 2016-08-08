package club.zhcs.monitor.module.admin;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import club.zhcs.monitor.domain.acl.Role;
import club.zhcs.monitor.service.acl.RoleService;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Pager;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 角色控制器
 * 
 * @copyright 内部代码,禁止转发
 *
 *
 * @time 2016年1月26日 下午3:38:21
 */
@At("role")
public class RoleModule extends AbstractBaseModule {

	@Inject
	private RoleService roleService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dgj.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return "acl";
	}

	/**
	 * 角色列表
	 * 
	 * @param page
	 *            页码
	 * @return
	 */
	@At
	@Ok("beetl:pages/admin/auth/role/list.html")
	@RequiresRoles("admin")
	public Result list(@Param(value = "page", df = "1") int page) {
		page = _fixPage(page);
		Pager<Role> pager = roleService.searchByPage(page);
		pager.setUrl(_base() + "/role/list");
		return Result.success().addData("pager", pager);
	}

	/**
	 * 搜索角色
	 * 
	 * @param page
	 *            页码
	 * @param key
	 *            关键词
	 * @return
	 */
	@At
	@Ok("beetl:pages/admin/auth/role/list.html")
	@RequiresRoles("admin")
	public Result search(@Param(value = "page", df = "1") int page, @Param("key") String key) {
		page = _fixPage(page);
		key = _fixSearchKey(key);
		Pager<Role> pager = roleService.searchByKeyAndPage(key, page, "name", "description");
		pager.setUrl(_base() + "/role/search");
		pager.addParas("key", key);
		return Result.success().addData("pager", pager);
	}

	/**
	 * 添加角色页面
	 * 
	 * @return
	 */
	@At
	@GET
	@Ok("beetl:pages/admin/auth/role/add_edit.html")
	@RequiresRoles("admin")
	public Result add() {
		return Result.success();
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            待添加角色
	 * @return
	 */
	@At
	@POST
	@RequiresRoles("admin")
	public Result add(@Param("..") Role role) {
		if (null != roleService.fetch(role.getName())) {
			return Result.fail("角色" + role.getName() + "已存在");
		}
		role.setInstalled(false);
		role = roleService.save(role);
		return role == null ? Result.fail("添加角色失败") : Result.success().addData("role", role);
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 *            角色id
	 * @return
	 */
	@At("/delete/*")
	@RequiresRoles("admin")
	public Result delete(int id) {
		return roleService.delete(id) == 1 ? Result.success() : Result.fail("删除失败!");
	}

	/**
	 * 编辑页码页面
	 * 
	 * @param id
	 *            角色id
	 * @return
	 */
	@At("/edit/*")
	@Ok("beetl:pages/admin/auth/role/add_edit.html")
	@RequiresRoles("admin")
	public Result edit(int id) {
		Role role = roleService.fetch(id);
		return Result.success().addData("role", role);
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 *            待更新角色
	 * @return
	 */
	@At
	@POST
	@RequiresRoles("admin")
	public Result update(@Param("..") Role role) {
		return roleService.update(role, "description") == 1 ? Result.success() : Result.fail("更新失败!");
	}

	/**
	 * 授权页面
	 * 
	 * @param id
	 * @return
	 *
	 * @author 王贵源
	 */
	@At("/grant/*")
	@GET
	@Ok("beetl:pages/admin/auth/role/grant.html")
	@RequiresRoles("admin")
	public Result grant(int id) {
		List<Record> records = roleService.findPermissionsWithRolePowerdInfoByRoleId(id);
		return Result.success().addData("records", records).addData("roleId", id);
	}

	/**
	 * ajax 授权
	 *
	 * @param ids
	 * @param roleId
	 * @return
	 *
	 * @author 王贵源
	 */
	@At
	@POST
	@RequiresRoles("admin")
	public Result grant(@Param("permissions") int[] ids, @Param("id") int roleId) {
		return roleService.setPermission(ids, roleId);
	}
}
