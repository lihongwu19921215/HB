package club.zhcs.monitor.extension.shiro;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.nutz.lang.Lang;
import org.nutz.mvc.Mvcs;

import club.zhcs.monitor.domain.acl.User;
import club.zhcs.monitor.domain.acl.User.Status;
import club.zhcs.monitor.service.acl.ShiroUserService;

/**
 * 
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description shiro鉴权realm
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年6月29日 下午2:47:35
 */
public class MonitorRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		HttpSession session = Mvcs.getReq().getSession();
		String userName = principalCollection.getPrimaryPrincipal().toString();
		User user = getUserService().findByName(userName);
		if (user == null)// 用户不存在
			return null;
		if (user.getStatus() == Status.DISABLED)// 用户被锁定
			throw new LockedAccountException("Account [" + userName + "] is locked.");
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		List<String> roleNameList = getUserService().getRolesInfo(user.getId());
		session.setAttribute("roles", roleNameList);
		auth.addRoles(roleNameList);// 添加角色
		List<String> permissionNames = getUserService().getAllPermissionsInfo(user.getId());
		session.setAttribute("permissions", permissionNames);
		auth.addStringPermissions(permissionNames);// 添加权限
		return auth;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		HttpSession session = Mvcs.getReq().getSession();
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();
		User user = getUserService().findByName(userName);
		if (Lang.isEmpty(user))// 用户不存在
			return null;
		if (user.getStatus() == Status.DISABLED)// 用户被锁定
			throw new LockedAccountException("Account [" + upToken.getUsername() + "] is locked.");

		SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());

		List<String> roleNameList = getUserService().getRolesInfo(user.getId());
		session.setAttribute("roles", roleNameList);
		List<String> permissionNames = getUserService().getAllPermissionsInfo(user.getId());
		session.setAttribute("permissions", permissionNames);
		return account;
	}

	private ShiroUserService getUserService() {
		return Mvcs.getIoc().get(ShiroUserService.class);
	}
}
