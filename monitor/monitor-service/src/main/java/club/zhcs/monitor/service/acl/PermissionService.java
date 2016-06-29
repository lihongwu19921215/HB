package club.zhcs.monitor.service.acl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.nutz.dao.sql.Sql;

import club.zhcs.monitor.domain.acl.Permission;
import club.zhcs.titans.utils.biz.BaseService;

/**
 * 
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 权限业务实现
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年6月29日 下午2:40:45
 */
public class PermissionService extends BaseService<Permission> {

	/**
	 * 获取用户的直接权限
	 * 
	 * @author 王贵源
	 * @param id
	 *            用户id
	 * @return 角色列表
	 */
	public List<Permission> listDirectPermissionsByUserId(int id) {
		Sql sql = dao().sqls().create("list.direct.permission.by.user.id");
		sql.params().set("userId", id);
		return searchObj(sql);
	}

	/**
	 * 获取用户的间接权限
	 * 
	 * @author 王贵源
	 * @param id
	 *            用户id
	 * @return 角色列表
	 */
	public List<Permission> listIndirectPermissionsByUserId(int id) {
		Sql sql = dao().sqls().create("list.indirect.permission.by.user.id");
		sql.params().set("userId", id);
		return searchObj(sql);
	}

	/**
	 * 用户的全部权限
	 * 
	 * @author 王贵源
	 * @param id
	 *            用户id
	 * @return
	 */
	public List<Permission> getAllPermissionsByUserId(int id) {
		List<Permission> target = listDirectPermissionsByUserId(id);
		target.addAll(listIndirectPermissionsByUserId(id));
		return new ArrayList(new HashSet(target));
	}

}
