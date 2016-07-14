package club.zhcs.monitor.extension.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.nutz.mvc.Mvcs;

import club.zhcs.monitor.Application.SessionKeys;
import club.zhcs.monitor.domain.acl.User;
import club.zhcs.monitor.service.team.TeamService;

import com.google.common.collect.Lists;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-service
 *
 * @file TeamFun.java
 *
 * @description //TODO description
 *
 * @time 2016年7月14日 下午9:36:00
 *
 */
public class TeamFun implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.Function#call(java.lang.Object[],
	 * org.beetl.core.Context)
	 */
	@Override
	public Object call(Object[] arg0, Context arg1) {

		Object object = Mvcs.getReq().getSession(true).getAttribute(SessionKeys.USER_KEY);
		if (object == null) {
			return Lists.newArrayList();
		}

		User user = (User) object;

		TeamService teamService = Mvcs.getIoc().get(TeamService.class);

		return teamService.listTeam(user.getId());
	}

}
