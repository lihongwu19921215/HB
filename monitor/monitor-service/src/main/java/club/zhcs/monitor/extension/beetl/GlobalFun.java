package club.zhcs.monitor.extension.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;

import club.zhcs.monitor.Application;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description Application 全局函数
 * 
 * @copyright 内部代码,禁止转发
 *
 *
 * @time 2016年1月26日 下午3:32:22
 */
public class GlobalFun implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.Function#call(java.lang.Object[],
	 * org.beetl.core.Context)
	 */
	@Override
	public Object call(Object[] paras, Context ctx) {
		if (paras == null) {
			return null;
		}
		switch (paras[0].toString()) {
		case "debug":
			return Strings.equalsIgnoreCase("localhost", Mvcs.getReq().getServerName()) || Strings.equalsIgnoreCase("127.0.0.1", Mvcs.getReq().getServerName());
		case "name":
			return Application.NAME;
		case "copyright":
			return Application.COPYRIGHT;
		default:
			return paras;
		}

	}

}
