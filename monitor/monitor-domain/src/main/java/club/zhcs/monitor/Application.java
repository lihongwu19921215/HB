package club.zhcs.monitor;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.mvc.Mvcs;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project syl-bean
 *
 * @file Application.java
 *
 * @description 应用常量数据
 *
 * @time 2016年3月8日 上午10:51:26
 *
 */
public class Application {

	public static final String NAME = Mvcs.getIoc().get(PropertiesProxy.class, "config").get("application.name");
	public static final String COPYRIGHT = Mvcs.getIoc().get(PropertiesProxy.class, "config").get("copy.right");
	public static final String BAIDUMAPKEY = Mvcs.getIoc().get(PropertiesProxy.class, "config").get("baidu.map.key");

	/**
	 * 
	 * @author 王贵源
	 * @description session keys
	 * @Copyright 内部代码,禁止转发
	 * @date 2015年12月28日 下午9:54:31
	 *
	 */
	public static class SessionKeys {
		/**
		 * 用戶在session中保存的key
		 */
		public static final String USER_KEY = "KERBORES_USER";
	}

}
