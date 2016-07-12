package club.zhcs.monitor.extension.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.nutz.mvc.Mvcs;

import club.zhcs.monitor.Application.SessionKeys;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description session 登录用户函数
 * 
 * @copyright 内部代码,禁止转发
 *
 *
 * @time 2016年1月26日 下午3:32:45
 */
public class LoginUserFun implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.Function#call(java.lang.Object[],
	 * org.beetl.core.Context)
	 */
	@Override
	public Object call(Object[] paras, Context ctx) {
		return Mvcs.getReq().getSession(true).getAttribute(SessionKeys.USER_KEY);
	}

}
